package com.example.hp.pollice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



public class yourComplain extends AppCompatActivity {
    private String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
        }
    }

    public void go(View view) {
        double loca[]=new publicClass().getLocation(this, yourComplain.this);

        Toast.makeText(getApplicationContext(), new publicClass().getCurrentDate()+"Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();
    }
}
