package com.pacmac.solarmate.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacmac.solarmate.util.Constants;
import com.pacmac.solarmate.R;

/**
 * Created by pacmac on 10/05/16.
 */

public class TomorrowFragment extends Fragment {

    public TomorrowFragment() {
    }

    public static TomorrowFragment newInstance(int sectionNumber) {
        TomorrowFragment fragment = new TomorrowFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        return view;
    }
}



