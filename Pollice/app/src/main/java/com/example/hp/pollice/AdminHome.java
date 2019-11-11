package com.example.hp.pollice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

public class AdminHome extends AppCompatActivity {
    private GridView gridView;
    static final String[] MOBILE_OS = new String[] {
            "Users", "Thana", "My Complain", "Complain For Other","Immediate Complain"};
    static final String[] MOBILE = new String[] {
            "10022", "100","2001", "5674", "98765" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new AdminGridAdapter(this, MOBILE_OS, MOBILE));
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(null).setTitle("Closing App Warning!!").setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                AdminHome.this.finishAffinity();     //minimum sdk 16
                System.exit(0);
            }
        }).setNegativeButton("No", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.admn_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.new_password_save){
//            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
//            SaveBtn();
        }else if (item.getItemId()==R.id.adminSignOut){
            new SQLiteDatabaseHelper(getApplicationContext()).drop();
            Intent i =new Intent(getApplicationContext(), loginActivity.class);
            startActivity(i);
        }else if(item.getItemId() == android.R.id.home){
            new AlertDialog.Builder(this).setIcon(null).setTitle("Closing App Warning!!").setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                    AdminHome.this.finishAffinity();     //minimum sdk 16
                    System.exit(0);
                }
            }).setNegativeButton("No", null).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
