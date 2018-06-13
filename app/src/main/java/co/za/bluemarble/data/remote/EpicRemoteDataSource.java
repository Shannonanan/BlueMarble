package co.za.bluemarble.data.remote;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpicRemoteDataSource implements EpicDataSource {


    private final NasaEpicApi mNasaEpicApi;

    @Nullable
    private Call<List<EarthInfoSchema>> mCall;

    public EpicRemoteDataSource(NasaEpicApi nasaEpicApi) {
        mNasaEpicApi = nasaEpicApi;
    }


    @Override
    public void getEarthInfo(String date, LoadInfoCallback callback) {
        mCall = mNasaEpicApi.getEarthData(date);
                    mCall.enqueue(new Callback<List<EarthInfoSchema>>() {
                        @Override
                        public void onResponse(Call<List<EarthInfoSchema>> call, Response<List<EarthInfoSchema>> response) {
                            if (response.isSuccessful()) {
                                List<EarthInfo> info = new ArrayList<>();
                                info.addAll(earthinfoFromEarthInfoSchemas(response.body()));
                                callback.onDataLoaded(info);
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
    public void saveTask(EarthInfo marbles) {

    }


    private List<EarthInfo> earthinfoFromEarthInfoSchemas(List<EarthInfoSchema> earthInfoSchemas) {
        List<EarthInfo> info = new ArrayList<>(earthInfoSchemas.size());
        for (EarthInfoSchema schema : earthInfoSchemas) {
            info.add(new EarthInfo(schema.getIdentifier(),schema.getCaption(),schema.getImage(),
                    schema.getVersion(), schema.getDate()));
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
//    public List<EarthInfo> getEarthInfo(String date, ) {
//        return Observable.create(emitter -> {
//            if (isThereInternetConnection()) {
//                try {
//
//                    mCall = mNasaEpicApi.getEarthData(date);
//                    mCall.enqueue(new Callback<List<EarthInfoSchema>>() {
//                        @Override
//                        public void onResponse(Call<List<EarthInfoSchema>> call, Response<List<EarthInfoSchema>> response) {
//                            if (response.isSuccessful()) {
//                                List<EarthInfo> info = new ArrayList<>();
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
