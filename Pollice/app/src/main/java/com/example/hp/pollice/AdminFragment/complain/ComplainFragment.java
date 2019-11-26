package com.example.hp.pollice.AdminFragment.complain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.example.hp.pollice.AdminComplainForOthersView;
import com.example.hp.pollice.AdminComplainView;
import com.example.hp.pollice.AdminImmediateComplainView;
import com.example.hp.pollice.R;

public class ComplainFragment extends Fragment {


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.xml.fragment_complain, container, false);

        LinearLayout immediateComplain = (LinearLayout) root.findViewById(R.id.admin_immediate_complain);
        LinearLayout complain = (LinearLayout) root.findViewById(R.id.admin_complain);
        LinearLayout complainForOther = (LinearLayout) root.findViewById(R.id.admin_complain_for_other);

        immediateComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdminImmediateComplainView.class));
            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdminComplainView.class));
            }
        });

        complainForOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdminComplainForOthersView.class));
            }
        });

        return root;
    }
}