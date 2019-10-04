package com.escapadetechnologies.dogsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.escapadetechnologies.dogsapp.model.DogBreed;

public class DetailViewModel extends ViewModel {


    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<DogBreed>();

    public void fetch(){
        DogBreed dog1 = new DogBreed("1","cargi","15 yrs","","companionship","calm and friendly","");
        dogLiveData.setValue(dog1);
    }
}
