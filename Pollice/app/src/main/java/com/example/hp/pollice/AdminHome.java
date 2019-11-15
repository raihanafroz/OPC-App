package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {
    GridView gridView;
    static final String[] MOBILE_OS = new String[] {
            "Users", "Police Station", "Immediate Complain", "Complain For Themself", "Complain For Other"};
    String[] MOBILE = new String[] {
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

        new gettingData().execute("adminPage");

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
//            new AlertDialog.Builder(this).setIcon(null).setTitle("Closing App Warning!!").setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
//                    AdminHome.this.finishAffinity();     //minimum sdk 16
//                    System.exit(0);
//                }
//            }).setNegativeButton("No", null).show();
            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    public class gettingData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminHome.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("adminPage")) {
                try {
                    URL url = new URL(new publicClass().url_adminPage);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
                    bw.write(data);
                    bw.flush();
                    bw.close();
                    os.close();

                    InputStream is = huc.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String respose = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        respose += line;
                    }
                    br.close();
                    is.close();
                    huc.disconnect();
                    Log.i("json result res", respose);
                    return respose;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    //return e.getMessage();
                }
            }
            Log.i("json result", "null");
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            ArrayList<String> data=new ArrayList<>();

            if (result.equals("Failed") || result.equals(null)) {
                data.add("0");data.add("0");data.add("0");data.add("0");data.add("0");
//                Toast.makeText(getApplicationContext(), "Sorry to login"+result, Toast.LENGTH_SHORT).show();
            } else {
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    jo=ja.getJSONObject(0);

                    data.add(String.valueOf(jo.getString("users")));
                    data.add(String.valueOf(jo.getString("police_station")));
                    data.add(String.valueOf(jo.getString("immediate_complain")));
                    data.add(String.valueOf(jo.getString("complain_for_me")));
                    data.add(String.valueOf(jo.getString("complain_for__others")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String data1[] = new String[data.size()];
            data1= data.toArray(data1);
            gridView.setAdapter(new AdminGridAdapter(AdminHome.this, MOBILE_OS, data1));
        }
    }

}
