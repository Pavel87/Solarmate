package com.pacmac.solarmate.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pacmac.solarmate.R;
import com.pacmac.solarmate.model.SolarDataObject;
import com.pacmac.solarmate.util.Utility;

/**
 * Created by pacmac on 10/05/16.
 */

public class TodayFragment extends Fragment {

    private SolarDataObject solarDataObject = null;

    private TextView sunrise;
    private TextView sunset;
    private TextView timestamp;
    private TextView nextEvent;
    private TextView gpsToCity;

    private int fragID;

    public TodayFragment(){

    }

    public TodayFragment(int sectionNumber) {
        this.fragID = sectionNumber;
    }

//    public static TodayFragment newInstance(int sectionNumber) {
//        TodayFragment fragment = new TodayFragment();
//        Bundle args = new Bundle();
//        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.fragment_today, container,  false);

        sunrise = (TextView) view.findViewById(R.id.sunrise);
        sunset = (TextView) view.findViewById(R.id.sunset);
        timestamp = (TextView) view.findViewById(R.id.lastUpdate);
        nextEvent = (TextView) view.findViewById(R.id.sunEvent);
        gpsToCity = (TextView) view.findViewById(R.id.gpsToCity);


        return view;
    }

    public void updateSolarData(SolarDataObject sunObject){

        long lastUpdate = sunObject.getTimestamp();

        //sunrise = (TextView) findViewById(R.id.sunrise);
        this.solarDataObject = sunObject;
        if (sunrise != null) {
            sunrise.setText(Utility.getShortTimeForEvent(Utility.getDateFromTimeString(sunObject.getSunrise(), lastUpdate)));
            sunset.setText((Utility.getShortTimeForEvent(Utility.getDateFromTimeString(sunObject.getSunset(), lastUpdate))));
            timestamp.setText(Utility.getFormatedDateTimeLong(lastUpdate));
            gpsToCity.setText(sunObject.getGeoArea());
            nextEvent.setText(Utility.getDiffTimeForNextEvent(Utility.getDateFromTimeString(sunObject.getSunrise(), lastUpdate)));
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
