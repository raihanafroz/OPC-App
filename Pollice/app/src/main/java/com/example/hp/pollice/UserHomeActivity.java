package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

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


public class UserHomeActivity extends AppCompatActivity {

    GridView gridView;
    String email = "";
    String password = "";
    static final String[] dataType = new String[] {"Immediate Complain", "Complain For Me", "Complain For Other", "Total Complain"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        final Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            password=extra.getString("Password");
        }
        if(new publicClass().checkInternetConnection(UserHomeActivity.this)) {
            new gettingData().execute("UsaePage");
        }


            gridView = (GridView) findViewById(R.id.user_gridview);

        LinearLayout profilePage = (LinearLayout) findViewById(R.id.user_profile);
        LinearLayout complainPage = (LinearLayout) findViewById(R.id.user_complain);

        profilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new publicClass().checkInternetConnection(UserHomeActivity.this)) {
                    Intent i = new Intent(getApplicationContext(), PhofileActivity.class);
                    i.putExtra("Email", email);
                    i.putExtra("Password", password);
                    startActivity(i);
                }
            }
        });

        complainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new publicClass().checkInternetConnection(UserHomeActivity.this)) {
                    double loca[] = new publicClass().getLocation(UserHomeActivity.this, UserHomeActivity.this);
                    if (loca[0] != 0 && loca[1] != 0) {
                        Intent i = new Intent(getApplicationContext(), complainActivity.class);
                        i.putExtra("User_mail", email);
                        i.putExtra("Password", password);
                        i.putExtra("Latitude", loca[0]);
                        i.putExtra("Longitude", loca[1]);
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1], Toast.LENGTH_LONG).show();
                    }
                }else{
//                    new Alert_Builder().settingAlert(getApplicationContext(), UserHomeActivity.this, false);
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(null).setTitle("Warning!!").setMessage("Are you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                UserHomeActivity.this.finishAffinity();     //minimum sdk 16
                System.exit(0);
            }
        }).setNegativeButton("No", null).show();
    }



    public class gettingData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UserHomeActivity.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("UsaePage")) {
                try {
                    URL url = new URL(new publicClass().url_userPage);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (result.equals("Access defined.") || result.equals(null)) {
                data.add("0");data.add("0");data.add("0");data.add("0");
//                Toast.makeText(getApplicationContext(), "Sorry to login"+result, Toast.LENGTH_SHORT).show();
            } else {
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    jo=ja.getJSONObject(0);
                    data.add(String.valueOf(jo.getString("immediate_complain")));
                    data.add(String.valueOf(jo.getString("complain_for_me")));
                    data.add(String.valueOf(jo.getString("complain_for_others")));
                    data.add(String.valueOf(jo.getString("total_complain")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String data1[] = new String[data.size()];
            data1= data.toArray(data1);
            gridView.setAdapter(new UserGridAdapter(UserHomeActivity.this, dataType, data1));

        }
    }
}
