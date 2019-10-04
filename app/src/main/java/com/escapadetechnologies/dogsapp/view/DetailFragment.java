package com.escapadetechnologies.dogsapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.escapadetechnologies.dogsapp.R;
import com.escapadetechnologies.dogsapp.model.DogBreed;
import com.escapadetechnologies.dogsapp.viewmodel.DetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private int dogUuid;
    private DetailViewModel mDetailViewModel;

    @BindView(R.id.dogImage)
    ImageView dogImage;

    @BindView(R.id.dogName)
    TextView dogName;

    @BindView(R.id.dogPurpose)
    TextView dogPurpose;

    @BindView(R.id.dogTemperament)
    TextView dogTemparment;

    @BindView(R.id.dogLifeSpan)
    TextView dogLifeSpan;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();

        }

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mDetailViewModel.fetch();

        observeVIewModel();
    }

    private void observeVIewModel() {
        mDetailViewModel.dogLiveData.observe(this, dogBreed -> {
            if (dogBreed != null && dogBreed instanceof DogBreed){
                dogName.setText(dogBreed.dogBreed);
                dogPurpose.setText(dogBreed.bredFor);
                dogTemparment.setText(dogBreed.temparment);
                dogLifeSpan.setText(dogBreed.lifeSpan);
            }
        });
    }
}
