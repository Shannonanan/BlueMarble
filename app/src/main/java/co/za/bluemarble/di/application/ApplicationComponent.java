package co.za.bluemarble.di.application;

import android.app.Application;

import javax.inject.Singleton;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.local.EarthDao;
import co.za.bluemarble.data.local.EarthInfoDatabase;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.di.presentation.PresentationComponent;
import co.za.bluemarble.di.presentation.PresentationModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class, RoomModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);



}
