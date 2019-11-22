package com.example.hp.pollice.ui.policeStation;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.hp.pollice.SQLiteDatabaseHelper;
import com.example.hp.pollice.loginActivity;

public class AdminLogoutFragment extends Fragment {


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.xml.fragment_admin_logout, container, false);
        new SQLiteDatabaseHelper(getContext()).drop();
        Intent i =new Intent(getContext(), loginActivity.class);
        startActivity(i);
        return root;
    }
}