package com.example.hp.pollice.ui.policeStation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.R;
import com.example.hp.pollice.ui.slideshow.SlideshowViewModel;

public class ViewPoliceStationFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.xml.fragment_view_station_list, container, false);

        return root;
    }
}