package com.example.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogsapp.di.DaggerDogApiComponent;
import com.example.dogsapp.model.DogApiService;
import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.model.DogDao;
import com.example.dogsapp.model.DogDatabase;
import com.example.dogsapp.util.NotificationHelper;
import com.example.dogsapp.util.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    @Inject
    public DogApiService mDogApiService;


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();

    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private SharedPreferenceHelper mSharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    AsyncTask<List<DogBreed>,Void,List<DogBreed>> insertTask;
    AsyncTask<Void,Void,List<DogBreed>> retrieveTask;

    public ListViewModel(@NonNull Application application) {
        super(application);
        DaggerDogApiComponent.create().inject(this);
    }

    public void refresh() {

        checkCacheDuration();

        long updateTime = mSharedPreferenceHelper.getUpdateTime();
        long currentTime = System.nanoTime();

        if (updateTime != 0 && currentTime - updateTime < refreshTime){
            fetchFromDatabase();
        }else {
            fetchFromRemote();
        }
    }

    public void refreshByCache(){

        fetchFromRemote();
    }

    private void checkCacheDuration(){

        String preferenceTime = mSharedPreferenceHelper.getCacheDuration();

        if (!preferenceTime.equals("")){
            try {

                int cachePreferenceInt = Integer.parseInt(preferenceTime);
                refreshTime = cachePreferenceInt * 1000 * 1000 * 1000L;

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

    }

    private void fetchFromDatabase(){
        loading.setValue(true);
        retrieveTask = new RetrieveDogsTask();
        retrieveTask.execute();
    }

    private void fetchFromRemote() {

        loading.setValue(true);
        mCompositeDisposable.add(
                mDogApiService.getDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> dogBreeds) {

                                insertTask = new InsertDogsTask();
                                insertTask.execute(dogBreeds);
                                Toast.makeText(getApplication(), "Dogs data retrieved", Toast.LENGTH_SHORT).show();

                                NotificationHelper.getInstance(getApplication()).createNotification();

                            }

                            @Override
                            public void onError(Throwable e) {

                                dogLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void dogsRetrieved(List<DogBreed> dogBreedList){
        dogs.setValue(dogBreedList);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();

        if (insertTask != null){
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null){
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }

    private class InsertDogsTask extends AsyncTask<List<DogBreed>,Void,List<DogBreed>>{

        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];
            DogDao dao = DogDatabase.getInstance(getApplication()).mDogDao();
            dao.deleteAllDogs();

            ArrayList<DogBreed> newList = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));


            int i = 0;
            while (i < list.size()){
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            super.onPostExecute(dogBreedList);
            dogsRetrieved(dogBreedList);
            mSharedPreferenceHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveDogsTask extends AsyncTask<Void,Void,List<DogBreed>>{

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).mDogDao().getAllDOgs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            dogsRetrieved(dogBreedList);
            Toast.makeText(getApplication(), "Dogs Retrieved from Database", Toast.LENGTH_SHORT).show();
        }
    }
}
