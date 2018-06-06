package co.za.bluemarble.di.application;

import javax.inject.Singleton;

import co.za.bluemarble.Constants;
import co.za.bluemarble.data.remote.NasaEpicApi;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingModule
{
    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    NasaEpicApi getStackoverflowApi(Retrofit retrofit) {
        return retrofit.create(NasaEpicApi.class);
    }

}
