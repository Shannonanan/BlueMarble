package co.za.bluemarble.data.remote;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.za.bluemarble.Constants;
import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import co.za.bluemarble.features.common.ImageLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EpicRemoteDataSource implements EpicDataSource {


    private final NasaEpicApi mNasaEpicApi;
     Bitmap bitmap;

    @Nullable
    private Call<List<EarthInfoSchema>> mCall;

    public EpicRemoteDataSource(NasaEpicApi nasaEpicApi) {
        mNasaEpicApi = nasaEpicApi;
    }


    @Override
    public void getEarthInfo(ImageLoader imageLoader, String date, LoadInfoCallback callback) {

        List<EarthInfoPojos> allEnhancedInfo = new ArrayList<>();
        mCall = mNasaEpicApi.getEarthData("2017-06-11", "2qbtLM8G62k7Um5iwKE7gTlPJKUyP67u4J7h9sUw");
        mCall.enqueue(new Callback<List<EarthInfoSchema>>() {
            @Override
            public void onResponse(Call<List<EarthInfoSchema>> call, Response<List<EarthInfoSchema>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        allEnhancedInfo.addAll(convertSerilizableIntoPojo(response.body()));

                        for (EarthInfoPojos pojo: allEnhancedInfo) {

                            //get the date
                            String getDate = pojo.getDate();
                            String[] dateSplit = getDate.split("-");
                            String year = dateSplit[0];
                            String month = dateSplit[1];
                            String dayHasTimeAttached = dateSplit[2];

                            String dayWithoutTime = dayHasTimeAttached.substring(0, dayHasTimeAttached.indexOf(" "));

                            String url = "https://api.nasa.gov/EPIC/archive/enhanced/" +
                                    year + "/" + month + "/" + dayWithoutTime +
                                    "/png/" + pojo.getImage() + ".png?api_key=2qbtLM8G62k7Um5iwKE7gTlPJKUyP67u4J7h9sUw";

                            //save the url to get the image
                            pojo.setImage(url);

                           // encode the bitmap
                            bitmap = imageLoader.loadImageIntoBitmap(url);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            bitmap.recycle();

                            pojo.setEnhancedEarthImage(byteArray);
                        }


                        callback.onDataLoaded(allEnhancedInfo);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EarthInfoSchema>> call, Throwable t) {
                // emitter.onError(new NetworkConnectionException());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllInfo() {

    }

    @Override
    public void saveTask(EarthInfoObj marbles) {

    }

    @Override
    public void refreshTasks() {

    }


    private List< EarthInfoPojos> convertSerilizableIntoPojo(List<EarthInfoSchema> earthInfoSchemas) {
        //sets only the information that I want from the API
        List<EarthInfoPojos> info = new ArrayList<>(earthInfoSchemas.size());
        for (EarthInfoSchema schema : earthInfoSchemas) {
            info.add(new EarthInfoPojos(schema.getIdentifier(), schema.getCaption(), schema.getImage(),
                    schema.getVersion(), schema.getDate(), null));
        }
        return info;
    }


    private boolean isThereInternetConnection() {
        boolean isConnected = false;

//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}


//
//    public List<EarthInfoObj> getEarthInfo(String date, ) {
//        return Observable.create(emitter -> {
//            if (isThereInternetConnection()) {
//                try {
//
//                    mCall = mNasaEpicApi.getEarthData(date);
//                    mCall.enqueue(new Callback<List<EarthInfoSchema>>() {
//                        @Override
//                        public void onResponse(Call<List<EarthInfoSchema>> call, Response<List<EarthInfoSchema>> response) {
//                            if (response.isSuccessful()) {
//                                List<EarthInfoObj> info = new ArrayList<>();
//                                info.addAll(earthinfoFromEarthInfoSchemas(response.body()));
//                                emitter.onNext(info);
//                                emitter.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<EarthInfoSchema>> call, Throwable t) {
//                            emitter.onError(new NetworkConnectionException());
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    emitter.onError(new Exception());
//                }
//            }
//            else {
//                emitter.onError(new NetworkConnectionException());
//            }
//        });
//    }
