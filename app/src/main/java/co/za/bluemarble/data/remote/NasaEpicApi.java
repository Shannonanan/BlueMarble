package co.za.bluemarble.data.remote;


import java.util.List;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NasaEpicApi {

    @GET("EPIC/api/enhanced/date/{date}/")
    Call <List<EarthInfoSchema>>  getEarthData(@Path("date") String date, @Query("api_key") String api_key);


    //natural
    //https://api.nasa.gov/EPIC/api/natural/date/2018-06-01?api_key=DEMO_KEY
    @GET("EPIC/api/natural/date/{date}/")
    Call <List<EarthInfoSchema>>  getEarthNaturalData(@Path("date") String date, @Query("api_key") String api_key);
}

