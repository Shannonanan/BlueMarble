package co.za.bluemarble;

import android.app.Application;

import co.za.bluemarble.di.application.ApplicationComponent;
import co.za.bluemarble.di.application.ApplicationModule;
import co.za.bluemarble.di.application.DaggerApplicationComponent;
import co.za.bluemarble.di.application.RoomModule;


public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}
