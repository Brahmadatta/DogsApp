package com.example.dogsapp.model;

import com.example.dogsapp.di.DaggerDogApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class DogApiService {


    @Inject
    public DogApi mDogApi;


    public DogApiService(){
        //DaggerDogApiComponent.create().inject(this);
        DaggerDogApiComponent.create().inject(this);
    }

    public Single<List<DogBreed>> getDogs(){
        return mDogApi.getDogs();
    }

}
