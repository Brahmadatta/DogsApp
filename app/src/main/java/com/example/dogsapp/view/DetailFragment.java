package com.example.dogsapp.view;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentDetailBinding;
import com.example.dogsapp.databinding.SendSmsDialogBinding;
import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.model.DogPalette;
import com.example.dogsapp.model.SmsInfo;
import com.example.dogsapp.util.Util;
import com.example.dogsapp.viewmodel.DetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private boolean sendSms = false;

    private int dogUuid;
    private DetailViewModel mDetailViewModel;
    FragmentDetailBinding mDetailBinding;
    private DogBreed mDogBreed;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false);
        this.mDetailBinding = binding;
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();

        }

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mDetailViewModel.fetch(dogUuid);

        observeVIewModel();
    }

    private void observeVIewModel() {
        mDetailViewModel.dogLiveData.observe(this, dogBreed -> {
            if (dogBreed != null && dogBreed instanceof DogBreed && getContext() != null){
                mDogBreed = dogBreed;
                mDetailBinding.setDog(dogBreed);
                if (dogBreed.imageUrl != null){
                    setupBackgroundColor(dogBreed.imageUrl);
                }
            }
        });
    }

    private void setupBackgroundColor(String url){

        Glide.with(this).asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        Palette.from(resource)
                                .generate(palette -> {
                                   int intColor = palette.getLightMutedSwatch().getRgb();
                                    DogPalette dogPalette = new DogPalette(intColor);
                                    mDetailBinding.setPalette(dogPalette);
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_send_sms: {

                if (!sendSms){
                    sendSms = true;
                    ((MainActivity)getActivity()).checkSmsPermission();
                }
                break;
            }

            case R.id.action_share: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check out this dog breed");
                intent.putExtra(Intent.EXTRA_TEXT,mDogBreed.dogBreed + " bred for " + mDogBreed.bredFor);
                intent.putExtra(Intent.EXTRA_STREAM,mDogBreed.imageUrl);
                startActivity(Intent.createChooser(intent,"Share with"));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPermissionResult(Boolean permissionGranted){

        if (isAdded() && permissionGranted && sendSms){

            SmsInfo smsInfo = new SmsInfo("",mDogBreed.dogBreed + "bred for " + mDogBreed.bredFor, mDogBreed.imageUrl);

            SendSmsDialogBinding sendSmsDialogBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.send_sms_dialog,
                    null,
                    false
            );

            new AlertDialog.Builder(getContext())
                    .setView(sendSmsDialogBinding.getRoot())
                    .setPositiveButton("Send SMS",((dialogInterface, i) -> {

                        if (!sendSmsDialogBinding.smsDestination.getText().toString().isEmpty()){
                            smsInfo.to = sendSmsDialogBinding.smsDestination.getText().toString();
                            sendSms(smsInfo);
                        }
                    }))
                    .setNegativeButton("Cancel",((dialogInterface, i) -> {

                    }))
                    .show();

            sendSms = false;
            sendSmsDialogBinding.setSmsInfo(smsInfo);
        }

    }

    private void sendSms(SmsInfo smsInfo) {

        Intent intent = new Intent(getContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0,intent,0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsInfo.to,null,smsInfo.text,pendingIntent,null);
    }
}
