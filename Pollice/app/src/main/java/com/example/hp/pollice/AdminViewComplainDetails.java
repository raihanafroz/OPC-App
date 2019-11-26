package com.example.hp.pollice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AdminViewComplainDetails extends AppCompatActivity {
    String title = "Loading...", stringUserName ="", stringUserPhone ="", stringUserGender ="", stringUserEmail ="", stringUserAdress ="", stringStationName ="", stringComplainDetails ="";
    private TextView userName, userPhone, userGender, userEmail, userAdress, stationName, complainDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_complain_details);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            title = extra.getString("Title");
            stringUserName = extra.getString("UserName");
            stringUserPhone = extra.getString("UserPhone");
            stringUserGender = extra.getString("UserGender");
            stringUserEmail = extra.getString("UserEmail");
            stringUserAdress = extra.getString("UserAddress");
            stringStationName = extra.getString("StationName");
            stringComplainDetails = extra.getString("ComplainDetails");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_view_complain_details_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userName = (TextView) findViewById(R.id.admin_view_complain_details_user_name);
        userEmail = (TextView) findViewById(R.id.admin_view_complain_details_user_email);
        userAdress = (TextView) findViewById(R.id.admin_view_complain_details_user_address);
        userGender = (TextView) findViewById(R.id.admin_view_complain_details_user_gender);
        userPhone = (TextView) findViewById(R.id.admin_view_complain_details_user_phone);
        stationName = (TextView) findViewById(R.id.admin_view_complain_details_station_name);
        complainDetails = (TextView) findViewById(R.id.admin_view_complain_details_complain_details);


        userName.setText(stringUserName);
        userEmail.setText(stringUserEmail);
        userAdress.setText(stringUserAdress);
        userGender.setText(stringUserGender);
        userPhone.setText(stringUserPhone);
        stationName.setText(stringStationName);
        complainDetails.setText(stringComplainDetails);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
