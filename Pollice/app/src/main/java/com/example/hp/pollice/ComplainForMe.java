package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComplainForMe extends AppCompatActivity {
    public List<String> item = new ArrayList<String>();
    private String email="";
    private String userId="";
    private TextInputEditText currentAddress,cause;
    private EditText description;
    private MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
            userId=extra.getString("Id");
        }

        if(new PublicClass().checkInternetConnection(ComplainForMe.this)) {
            new getDataOfStation().execute("Station Details",email);
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.complain_for_me_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("For me");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (MaterialSpinner) findViewById(R.id.thana);
        currentAddress=(TextInputEditText) findViewById(R.id.yourCurrentAddress);
        cause=(TextInputEditText) findViewById(R.id.yourCause);
        description=(EditText) findViewById(R.id.yourDescription);

        spinner.setFocusableInTouchMode(true);
        spinner.setFocusable(true);
        spinner.requestFocus();
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            spinner.setError(null);
            }
        });



        /*
         *   checking address valid or not
         **/
        currentAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    currentAddress.setError(null);
                }
                if(!currentAddress.getText().toString().isEmpty()){
                    currentAddress.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking cause valid or not
         **/
        cause.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    cause.setError(null);
                }
                if(!cause.getText().toString().isEmpty()){
                    cause.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking description valid or not
         **/
        description.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    description.setError(null);
                }
                if(!description.getText().toString().isEmpty()){
                    description.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Complain.class);
        i.putExtra("User_mail", email);
        i.putExtra("Id",userId);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.app_bar_save_btn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if (item.getItemId()==R.id.app_bar_save_btn){
           makeComplain();
        }
        return super.onOptionsItemSelected(item);
    }


    public void go(View view) {
        makeComplain();
    }


    public void makeComplain(){
        double loca[]=new PublicClass().getLocation(this, ComplainForMe.this);
//        Toast.makeText(getApplicationContext(), new PublicClass().getCurrentDate()+"Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();
        if(spinner.getText().toString().equals("Select Police Station")){
            spinner.setError("Wrong option selected");
            spinner.setFocusableInTouchMode(true);
            spinner.setFocusable(true);
            spinner.requestFocus();
            spinner.performClick();
        }else{
            if(currentAddress.getText().toString().isEmpty()){
                currentAddress.setError("Addrss required");
                currentAddress.requestFocus();
            }else{
                if(cause.getText().toString().isEmpty()){
                    cause.setError("Cause required");
                    cause.requestFocus();
                }else {
                    if(description.getText().toString().isEmpty()){
                        description.setError("Description required");
                        description.requestFocus();
                    } else{
                        new complain2().execute("complain2", email, currentAddress.getText().toString(),
                                cause.getText().toString(), description.getText().toString(), new PublicClass().getCurrentDate(),
                                spinner.getText().toString(), String.valueOf(loca[0]), String.valueOf(loca[1]));
                    }
                }
            }
        }
    }


    public void clearField(View view) {
//        Toast.makeText(getApplicationContext(), description.getText().toString(), Toast.LENGTH_SHORT).show();
        currentAddress.setText("");
        cause.setText("");
        description.setText("");
        spinner.setSelectedIndex(0);
        spinner.setFocusableInTouchMode(true);
        spinner.setFocusable(true);
        spinner.requestFocus();
        spinner.performClick();

    }


    private class getDataOfStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ComplainForMe.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }
        @Override
        protected String doInBackground(String... voids) {
            String url_profile = new PublicClass().url_thanaList;
            String method = voids[0];
            if (method.equals("Station Details")) { //        select data from database
                String user_email = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_stationDetails);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8");
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
            item.add("Select Police Station");
            if ((!result.isEmpty())){
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    for(int position=0;position<ja.length();position++){
                        jo=ja.getJSONObject(position);
                        item.add(jo.getString("thanaName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(ComplainForMe.this,
                android.R.layout.simple_spinner_item, item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    public class complain2 extends AsyncTask<String, Void, String> {

        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ComplainForMe.this);
            pd.setTitle("Sending Complain");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String url_reg = new PublicClass().url_complain2;
            String method = voids[0];
            if (method == "complain2") {
                String currentemail = voids[1];
                String complainAddress = voids[2];
                String complainCuse = voids[3];
                String complainDescription = voids[4];
                String currentTime = voids[5];
                String thanaName = voids[6];
                String latitude = voids[7];
                String longitude = voids[8];
                try {
                    URL url = new URL(new PublicClass().url_complain2);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(currentemail, "UTF-8") + "&" +
                            URLEncoder.encode("complainAddress", "UTF-8") + "=" + URLEncoder.encode(complainAddress, "UTF-8") + "&" +
                            URLEncoder.encode("complainCuse", "UTF-8") + "=" + URLEncoder.encode(complainCuse, "UTF-8") + "&" +
                            URLEncoder.encode("complainDescription", "UTF-8") + "=" + URLEncoder.encode(complainDescription, "UTF-8") + "&" +
                            URLEncoder.encode("currentTime", "UTF-8") + "=" + URLEncoder.encode(currentTime, "UTF-8") + "&" +
                            URLEncoder.encode("thanaName", "UTF-8") + "=" + URLEncoder.encode(thanaName, "UTF-8") + "&" +
                            URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&" +
                            URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
//                    Log.i("json data sending", data);

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
            Log.i("json result", ">"+result+"<");

            if (result.equals("Successfully Complained\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")){
                Toast.makeText(getApplicationContext(), "Successfully Sent", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry to complain", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }

    }





//
//    String loca="";
//
//    private class GeocoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            String locationAddress;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress = bundle.getString("address");
//                    break;
//                default:
//                    locationAddress = null;
//            }
//            //latLongTV.setText(locationAddress);
//            loca=locationAddress;
//        }
//    }

}
