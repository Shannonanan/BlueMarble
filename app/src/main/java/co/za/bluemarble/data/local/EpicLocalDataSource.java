package co.za.bluemarble.data.local;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import co.za.bluemarble.utils.AppExecutors;


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
                    final List<EarthInfoObj> info = mEarthDao.getEarthInfo();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (info.isEmpty()){
                                callback.onDataNotAvailable();
                            }else {

                                callback.onDataLoaded(convertEntityToSchema(info));
                            }
                        }
                    });
                }
            };

            mAppExecutors.diskIO().execute(runnable);
    }

    private List<EarthInfoSchema> convertEntityToSchema(List<EarthInfoObj> earthInfoObj) {
        List<EarthInfoSchema> info = new ArrayList<>(earthInfoObj.size());
        for (EarthInfoObj schema : earthInfoObj) {
            info.add(new EarthInfoSchema(schema.getIdentifier(),schema.getCaption(),schema.getImage(),
                    schema.getVersion(), schema.getDate()));
        }
        return info;
    }

    @Override
    public void deleteAllInfo() {
        Runnable deleteRunnable = () -> mEarthDao.deleteInfo();
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void saveTask(EarthInfoObj marbles) {
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
