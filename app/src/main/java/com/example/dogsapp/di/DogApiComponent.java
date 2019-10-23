package com.example.dogsapp.di;

import com.example.dogsapp.model.DogApiService;
import com.example.dogsapp.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = DogApiModule.class)
public interface DogApiComponent {

    void inject(ListViewModel listViewModel);

    void inject(DogApiService dogApiService);
}
