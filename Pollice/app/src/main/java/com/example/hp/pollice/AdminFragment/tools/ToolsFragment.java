package com.example.hp.pollice.AdminFragment.tools;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.R;

public class ToolsFragment extends Fragment {


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.xml.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);

        return root;
    }
}