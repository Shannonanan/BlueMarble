package co.za.bluemarble.data.local;

import android.support.annotation.NonNull;

import java.util.List;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import co.za.bluemarble.utils.AppExecutors;
import io.reactivex.Observable;



/**
 * Concrete implementation of a data source as a db.
 */
public class EpicLocalDataSource implements EpicDataSource {

    private static volatile EpicLocalDataSource INSTANCE;

    private EarthDao mEarthDao;

    private AppExecutors mAppExecutors;

    public EpicLocalDataSource(EarthDao mEarthDao, AppExecutors mAppExecutors) {
        this.mEarthDao = mEarthDao;
        this.mAppExecutors = mAppExecutors;
    }


    public static EpicLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull EarthDao earthDao) {
        if (INSTANCE == null) {
            synchronized (EpicLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EpicLocalDataSource(earthDao, appExecutors );
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadInfoCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getEarthInfo(String date, LoadInfoCallback callback) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    final List<EarthInfo> info = mEarthDao.getEarthInfo();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (info.isEmpty()){
                                callback.onDataNotAvailable();
                            }else {
                                callback.onDataLoaded(info);
                            }
                        }
                    });
                }
            };

            mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllInfo() {
        Runnable deleteRunnable = () -> mEarthDao.deleteInfo();
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void saveTask(EarthInfo marbles) {
            Runnable saveRunnable = new Runnable() {
                @Override
                public void run() {
                    mEarthDao.insertInfo(marbles);
                }
            };
            mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshTasks() {

    }
}
