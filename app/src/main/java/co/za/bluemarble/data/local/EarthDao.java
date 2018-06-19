package co.za.bluemarble.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;

@Dao
public interface EarthDao {

    /**
     * Select all EarthInfoObj from the table.
     *
     * @return all EarthInfoObj.
     */
    @Query("SELECT * FROM EarthInfoObj")
    List<EarthInfoObj> getEarthInfo();


    /**
     * Delete all info.
     */
    @Query("DELETE FROM EarthInfoObj")
    void deleteInfo();

    /**
     * Insert info in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfo(EarthInfoObj task);
}
