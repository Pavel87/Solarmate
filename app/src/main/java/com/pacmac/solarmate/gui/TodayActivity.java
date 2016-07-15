package com.pacmac.solarmate.gui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import com.pacmac.solarmate.model.LocationObject;
import com.pacmac.solarmate.util.Constants;
import com.pacmac.solarmate.util.DownloadClient;
import com.pacmac.solarmate.R;
import com.pacmac.solarmate.util.LocationAware;

public class TodayActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private boolean isLocConnected = false;
    private LocationObject location = null;


    private BroadcastReceiver locationConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isLocConnected = intent.getBooleanExtra(Constants.INTENT_EXTRA_CONNECTED_FLAG, false);
            LocationAware.getLastLocation();
        }
    };

    private IntentFilter locIntentFilter = new IntentFilter(Constants.ACTION_LOC_CONNECTED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

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

                if (isLocConnected) {
                    LocationAware.getLastLocation();
                    location = new LocationObject(LocationAware.getLatitude(), LocationAware.getLongitude());

                    Log.d(Constants.TAG, "Lat:" + location.getLatitude() + " Long:" + location.getLongitude());
                    DownloadClient downloadClient = new DownloadClient(location.getLatitude(), location.getLongitude(), "today");
                    downloadClient.getSUNInformation();
                }
            }
        });

    }


    private void getLastLocation() {
        if (isLocConnected) {
            LocationAware.getLastLocation();
            location = new LocationObject(LocationAware.getLatitude(), LocationAware.getLongitude());
        }
        else{
            Log.d(Constants.TAG, "Location Service not connected");
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
                    return TodayFragment.newInstance(position);
                case 1:
                    return TomorrowFragment.newInstance(position);
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
