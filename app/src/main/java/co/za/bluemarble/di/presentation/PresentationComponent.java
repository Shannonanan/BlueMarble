package co.za.bluemarble.di.presentation;

import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.features.GetAllImages.GetAllInfoActivity;
import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(GetAllInfoActivity getAllInfoActivity);

}
