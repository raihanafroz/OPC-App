package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

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

public class AdminViewComplainDetails extends AppCompatActivity {
    String title = "Loading...", stringComplainID ="", stringUserName ="", stringUserPhone ="", stringUserGender ="", stringUserEmail ="", stringUserAdress ="", stringStationName ="", stringComplainDetails ="", stringComplainStatus="";
    private TextView userName, userPhone, userGender, userEmail, userAdress, stationName, complainDetails;
    private MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_complain_details);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            title = extra.getString("Title");
            stringComplainID = extra.getString("ComplainID");
            stringUserName = extra.getString("UserName");
            stringUserPhone = extra.getString("UserPhone");
            stringUserGender = extra.getString("UserGender");
            stringUserEmail = extra.getString("UserEmail");
            stringUserAdress = extra.getString("UserAddress");
            stringStationName = extra.getString("StationName");
            stringComplainStatus = extra.getString("ComplainStatus");
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
        spinner = (MaterialSpinner) findViewById(R.id.status_spinner);

        userName.setText(stringUserName);
        userEmail.setText(stringUserEmail);
        userAdress.setText(stringUserAdress);
        userGender.setText(stringUserGender);
        userPhone.setText(stringUserPhone);
        stationName.setText(stringStationName);
        complainDetails.setText(stringComplainDetails);

        if(stringComplainStatus.equals("Reject")){
            spinner.setItems("Reject");
            spinner.setEnabled(false);
        }if(stringComplainStatus.equals("Done")){
            spinner.setItems("Done");
            spinner.setEnabled(false);
        }if(stringComplainStatus.equals("Working")){
            spinner.setItems("Sent", "Working", "Reject", "Done");
            spinner.setSelectedIndex(1);
        }if(stringComplainStatus.equals("Sent")){
            spinner.setItems("Sent", "Working", "Reject", "Done");
            spinner.setSelectedIndex(0);
        }
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String tableName = "tbl_complain3";
                if(title.equals("Immediate Complain")){
                    tableName = "tbl_complain1";
                }
                if(title.equals("Complain")){
                    tableName = "tbl_complain2";
                }
                new changeComplainStatus().execute("Change Status", stringComplainID, tableName, spinner.getText().toString());
            }
        });

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

    public class changeComplainStatus extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminViewComplainDetails.this);
            pd.setTitle("Sending Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method == "Change Status") {
                String id = voids[1];
                String tableName = voids[2];
                String status = voids[3];
                try {
                    URL url = new URL(new PublicClass().url_adminChangeComplainStatus);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                            URLEncoder.encode("table_name", "UTF-8") + "=" + URLEncoder.encode(tableName, "UTF-8") + "&" +
                            URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
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

                    Log.i("json data sending", respose+" <<==>>");

                    return respose;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    //return e.getMessage();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            if(result.equals("Successfully")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }else{
                Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}
