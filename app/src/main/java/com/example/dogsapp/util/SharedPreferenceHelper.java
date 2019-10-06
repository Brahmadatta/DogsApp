package com.example.dogsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferenceHelper {

    private static final String PREF_TIME = "Pref time";

    private static SharedPreferenceHelper instance;

    private SharedPreferences mPreferences;

    private SharedPreferenceHelper(Context context){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceHelper getInstance(Context context){
        if (instance == null){
            instance = new SharedPreferenceHelper(context);
        }

        return instance;
    }

    public void saveUpdateTime(long time){
        mPreferences.edit().putLong(PREF_TIME,time).apply();
    }

    public long getUpdateTime(){
        return mPreferences.getLong(PREF_TIME,0);
    }

}
