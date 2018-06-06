package co.za.bluemarble.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import java.util.List;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;

@Dao
public interface EarthDao {

    /**
     * Select all EarthInfo from the table.
     *
     * @return all EarthInfo.
     */
    @Query("SELECT * FROM EarthInfo")
    List<EarthInfo> getEarthInfo();
}
