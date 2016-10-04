package com.pacmac.solarmate.gui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pacmac.solarmate.R;
import com.pacmac.solarmate.model.LocationObject;
import com.pacmac.solarmate.model.SolarDataObject;
import com.pacmac.solarmate.util.Constants;
import com.pacmac.solarmate.util.DownloadClient;
import com.pacmac.solarmate.util.LocationAware;
import com.pacmac.solarmate.util.Utility;

public class SolarActivity extends AppCompatActivity implements PublishSolarDataCallback {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private LocationObject location = null;
    private boolean locationAvailable = false;

    private SolarDataObject today = null;
    private SolarDataObject tomorrow = null;


    private BroadcastReceiver locationConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle locBundle = LocationAware.getLastLocation();
            if (locBundle != null) {
                location = new LocationObject(locBundle.getDouble(Constants.LAT_LOC), locBundle.getDouble(Constants.LONG_LOC));
                locationAvailable = true;
                downloadSunData(getCurrentFragment());
            } else {
                locationAvailable = false;
            }
        }
    };

    private IntentFilter locIntentFilter = new IntentFilter(Constants.ACTION_LOC_CONNECTED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        // Load Shared Pref Location if available:
        String lat = Utility.getPreferenceString(getApplicationContext(), Constants.LAT_LOC);
        String lon = Utility.getPreferenceString(getApplicationContext(), Constants.LONG_LOC);
        if (lat != null || lon != null) {
            location = new LocationObject(Double.parseDouble(lat), Double.parseDouble(lon));
            locationAvailable = true;
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationConnReceiver, locIntentFilter);
        LocationAware.init(getApplicationContext());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d(Constants.TAG, "i: " + i);
                if (LocationAware.isGooglePlayServicesConnected()) {
                    LocationAware.getLastLocation();
                    location = new LocationObject(LocationAware.getLatitude(), LocationAware.getLongitude());
                    downloadSunData(getCurrentFragment());
                }
            }
        });

    }

    private int getCurrentFragment() {
        return mViewPager.getCurrentItem();
    }


    private void getLastLocation() {
        if (LocationAware.isGooglePlayServicesConnected()) {
            LocationAware.getLastLocation();
            location = new LocationObject(LocationAware.getLatitude(), LocationAware.getLongitude());
            locationAvailable = true;
        } else {
            locationAvailable = false;
            Log.d(Constants.TAG, "Location Service not connected");
        }
    }


    private void downloadSunData(int fragID) {      // 0 today // 1 tomorrow
        // TODO network check is missing now
        if (!locationAvailable)
            return;

        //get fresher loc update if possible
        getLastLocation();
        DownloadClient downloadClient = new DownloadClient(getApplicationContext(), location.getLatitude(), location.getLongitude(), fragID);
        downloadClient.setDataListener(this);
        downloadClient.getSUNInformation();
        downloadClient.translateCoordinates();
    }


    @Override
    public void dataReady(int day, SolarDataObject solarData) {
        if (solarData == null)
            return;
        today = solarData;
        updateFragment(day, solarData);

        // create timestamp and save it to preferences so we do not do network calls in short intervals
    }

    @Override
    public void gecodingCompleted(final int day, final String[] address) {
        if(address == null)
            return;
        if (today != null) {
            today.setGeoArea(address);
            updateFragment(day, today);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gecodingCompleted(day, address);
                    Log.d(Constants.TAG, "geocodes waiting to be inserted");
                }
            }, 2000);
        }
    }

    private void updateFragment(int fragID, SolarDataObject data) {

        Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        if (fragment instanceof TodayFragment && fragID == 0) {  // TODO and also fragID == 0
            ((TodayFragment) fragment).updateSolarData(data);

        } else {
            ((TomorrowFragment) fragment).updateSolarData(data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_today, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // SECTION PAGE ADAPTER //
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TodayFragment(position);
                case 1:
                    return new TomorrowFragment(position);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODAY";
                case 1:
                    return "TOMORROW";
            }
            return null;
        }
    }
}
