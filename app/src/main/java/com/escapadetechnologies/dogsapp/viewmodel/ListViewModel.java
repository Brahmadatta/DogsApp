package com.escapadetechnologies.dogsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.escapadetechnologies.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();

    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh(){
        DogBreed dog1 = new DogBreed("1","cargi","15 yrs","","","","");
        DogBreed dog2 = new DogBreed("2","shepard","10 yrs","","","","");
        DogBreed dog3 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog4 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog5 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog6 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog7 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog8 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog9 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog10 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog11 = new DogBreed("3","labrador","13 yrs","","","","");
        DogBreed dog12 = new DogBreed("3","labrador","13 yrs","","","","");

        ArrayList<DogBreed> dogBreeds = new ArrayList<>();
        dogBreeds.add(dog1);
        dogBreeds.add(dog2);
        dogBreeds.add(dog3);
        dogBreeds.add(dog4);
        dogBreeds.add(dog5);
        dogBreeds.add(dog6);
        dogBreeds.add(dog7);
        dogBreeds.add(dog8);
        dogBreeds.add(dog9);
        dogBreeds.add(dog10);
        dogBreeds.add(dog11);
        dogBreeds.add(dog12);

        dogs.setValue(dogBreeds);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }
}
