package com.example.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.model.DogDatabase;

public class DetailViewModel extends AndroidViewModel {


    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<DogBreed>();

    private RetrieveDogTask mDogTask;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int uuid){
        mDogTask = new RetrieveDogTask();
        mDogTask.execute(uuid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDogTask != null){
            mDogTask.cancel(true);
            mDogTask = null;
        }
    }

    private class RetrieveDogTask extends AsyncTask<Integer,Void,DogBreed>{

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int uuid = integers[0];
            return DogDatabase.getInstance(getApplication()).mDogDao().getDog(uuid);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {

            dogLiveData.setValue(dogBreed);
        }
    }
}
