package com.pacmac.solarmate.model;

import com.pacmac.solarmate.util.LocationAware;

/**
 * Created by pacmac on 2016-06-21.
 */

public class LocationObject {

    private double longitude;
    private double latitude;

    public LocationObject(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
