package com.example.hp.pollice;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView vr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signin=(Button)findViewById(R.id.sign_in);
        //signup=(Button)findViewById(R.id.sign_up);

        vr = (TextView) findViewById(R.id.version);

        if(new publicClass().checkInternetConnection(getApplicationContext())){
            SQLiteDatabaseHelper sdh = new SQLiteDatabaseHelper(getApplicationContext());
            SQLiteDatabase ad = sdh.getWritableDatabase();
            checkUserData(true);
        }else {
            vr.setText("No Internet");
            //new Alert_Builder().settingAlert(this.getApplicationContext(),MainActivity.this);
            ///checkUserData(false);
            Toast.makeText(getApplicationContext(),"No Internet.",Toast.LENGTH_SHORT).show();

            new Alert_Builder().settingAlert(this,MainActivity.this,true);
        }
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(null).setTitle("Closing App Warning!!").setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                MainActivity.this.finishAffinity();     //minimum sdk 16
                System.exit(0);
            }
        }).setNegativeButton("No", null).show();
    }
    public void signin_page(View view) {
        if(new publicClass().checkInternetConnection(getApplicationContext())){
            Intent i = new Intent(getApplicationContext(), loginActivity.class);
            startActivity(i);
        }else {
            new Alert_Builder().settingAlert(this,MainActivity.this,false);
        }
    }

    public void signup_page(View view) {
        if(new publicClass().checkInternetConnection(getApplicationContext())){
            Intent i = new Intent(getApplicationContext(), registerActivity.class);
            startActivity(i);
        }else {
            new Alert_Builder().settingAlert(this,MainActivity.this, false);
        }
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
    public void checkUserData(boolean userType){
        Cursor cursor=new SQLiteDatabaseHelper(this).check_user();
        if(cursor!=null){
            if(cursor.getCount()==1){
                while(cursor.moveToNext()){
                    String email=cursor.getString(0);
                    String pass=cursor.getString(1);
                    //String userName=cursor.getString(2);
                    Intent i;
                    if(userType) {
                        i = new Intent(getApplicationContext(), PhofileActivity.class);
                    }else{
                        i = new Intent(getApplicationContext(), OffLineMode.class);
                        //i.putExtra("UserName",userName);
                    }
                    i.putExtra("Email",email);
                    i.putExtra("Password",pass);
                    startActivity(i);
                }
            }
        }
    }

}
