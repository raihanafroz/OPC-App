package com.example.hp.pollice.ui.addStation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.hp.pollice.R;

public class AddStationFragment extends Fragment {

    private AddStationViewModel addStationViewModel;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addStationViewModel =
                ViewModelProviders.of(this).get(AddStationViewModel.class);
        View root = inflater.inflate(R.xml.fragment_add_station, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        addStationViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}