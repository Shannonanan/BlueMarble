package co.za.bluemarble.data.local;

import java.util.List;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import io.reactivex.Observable;



/**
 * Concrete implementation of a data source as a db.
 */
public class EpicLocalDataSource implements EpicDataSource {


    private EarthDao mEarthDao;

    @Override
    public Observable<List<EarthInfo>> getEarthInfo(String date) {
        return null;
    }
}
