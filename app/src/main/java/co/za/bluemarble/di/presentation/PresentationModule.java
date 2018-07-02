package co.za.bluemarble.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.common.UseCaseThreadPoolScheduler;
import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.data.local.EarthDao;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.data.remote.LoadImageInterface;
import co.za.bluemarble.data.remote.NasaEpicApi;
import co.za.bluemarble.features.GetAllImages.GetAllInfoContract;
import co.za.bluemarble.features.GetAllImages.GetAllInfoPresenter;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;
import co.za.bluemarble.features.common.ImageLoader;
import co.za.bluemarble.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {


    private final FragmentActivity mActivity;
  //  private LoadImageInterface imageInterface;

    public PresentationModule(FragmentActivity fragmentActivity)
                             // LoadImageInterface loadImageInterface)
    {
        mActivity = fragmentActivity;
      //  imageInterface = loadImageInterface;
    }


//    @Provides
//    LoadImageInterface imageInterface(){
//        return imageInterface;
//    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    Context context(Activity activity) {
        return activity;
    }


    @Provides
    UseCaseHandler getUseCaseHandler() {
        return  UseCaseHandler.getInstance();
    }

    @Provides
    GetAllInfoPresenter getAllInfoPresenter(GetAllInfo mGetAllInfo,
                                            UseCaseHandler mUseCaseHandler) {
        return new GetAllInfoPresenter(mGetAllInfo, mUseCaseHandler);
    }


    @Provides
    EpicLocalDataSource localDataSource(EarthDao mEarthDao, AppExecutors mAppExecutors) {
        return new EpicLocalDataSource(mEarthDao, mAppExecutors);
    }

    @Provides
    EpicRemoteDataSource remoteDataSource(NasaEpicApi nasaEpicApi) {
        return new EpicRemoteDataSource(nasaEpicApi);
    }


    @Provides
    EpicRepository epicRepository(EpicRemoteDataSource mRemoteDataSource, EpicLocalDataSource mLocalDataSource, ImageLoader imageLoader) {
        return new EpicRepository(mRemoteDataSource,mLocalDataSource, imageLoader);
    }

    @Provides
    GetAllInfo getAllInfoUseCase(EpicRepository epicRepository, ImageLoader imageLoader) {
        return new GetAllInfo(epicRepository, imageLoader);
    }

    @Provides
    ImageLoader getImageLoader(Activity activity) {
        return new ImageLoader(activity);
    }

}
