package co.za.bluemarble.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;

@Database(entities = {EarthInfo.class}, version = 1)
public abstract class EarthInfoDatabase extends RoomDatabase {

    private static EarthInfoDatabase INSTANCE;

    public abstract EarthDao earthDao();

    private static final Object sLock = new Object();

    public static EarthInfoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        EarthInfoDatabase.class, "EarthInfo.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
