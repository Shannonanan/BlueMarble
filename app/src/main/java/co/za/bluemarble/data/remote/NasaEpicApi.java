package co.za.bluemarble.data.remote;


import java.util.List;

import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NasaEpicApi {

    ///api/enhanced/date/2018-06-01
    @GET("/api/enhanced/date/{date}")
    Call<List<EarthInfo>> getEarthData(@Path("date") String date);
}
