package co.za.bluemarble.di.application;

import android.app.Application;

import javax.inject.Singleton;

import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.common.UseCaseScheduler;
import co.za.bluemarble.common.UseCaseThreadPoolScheduler;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.data.remote.NasaEpicApi;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;
import co.za.bluemarble.utils.AppExecutors;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    AppExecutors getAppExecutors(){
        return new AppExecutors();
    }



}

