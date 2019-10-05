package com.escapadetechnologies.dogsapp.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.escapadetechnologies.dogsapp.R;

public class Util {

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable circularProgressDrawable){

        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.dog);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context){

        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }
}
