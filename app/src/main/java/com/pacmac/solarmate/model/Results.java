package com.pacmac.solarmate.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pacmac on 08/05/16.
 */



public class Results {

    private String sunrise;
    private String sunset;
    private String solarNoon;
    private String dayLength;
    private String civilTwilightBegin;
    private String civilTwilightEnd;
    private String nauticalTwilightBegin;
    private String nauticalTwilightEnd;
    private String astronomicalTwilightBegin;
    private String astronomicalTwilightEnd;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The sunrise
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     *
     * @param sunrise
     * The sunrise
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     *
     * @return
     * The sunset
     */
    public String getSunset() {
        return sunset;
    }

    /**
     *
     * @param sunset
     * The sunset
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    /**
     *
     * @return
     * The solarNoon
     */
    public String getSolarNoon() {
        return solarNoon;
    }

    /**
     *
     * @param solarNoon
     * The solar_noon
     */
    public void setSolarNoon(String solarNoon) {
        this.solarNoon = solarNoon;
    }

    /**
     *
     * @return
     * The dayLength
     */
    public String getDayLength() {
        return dayLength;
    }

    /**
     *
     * @param dayLength
     * The day_length
     */
    public void setDayLength(String dayLength) {
        this.dayLength = dayLength;
    }

    /**
     *
     * @return
     * The civilTwilightBegin
     */
    public String getCivilTwilightBegin() {
        return civilTwilightBegin;
    }

    /**
     *
     * @param civilTwilightBegin
     * The civil_twilight_begin
     */
    public void setCivilTwilightBegin(String civilTwilightBegin) {
        this.civilTwilightBegin = civilTwilightBegin;
    }

    /**
     *
     * @return
     * The civilTwilightEnd
     */
    public String getCivilTwilightEnd() {
        return civilTwilightEnd;
    }

    /**
     *
     * @param civilTwilightEnd
     * The civil_twilight_end
     */
    public void setCivilTwilightEnd(String civilTwilightEnd) {
        this.civilTwilightEnd = civilTwilightEnd;
    }

    /**
     *
     * @return
     * The nauticalTwilightBegin
     */
    public String getNauticalTwilightBegin() {
        return nauticalTwilightBegin;
    }

    /**
     *
     * @param nauticalTwilightBegin
     * The nautical_twilight_begin
     */
    public void setNauticalTwilightBegin(String nauticalTwilightBegin) {
        this.nauticalTwilightBegin = nauticalTwilightBegin;
    }

    /**
     *
     * @return
     * The nauticalTwilightEnd
     */
    public String getNauticalTwilightEnd() {
        return nauticalTwilightEnd;
    }

    /**
     *
     * @param nauticalTwilightEnd
     * The nautical_twilight_end
     */
    public void setNauticalTwilightEnd(String nauticalTwilightEnd) {
        this.nauticalTwilightEnd = nauticalTwilightEnd;
    }

    /**
     *
     * @return
     * The astronomicalTwilightBegin
     */
    public String getAstronomicalTwilightBegin() {
        return astronomicalTwilightBegin;
    }

    /**
     *
     * @param astronomicalTwilightBegin
     * The astronomical_twilight_begin
     */
    public void setAstronomicalTwilightBegin(String astronomicalTwilightBegin) {
        this.astronomicalTwilightBegin = astronomicalTwilightBegin;
    }

    /**
     *
     * @return
     * The astronomicalTwilightEnd
     */
    public String getAstronomicalTwilightEnd() {
        return astronomicalTwilightEnd;
    }

    /**
     *
     * @param astronomicalTwilightEnd
     * The astronomical_twilight_end
     */
    public void setAstronomicalTwilightEnd(String astronomicalTwilightEnd) {
        this.astronomicalTwilightEnd = astronomicalTwilightEnd;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
