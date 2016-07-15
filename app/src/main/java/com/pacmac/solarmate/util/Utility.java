package com.pacmac.solarmate.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pacmac on 2016-06-21.
 */

public class Utility {

    public static void setPreferenceBoolean(Context context, String key, boolean flag) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PreferenceFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,flag);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PreferenceFile, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(key,false);
    }

}
