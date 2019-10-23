package com.example.dogsapp.di;

import com.example.dogsapp.model.DogApi;
import com.example.dogsapp.model.DogApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DogApiModule {

    private static final String BASE_URL = "https://raw.githubusercontent.com";

    @Provides
    public DogApi getDogsApiService(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DogApi.class);
    }

    @Provides
    public DogApiService provideDogsApiService(){
        return new DogApiService();
    }

}
