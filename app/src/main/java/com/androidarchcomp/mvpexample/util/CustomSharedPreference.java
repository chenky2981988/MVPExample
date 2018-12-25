package com.androidarchcomp.mvpexample.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreference {

    private static String PREFS_NAME = "mvpexample_pref";
    private Context mContext;
    private static CustomSharedPreference mCustomSharedPreference;
    private SharedPreferences mSharedPreferences;

    private CustomSharedPreference(Context context){
        this.mContext = context;
    }
    public static CustomSharedPreference getInstance(Context context){
        if(mCustomSharedPreference == null){
            mCustomSharedPreference = new CustomSharedPreference(context);
        }
        return mCustomSharedPreference;
    }

    public void saveString(String key, String value){
        mSharedPreferences = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String retriveString(String key){
        mSharedPreferences = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key,"");
    }

}
