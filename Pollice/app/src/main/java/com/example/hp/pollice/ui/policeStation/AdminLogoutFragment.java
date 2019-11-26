package com.example.hp.pollice.ui.policeStation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.R;
import com.example.hp.pollice.SQLiteDatabaseHelper;
import com.example.hp.pollice.Login;

public class AdminLogoutFragment extends Fragment {


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.xml.fragment_admin_logout, container, false);
        new SQLiteDatabaseHelper(getContext()).drop();
        Intent i =new Intent(getContext(), Login.class);
        startActivity(i);
        return root;
    }
}