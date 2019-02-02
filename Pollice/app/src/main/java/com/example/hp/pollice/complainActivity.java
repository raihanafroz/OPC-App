package com.example.hp.pollice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class complainActivity extends AppCompatActivity {
    private String email;
    private double lat,lon;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            lat=extra.getDouble("Latitude");
            lon=extra.getDouble("Longitude");
        }
        //btnSend=(Button)findViewById(R.id.btnSend);

    }

    public void btnSend_click(View view) {
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "Lon: " + lon, Toast.LENGTH_LONG).show();
    }

    public void showComplainList(View view) {
        Toast.makeText(getApplicationContext(), "List", Toast.LENGTH_LONG).show();
    }

    public void complainForOther(View view) {
        Toast.makeText(getApplicationContext(), "Other", Toast.LENGTH_LONG).show();
    }

    public void yourComplain(View view) {
        Toast.makeText(getApplicationContext(), "Self", Toast.LENGTH_LONG).show();
    }

    public void immediateComplain(View view) {
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "Lon: " + lon, Toast.LENGTH_LONG).show();
    }
}
