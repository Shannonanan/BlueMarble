package co.za.bluemarble.data.remote;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObjEnhanced;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EpicRemoteDataSource implements EpicDataSource {


    private final NasaEpicApi mNasaEpicApi;
//    LoadImageInterface loadImageInterface;
//    byte[] byteArray = new byte[0];
//    List<String> allUrls = new ArrayList<>();



    @Nullable
    private Call<List<EarthInfoSchema>> mCall;

    public EpicRemoteDataSource(NasaEpicApi nasaEpicApi ) {
        mNasaEpicApi = nasaEpicApi;
    }



    @Override
    public void getEarthInfo(String date, LoadInfoCallback callback) {

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

                            //save the url to get the image later
                            pojo.setImage(url);

                           // allUrls.add(url);
                        }
                        callback.onDataLoaded(allEnhancedInfo);
                      //  getAllInfo(callback, allEnhancedInfo, allUrls);
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

//    public void getAllInfo(LoadInfoCallback callback, List<EarthInfoPojos> allEnhancedInfoo,
//                           List<String> allUrls){
//
//
//        loadImageInterface.getImages(allUrls, new LoadImageCallback() {
//            @Override
//            public void onImageLoaded(List<byte[]> allBitmaps ) {
//               // allBitmaps.addAll(allEnhancedInfo);
//                for(int i =0; i <= allEnhancedInfoo.size(); i ++) {
//                    allEnhancedInfoo.get(i).setEnhancedEarthImage(allBitmaps.get(i));
//                }
//                callback.onDataLoaded(allEnhancedInfoo);
//            }
//
//            @Override
//            public void onImageNotAvailable() {
//
//            }
//        });
//    }

    @Override
    public void deleteAllInfo() {

    }

    @Override
    public void saveTask(EarthInfoObjEnhanced marbles) {

    }

    @Override
    public void refreshTasks() {

    }


    private List< EarthInfoPojos> convertSerilizableIntoPojo(List<EarthInfoSchema> earthInfoSchemas) {
        //sets only the information that I want from the API
        List<EarthInfoPojos> info = new ArrayList<>(earthInfoSchemas.size());
        for (EarthInfoSchema schema : earthInfoSchemas) {
            info.add(new EarthInfoPojos(schema.getIdentifier(), schema.getCaption(), schema.getImage(),
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

//
//    @Override
//    public void getImages(List<String> allUrls, LoadImageCallback callback) {
//
//    }

}


