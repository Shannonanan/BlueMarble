package co.za.bluemarble.presenter;


import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.features.GetAllImages.GetAllInfoContract;
import co.za.bluemarble.features.GetAllImages.GetAllInfoPresenter;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObjEnhanced;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;
import co.za.bluemarble.features.common.ImageLoader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GellAllInfoPresenterTest {

    private static List<EarthInfoPojos> INFO;

    @Mock
    private EpicRepository mEpicRepository;

    @Mock
    private GetAllInfoContract contractView;

    @Mock
    private ImageLoader mLoader;

    private String dated = "2018-06-01";
    @Captor
    private ArgumentCaptor<EpicDataSource.LoadInfoCallback> mLoadInfoCallbackArgumentCaptor;

    private GetAllInfoPresenter mGetAllInfoPresenter;

    @Before
    public void setupInfoPresenter(){
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mGetAllInfoPresenter = givenInfoPresenter();

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);

        INFO = Lists.newArrayList(
                new EarthInfoPojos("20180601010436",
                        "This image was taken by NASA's EPIC camera onboard the NOAA DSCOVR spacecraft",
                        "epic_RGB_20180601010436", "02", "2018-06-01" ),
                new EarthInfoPojos("identifier",
                        "caption", "image",
                        "version", "2018-06-01" ));
    }

    private GetAllInfoPresenter givenInfoPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetAllInfo getAllInfo = new GetAllInfo(mEpicRepository, mLoader);

        return new GetAllInfoPresenter(getAllInfo,useCaseHandler);
    }

    @Test
    public void createPresenter_SetsThePresenterToView(){
        // Get a reference to the class under test
        mGetAllInfoPresenter = givenInfoPresenter();


        // Then the presenter is set to the view
        verify(mGetAllInfoPresenter).setView(contractView);
    }

    @Test
    public void loadAllInfoFromRepositoryAndLoadIntoView()
    {
            mGetAllInfoPresenter.loadInfo(true);

        // Callback is captured and invoked with stubbed tasks
        verify(mEpicRepository).getEarthInfo(Matchers.eq(dated), mLoadInfoCallbackArgumentCaptor.capture());
            mLoadInfoCallbackArgumentCaptor.getValue().onDataLoaded(INFO);

        // Then progress indicator is shown
        InOrder inOrder = inOrder(contractView);
        inOrder.verify(contractView).setLoadingIndicator(true);
        // Then progress indicator is hidden and all tasks are shown in UI
        inOrder.verify(contractView).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(contractView).showInfo(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 2);
    }
}
