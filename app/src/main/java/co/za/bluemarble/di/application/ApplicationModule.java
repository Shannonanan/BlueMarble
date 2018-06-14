package co.za.bluemarble.di.application;

import android.app.Application;

import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.data.remote.NasaEpicApi;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;
import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    EpicRemoteDataSource getAllInfoUseCase(NasaEpicApi nasaEpicApi) {
        return new EpicRemoteDataSource(nasaEpicApi);
    }
//
//    @Provides
//    FetchQuestionsListUseCase getFetchQuestionsListUseCase(StackoverflowApi stackoverflowApi) {
//        return new FetchQuestionsListUseCase(stackoverflowApi);
//    }

}

