package com.example.hp.pollice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class AdminEditPoliceStation extends AppCompatActivity {
    private String stationId,stationName,stationPhone,stationLatitude,stationLongitude;
    private TextInputEditText inputName, inputPhone, inputLatitude, inputLongitude;
    private Button save;
    String namePattern = "[a-zA-Z\\s]+";
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_police_station);
        this.activity = this;
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            stationId=extra.getString("station_id");
            stationName=extra.getString("station_name");
            stationPhone=extra.getString("station_phone");
            stationLatitude=extra.getString("station_latitude");
            stationLongitude=extra.getString("station_longitude");
        }
        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_edit_police_station_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Police Station");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputName = (TextInputEditText) findViewById(R.id.admin_edit_police_station_name);
        inputPhone = (TextInputEditText) findViewById(R.id.admin_edit_police_station_phone);
        inputLatitude = (TextInputEditText) findViewById(R.id.admin_edit_police_station_latitude);
        inputLongitude = (TextInputEditText) findViewById(R.id.admin_edit_police_station_longitude);

        inputName.setText(stationName);
        inputPhone.setText(stationPhone);
        inputLatitude.setText(stationLatitude);
        inputLongitude.setText(stationLongitude);

        save = (Button) findViewById(R.id.admin_edit_police_station_btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStation();
            }
        });

        /*
         *   checking thanaName valid or not
         **/
        inputName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    inputName.setError(null);
                }else {

                    if (charSequence.toString().trim().matches(namePattern)) {
                        inputName.setError(null);
                    } else {
                        if(inputName.getText().toString().isEmpty()){
                            inputName.setError(null);
                        }else {
                            inputName.setError("Wrong input");
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking phoneNo valid or not
         **/
        inputPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    inputPhone.setError(null);
                }else {
                    if (charSequence.toString().length() >= 9) {
                        inputPhone.setError(null);
                    } else {
                        if(inputPhone.getText().toString().isEmpty()){
                            inputPhone.setError(null);
                        }else {
                            inputPhone.setError("Minimum 9 digit");
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking latitude valid or not
         **/
        inputLatitude.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    inputLatitude.setError(null);
                }else {
                    if (!charSequence.toString().isEmpty()) {
                        inputLatitude.setError(null);
                    } else {
                        inputLatitude.setError("Value required");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking logitude valid or not
         **/
        inputLongitude.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    inputLongitude.setError(null);
                }else {
                    if (!charSequence.toString().isEmpty()) {
                        inputLongitude.setError(null);
                    } else {
                        inputLongitude.setError("Value required");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.app_bar_save_btn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.app_bar_save_btn){
            updateStation();
        }else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStation() {
        if(checkValidation()) {
            new changePoliceStation().execute("UpdatePoliceStation", stationId, inputName.getText().toString(), inputPhone.getText().toString(), inputLatitude.getText().toString(), inputLongitude.getText().toString());
        }
    }

    boolean checkValidation(){
        boolean b = false;
        if(new PublicClass().checkInternetConnection(AdminEditPoliceStation.this)) {
            if (inputName.getText().toString().isEmpty()) {
                inputName.requestFocus();
            } else {
                inputName.setError(null);
                if (inputPhone.getText().toString().length() >= 9) {
                    inputPhone.setError(null);
                    if(inputLatitude.getText().toString().isEmpty()){
                        inputLatitude.requestFocus();
                    }else{
                        inputLatitude.setError(null);
                        if(inputLongitude.getText().toString().isEmpty()){
                            inputLongitude.requestFocus();
                        }else{
                            b = true;
                        }
                    }
                } else {
                    inputPhone.setError("Minimum 9 digit");
                    inputPhone.requestFocus();
                }
            }
        }
        return b;
    }


    public class changePoliceStation extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminEditPoliceStation.this);
            pd.setTitle("Updating");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_changePassword = "http://192.168.0.100/New_folder/Pollice/server/changePassword.php";
            String method = voids[0];
            if (method.equals("UpdatePoliceStation")) { //     Change Details
                String id = voids[1];
                String name = voids[2];
                String phone = voids[3];
                String latitude = voids[4];
                String longitude = voids[5];
                try {
                    URL url = new URL(new PublicClass().url_update_station);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&" +
                            URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&" +
                            URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
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
            Log.d("json res", result);
            if (result.equals("Successfully Change")) {
                Toast.makeText(getApplicationContext(), "Update Successfull",Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                Snackbar.make(new View(getApplicationContext()), result, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }


}
