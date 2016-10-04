package com.pacmac.solarmate.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import java.util.List;

/**
 * Created by pacmac on 08/05/16.
 */

public class DownloadClient {

    private URL url;
    private HttpURLConnection connection;
    private double longitude;
    private double latitude;
    private int day = 0;
    private Context context;

    private PublishSolarDataCallback dataListener = null;


    public DownloadClient(Context context, double latitude, double longitude, int day) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;
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

    public void translateCoordinates() {
        new GeoCoderTask().execute();
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

            if(sunObject == null)
                return null;
            SolarDataObject solarObj = new SolarDataObject(sunObject.getResults().getSunrise(), sunObject.getResults().getSunset(),
                    sunObject.getResults().getSolarNoon(), sunObject.getResults().getDayLength(),
                    System.currentTimeMillis(), latitude, longitude);
            return solarObj;
        }
    }


    public void setDataListener(PublishSolarDataCallback listener) {
        this.dataListener = listener;
    }


    // *******************************************************************//
    private class GeoCoderTask extends AsyncTask< Void, Integer, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return getCityFromCoord();
        }

        @Override
        protected void onPostExecute(String[] address) {
            dataListener.gecodingCompleted(day,address);
        }
    }

    // ******************************************************************
        private String[] getCityFromCoord() {
            if (Geocoder.isPresent()) {
                Geocoder geocoder = new Geocoder(context);
                List<Address> adressList = null;

                // will try to reverse geocode
                try {
                    adressList = geocoder.getFromLocation(latitude, longitude, 3);
                } catch (IOException e) {
                    // e.printStackTrace();
                    Log.e(Constants.TAG, "Reverse Geocoding failed due to network connection");
                    return null;
                }

                // If results available it will iterate through results and fill the fields
                if (adressList != null && adressList.size() > 0) {

                    String[] addressOutput = new String[5];

                    for (Address address : adressList) {

                        if (addressOutput[0] == null) {
                            addressOutput[0] = address.getLocality();
                        }
                        if (addressOutput[1] == null) {
                            addressOutput[1] = address.getPostalCode();
                        }
                        if (addressOutput[2] == null) {
                            addressOutput[2] = address.getAdminArea();
                        }
                        if (addressOutput[3] == null) {
                            addressOutput[3] = address.getCountryName();
                        }

                        if (addressOutput[4] == null) {
                            addressOutput[4] = address.getThoroughfare();
                        }
                    }

                    return addressOutput;
                }
            }
            return null;
        }

}
