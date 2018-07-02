package co.za.bluemarble.di.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.data.local.EarthDao;
import co.za.bluemarble.data.local.EarthInfoDatabase;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;
//https://medium.com/@marco_cattaneo/integrate-dagger-2-with-room-persistence-library-in-few-lines-abf48328eaeb

@Module
public class RoomModule {

    private EarthInfoDatabase earthInfoDatabase;


    public RoomModule(Application mApplication) {
        this.earthInfoDatabase = Room.databaseBuilder(mApplication,EarthInfoDatabase.class,
                "EarthInfoObjEnhanced.db").build();
    }

    @Singleton
    @Provides
    EarthInfoDatabase providesRoomDataBase() {
        return earthInfoDatabase;
    }

    @Singleton
    @Provides
    EarthDao providesEarthDao(EarthInfoDatabase earthInfoDatabase){
        return earthInfoDatabase.earthDao();
    }

    @Singleton
    @Provides
    EpicDataSource epicLocalDataSource(EarthDao earthDao, AppExecutors appExecutors){
        return new EpicLocalDataSource(earthDao, appExecutors);
    }


}
