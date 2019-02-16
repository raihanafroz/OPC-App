package com.example.hp.pollice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    TextView vr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signin=(Button)findViewById(R.id.sign_in);
        //signup=(Button)findViewById(R.id.sign_up);

        vr = (TextView) findViewById(R.id.version);
            SQLiteDatabaseHelper sdh = new SQLiteDatabaseHelper(getApplicationContext());
            SQLiteDatabase ad = sdh.getWritableDatabase();
            checkuser_data();
    }

    public void signin_page(View view) {
        Intent i = new Intent(getApplicationContext(), loginActivity.class);
        startActivity(i);
    }

    public void signup_page(View view) {
        Intent i = new Intent(getApplicationContext(), registerActivity.class);
        startActivity(i);
    }

    public void drop(View view) {
        new SQLiteDatabaseHelper(getApplicationContext()).drop();
    }

    public void create(View view) {






        //new SQLiteDatabaseHelper(getApplicationContext()).create("haha", "hahaha");

            //Call a number

        /*Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+8801797325129"));
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);*/

    }
    public void checkuser_data(){
        Cursor cursor=new SQLiteDatabaseHelper(this).check_user();
        if(cursor!=null){
            if(cursor.getCount()==1){
                while(cursor.moveToNext()){
                    String email=cursor.getString(0);
                    String pass=cursor.getString(1);
                    Intent i = new Intent(getApplicationContext(),homeActivity.class);
                    i.putExtra("Email",email);
                    i.putExtra("Password",pass);
                    startActivity(i);
                }
            }
        }
    }
}
