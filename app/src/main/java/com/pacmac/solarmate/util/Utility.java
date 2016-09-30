package com.pacmac.solarmate.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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


    public static void setPreferenceString(Context context, String key, String string) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PreferenceFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,string);
        editor.commit();
    }

    public static String getPreferenceString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PreferenceFile, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key,null);
    }




    public static String getFormatedDateTimeLong(long timestamp){
        SimpleDateFormat format = new SimpleDateFormat("MMM dd hh:mm a" , Locale.US);
            return format.format(new Date(timestamp)).toString();
        // returning unformatted Date
    }

    public static long getDateFromTimeString(String time, long lastUpdateTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d" , Locale.US);
        Date lastUpdate = new Date(lastUpdateTime);
        String dateCompleteStr = format.format(lastUpdate).toString() +" " + time;

        format = new SimpleDateFormat("yyyy-MM-d hh:mm:ss a" , Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date output = format.parse(dateCompleteStr);
           return output.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(Constants.TAG, "Cannot parse date: " + dateCompleteStr + " " + e.getMessage());
        }
        return -1;
    }

    public static String getShortTimeForEvent(long milliseconds){
        Date date = new Date(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a" , Locale.US);
        return format.format(date).toString();
    }

    public static String getDiffTimeForNextEvent(long milliseconds){

        long timeNow = System.currentTimeMillis();

        Date dateDiff = new Date(milliseconds-timeNow);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm" , Locale.US);
        return "- " + format.format(dateDiff).toString();
    }

}
