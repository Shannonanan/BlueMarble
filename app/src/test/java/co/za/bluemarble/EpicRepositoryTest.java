package co.za.bluemarble;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EpicRepositoryTest {


    private static final String TASK_IDENTIFIER = "identifier";

    private static final String TASK_CAPTION = "caption";

    private static final String TASK_iMAGE = "image";

    private static final String TASK_VERSION = "version";

    private static final String TASK_DATE = "2018-06-01";


    private static List<EarthInfo> INFO = Lists.newArrayList(
            new EarthInfo("20180601010436",
                    "This image was taken by NASA's EPIC camera onboard the NOAA DSCOVR spacecraft",
                    "epic_RGB_20180601010436", "02", "2018-06-01" ),
            new EarthInfo("identifier",
                    "caption", "image",
                    "version", "2018-06-01" ));

    private EpicRepository mEpicRepository;

    @Mock
    private EpicRemoteDataSource mEpicRemoteDataSource;

    @Mock
    private EpicLocalDataSource mEpicLocalDataSource;

    @Mock
    private EpicDataSource.LoadInfoCallback mLoadInfoCallback;


    private String dated ="2018-06-01" ;

    @Mock
    private EpicDataSource.GetInfoCallback  mGetInfoCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<EpicDataSource.LoadInfoCallback> mLoadInfoCallbackCaptor;

    @Captor
    private ArgumentCaptor<EpicDataSource.GetInfoCallback> mGetInfoCallbackCaptor;


    @Before
    public void setupInfoRepo(){
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mEpicRepository = EpicRepository.getInstance(
                mEpicRemoteDataSource, mEpicLocalDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
        EpicRepository.destroyInstance();
    }
    @Test
    public void getInfo_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the tasks repository
        twoTasksLoadCallsToRepository(TASK_DATE,mLoadInfoCallback);

        // Then tasks were only requested once from Service API
        verify(mEpicRemoteDataSource).getEarthInfo(Matchers.eq(TASK_DATE), any(EpicDataSource.LoadInfoCallback.class));

    }

    @Test
    public void getTasks_requestsAllTasksFromLocalDataSource() {
        // When tasks are requested from the tasks repository
        mEpicRepository.getEarthInfo(dated,mLoadInfoCallback);

        // Then tasks are loaded from the local data source
        verify(mEpicLocalDataSource).getEarthInfo(Matchers.eq(dated), any(EpicDataSource.LoadInfoCallback.class));
    }


    @Test
    public void saveTask_savesTaskToServiceAPI() {
        // Given a stub info with arguments
        EarthInfo newInfo = new EarthInfo(TASK_IDENTIFIER, TASK_CAPTION,TASK_iMAGE,TASK_VERSION,TASK_DATE);

        // When earthInfo is saved to the EpicRespository repository
        mEpicRepository.saveTask(newInfo);

        // Then the service API and persistent repository are called and the cache is updated
        verify(mEpicRemoteDataSource).saveTask(newInfo);
        verify(mEpicLocalDataSource).saveTask(newInfo);
        assertThat(mEpicRepository.mCachedEarthInfo.size(), is(1));
    }


    @Test
    public void deleteAllInfo_deleteInfoToServiceAPIUpdatesCache(){
        // Given 3 stub completed tasks  in the repository
        EarthInfo newInfo1 = new EarthInfo("identifier1","caption1","image1","version1","date1");
        mEpicRepository.saveTask(newInfo1);
        EarthInfo newInfo2 = new EarthInfo("identifier2","caption2","image2","version2","date2");
        mEpicRepository.saveTask(newInfo2);
        EarthInfo newInfo3 = new EarthInfo("identifier3","caption3","image3","version3","date3");
        mEpicRepository.saveTask(newInfo3);

        // When all tasks are deleted to the epic repository
        mEpicRepository.deleteAllInfo();

        // Verify the data sources were called
        verify(mEpicRemoteDataSource).deleteAllInfo();
        verify(mEpicLocalDataSource).deleteAllInfo();

        assertThat(mEpicRepository.mCachedEarthInfo.size(), is(0));
    }

    @Test
    public void getInfoWithDirtyCache_InfoIsRetrievedFromRemote() {
        // When calling getEarthinfo in the repository with dirty cache
        mEpicRepository.refreshTasks();
        mEpicRepository.getEarthInfo(dated,mLoadInfoCallback);

        // And the remote data source has data available
        setInfoAvailable(mEpicRemoteDataSource, INFO);

        // Verify the info from the remote data source is returned, not the local
        verify(mEpicLocalDataSource, never()).getEarthInfo(dated,mLoadInfoCallback);
        verify(mLoadInfoCallback).onDataLoaded(INFO);
    }

    @Test
    public void getInfoWithLocalDataSourceUnavailable_infoIsRetrievedFromRemote() {
        // When calling getEarthInfo in the repository
        mEpicRepository.getEarthInfo(dated, mLoadInfoCallback);

        // And the local data source has no data available
        setInfoNotAvailable(mEpicLocalDataSource);

        // And the remote data source has data available
        setInfoAvailable(mEpicRemoteDataSource, INFO);

        // Verify the tasks from the local data source are returned
        verify(mLoadInfoCallback).onDataLoaded(INFO);
    }

    @Test
    public void getInfoWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        // When calling getEarthInfo in the repository
        mEpicRepository.getEarthInfo(dated, mLoadInfoCallback);

        // And the local data source has no data available
        setInfoNotAvailable(mEpicLocalDataSource);

        // And the local data source has no data available
        setInfoNotAvailable(mEpicRemoteDataSource);

        // Verify no data is returned
        verify(mLoadInfoCallback).onDataNotAvailable();
    }

    @Test
    public void getInfo_refreshesLocalDataSource() {
        // Mark cache as dirty to force a reload of data from remote data source.
        mEpicRepository.refreshTasks();

        // When calling getEarthInfo in the repository
        mEpicRepository.getEarthInfo(dated, mLoadInfoCallback);

        // Make the remote data source return data
        setInfoAvailable(mEpicRemoteDataSource, INFO);

        // Verify that the data fetched from the remote data source was saved in local.
        verify(mEpicLocalDataSource, times(INFO.size())).saveTask(any(EarthInfo.class));
    }

    private void setInfoNotAvailable(EpicDataSource dataSource) {
        verify(dataSource).getEarthInfo(Matchers.eq(dated), mLoadInfoCallbackCaptor.capture());
        mLoadInfoCallbackCaptor.getValue().onDataNotAvailable();
    }


    private void setInfoAvailable(EpicDataSource dataSource, List<EarthInfo> info) {
        verify(dataSource).getEarthInfo(Matchers.eq(dated), mLoadInfoCallbackCaptor.capture());
        mLoadInfoCallbackCaptor.getValue().onDataLoaded(info);
    }

    /**
     * Convenience method that issues two calls to the tasks repository
     */
    private void twoTasksLoadCallsToRepository(String TASK_DATEe, EpicDataSource.LoadInfoCallback callback) {
        // When info is requested from repository
        mEpicRepository.getEarthInfo(dated,mLoadInfoCallback); // First call to API

        // Use the Mockito Captor to capture the callback
        verify(mEpicLocalDataSource).getEarthInfo(Matchers.eq(dated), mLoadInfoCallbackCaptor.capture());

        // Local data source doesn't have data yet
        mLoadInfoCallbackCaptor.getValue().onDataNotAvailable();


        // Verify the remote data source is queried
        verify(mEpicRemoteDataSource).getEarthInfo(Matchers.eq(dated), mLoadInfoCallbackCaptor.capture());

        // Trigger callback so tasks are cached
        mLoadInfoCallbackCaptor.getValue().onDataLoaded(INFO);

        mEpicRepository.getEarthInfo(Matchers.eq(dated), callback); // Second call to API
    }

}