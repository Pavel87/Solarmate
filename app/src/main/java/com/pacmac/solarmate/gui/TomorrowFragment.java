package com.pacmac.solarmate.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.pacmac.solarmate.R;
import com.pacmac.solarmate.model.SolarDataObject;
import com.pacmac.solarmate.util.Utility;

/**
 * Created by pacmac on 10/05/16.
 */

public class TomorrowFragment extends Fragment {

    private SolarDataObject solarDataObject = null;

    private TextView sunrise;
    private TextView sunset;
    private TextView timestamp;
    private TextView sunEvent;
    private TextView gpsToCity;

    long sunsetTS = 0l;
    long sunriseTS = 0l;
    boolean isAnimationRunning = false;

    final Handler handler = new Handler();
    Runnable updateTimeRunnable;

    private int fragID;

    public TomorrowFragment() {
    }

    public TomorrowFragment(int sectionNumber) {
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

        View view = layoutInflater.inflate(R.layout.fragment_tomorrow, container, false);

        sunrise = (TextView) view.findViewById(R.id.sunrise);
        sunset = (TextView) view.findViewById(R.id.sunset);
        timestamp = (TextView) view.findViewById(R.id.lastUpdate);
        sunEvent = (TextView) view.findViewById(R.id.sunEvent);
        gpsToCity = (TextView) view.findViewById(R.id.gpsToCity);


        return view;
    }

    public void updateSolarData(SolarDataObject sunObject) {
        this.solarDataObject = sunObject;

        long lastUpdate = sunObject.getTimestamp();
        sunriseTS = Utility.getDateFromTimeString(1, sunObject.getSunrise(), lastUpdate);
        sunsetTS = Utility.getDateFromTimeString(1, sunObject.getSunset(), lastUpdate);

        sunrise.setText(Utility.getShortTimeForEvent(sunriseTS));
        sunset.setText(Utility.getShortTimeForEvent(sunsetTS));
        timestamp.setText(Utility.getFormatedDateTimeLong(lastUpdate));
        gpsToCity.setText(sunObject.getGeoArea());
        updateViewTimer();
    }


    public void updateViewTimer() {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        final Animation finalAnim = anim;

        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {


                long timeNow = System.currentTimeMillis();
                long diff = 0l;
                long sunriseDiff = sunriseTS - timeNow;

                if (sunriseDiff > 0l){
                    diff = sunriseTS - timeNow;
                } else {
                    diff = sunsetTS - timeNow;
                }

                if (diff < 5*60*1000){

                    if(!isAnimationRunning) {
                        isAnimationRunning = true;
                        sunEvent.startAnimation(finalAnim);
                    }
                    sunEvent.setTextColor(getResources().getColor(R.color.colorTimerCritical));
                }
                else if (diff < 0) {
                    if (isAnimationRunning) {
                        isAnimationRunning = false;
                        sunEvent.clearAnimation();
                    }
                    sunEvent.setTextColor(getResources().getColor(R.color.colorTimerNegative));

                }
                else {
                    if (isAnimationRunning) {
                        isAnimationRunning = false;
                        sunEvent.clearAnimation();
                    }
                    sunEvent.setTextColor(getResources().getColor(R.color.colorTimerNormal));
                }

                sunEvent.setText(getNextEventTime(diff));
                handler.postDelayed(this, 500);
            }
        };
        handler.post(updateTimeRunnable);
    }

    private String getNextEventTime(long diff) {
        return Utility.getDiffTimeForNextEvent(diff);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (updateTimeRunnable != null && solarDataObject != null) {
            handler.post(updateTimeRunnable);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (updateTimeRunnable != null)
            handler.removeCallbacks(updateTimeRunnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (updateTimeRunnable != null)
            handler.removeCallbacks(updateTimeRunnable);
    }

}



