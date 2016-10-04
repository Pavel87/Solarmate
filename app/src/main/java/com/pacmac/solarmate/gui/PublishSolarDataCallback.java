package com.pacmac.solarmate.gui;

import com.pacmac.solarmate.model.SolarDataObject;

/**
 * Created by pacmac on 2016-09-26.
 */


public interface PublishSolarDataCallback {

    void dataReady(int day, SolarDataObject solarData);
    void gecodingCompleted(int day, String[] address);
}
