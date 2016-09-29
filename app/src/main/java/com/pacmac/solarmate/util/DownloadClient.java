package com.pacmac.solarmate.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.pacmac.solarmate.gui.PublishSolarDataCallback;
import com.pacmac.solarmate.model.SolarDataObject;
import com.pacmac.solarmate.model.SunObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pacmac on 08/05/16.
 */

public class DownloadClient {

    private URL url;
    private HttpURLConnection connection;
    private double longitude;
    private double latitude;
    private int day = 0;

    private PublishSolarDataCallback dataListener = null;


    public DownloadClient(double latitude, double longitude, int day) {

        this.latitude = latitude;
        this.longitude = longitude;
        // set day base on argument
        String path = Constants.URL_BASE + Constants.URL_LAT + latitude + Constants.URL_LON
                + longitude + ((day == 0) ? Constants.URL_TODAY : Constants.URL_TOMORROW);

//        Log.d(Constants.TAG, "URL: " + path);
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(Constants.TAG, "wrong URL: " + e.getMessage());
        }
    }

    public void getSUNInformation() {
        openComm();
        return;
    }

    private void openComm() {
        new DownloadTask().execute(url);
    }

    // *******************************************************************//
    private class DownloadTask extends AsyncTask<URL, Integer, String> {
        @Override
        protected String doInBackground(URL... urls) {
            StringBuilder sb = new StringBuilder();

            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                return sb.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(Constants.TAG, "result: " + result);
            try {
                if(dataListener != null) {
                    dataListener.dataReady(day,parseJSON(result));
                }
            } catch (JSONException e) {
                Log.e(Constants.TAG, "There was error during parsing JSON string: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private SolarDataObject parseJSON(String rawResult) throws JSONException {
            SunObject sunObject = new Gson().fromJson(rawResult, SunObject.class);
//            Log.d(Constants.TAG, "SUNSET: " + sunObject.getResults().getSunrise());

            SolarDataObject solarObj = new SolarDataObject(sunObject.getResults().getSunrise(), sunObject.getResults().getSunset(),
                    sunObject.getResults().getSolarNoon(), sunObject.getResults().getDayLength(),
                    System.currentTimeMillis(), latitude, longitude, "VICTORIA");
            return solarObj;
        }
    }


    public void setDataListener(PublishSolarDataCallback listener) {
        this.dataListener = listener;
    }


}
