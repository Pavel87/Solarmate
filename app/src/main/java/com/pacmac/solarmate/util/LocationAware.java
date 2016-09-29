package com.pacmac.solarmate.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by pacmac on 2016-06-13.
 */

public class LocationAware implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static double longitude = 0;
    private static double latitude = 0;
    private boolean isConnected = false;

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatitude() {
        return latitude;
    }


    private static Context mContext = null;

    private static LocationAware locationAware = null;
    private static GoogleApiClient mGoogleApiClient = null;


    private LocationAware(Context context) {
        setupProvider(context);
        onStart();
    }

    public static LocationAware init(Context context) {
        if (locationAware == null) {
            locationAware = new LocationAware(context);
        }
        mContext = context;
        return locationAware;
    }


    private void setupProvider(Context context) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    public void onStart() {
        mGoogleApiClient.connect();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        isConnected = false;
    }


    @Override
    public void onConnected(Bundle bundle) {
        isConnected = true;
        sendLocConnectedBroadcast(isConnected);
        getLastLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        isConnected = false;
        sendLocConnectedBroadcast(isConnected);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        isConnected = false;
        sendLocConnectedBroadcast(isConnected);
    }

    public static Bundle getLastLocation() {
        // TODO check better for permission
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        // Create bundle for passing location to main Solar Activity and storing last location in settings
        Bundle locBundle = new Bundle();
        longitude = currentLocation.getLongitude();
        latitude = currentLocation.getLatitude();
        locBundle.putDouble(Constants.LAT_LOC, currentLocation.getLatitude());
        locBundle.putDouble(Constants.LONG_LOC, currentLocation.getLongitude());
        Utility.setPreferenceString(mContext,Constants.LAT_LOC, String.valueOf(latitude));
        Utility.setPreferenceString(mContext,Constants.LONG_LOC, String.valueOf(longitude));

        return locBundle;
    }



    private void sendLocConnectedBroadcast(boolean isConnected) {
        Intent onConnectedIntent = new Intent(Constants.ACTION_LOC_CONNECTED);
        onConnectedIntent.putExtra(Constants.INTENT_EXTRA_CONNECTED_FLAG, isConnected);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(onConnectedIntent);
    }

}
//TODO check for permission available