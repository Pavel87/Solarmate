package com.pacmac.solarmate.model;

/**
 * Created by pacmac on 2016-06-22.
 */

public class SolarDataObject {

    private String sunrise = "";
    private String sunset = "";
    private String solarNoon = "";
    private String dayLength = "";
    private long timestamp = 0l;
    private double longitude = 0;
    private double latitude = 0;
    private String geoArea = "";


    public SolarDataObject(String sunrise, String sunset, String solarNoon, String dayLength, long timestamp, double latitude, double longitude, String geoArea) {
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.solarNoon = solarNoon;
        this.dayLength = dayLength;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geoArea = geoArea;
    }


    public String getGeoArea() {
        return geoArea;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getSolarNoon() {
        return solarNoon;
    }

    public String getDayLength() {
        return dayLength;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
