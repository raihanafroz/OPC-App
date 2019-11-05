package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class yourComplain extends AppCompatActivity {
    public List<String> item = new ArrayList<String>();
    private String email="";
    private TextInputEditText currentAddress,cause;
    private EditText description;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
        }

        spinner = (Spinner) findViewById(R.id.thana);
        currentAddress=(TextInputEditText) findViewById(R.id.yourCurrentAddress);
        cause=(TextInputEditText) findViewById(R.id.yourCause);
        description=(EditText) findViewById(R.id.yourDescription);


//        ArrayAdapter<String>adapter = new ArrayAdapter<String>(yourComplain.this,
//                android.R.layout.simple_spinner_item,city);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);

//        GetJsonString();
        new getDataOfStation().execute("Station Details",email);
    }
    private class getDataOfStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(yourComplain.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }
        @Override
        protected String doInBackground(String... voids) {
            String url_profile = new publicClass().url_thanaList;
            String method = voids[0];
            if (method.equals("Station Details")) { //        select data from database
                String user_email = voids[1];
                try {
                    URL url = new URL(new publicClass().url_stationDetails);
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
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(yourComplain.this,
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
            pd = new ProgressDialog(yourComplain.this);
            pd.setTitle("Sending Complain");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String url_reg = new publicClass().url_complain2;
            String method = voids[0];
            if (method == "complain2") {
                String currentemail = voids[1];
                String complainAddress = voids[2];
                String complainCuse = voids[3];
                String complainDescription = voids[4];
                String currentTime = voids[5];
                String thanaName = voids[6];
                try {
                    URL url = new URL(new publicClass().url_complain2);
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
                            URLEncoder.encode("thanaName", "UTF-8") + "=" + URLEncoder.encode(thanaName, "UTF-8");
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
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), complainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Sorry to complain", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }

    }





    public void go(View view) {

        //double loca[]=new publicClass().getLocation(this, yourComplain.this);
        //Toast.makeText(getApplicationContext(), new publicClass().getCurrentDate()+"Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();

       /* if(currentAddress.getText().toString().isEmpty() || cause.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "One or more field are empty.", Toast.LENGTH_SHORT).show();
        }else {
        }*/
            new complain2().execute("complain2", email, currentAddress.getText().toString(),
                    cause.getText().toString(), description.getText().toString(), new publicClass().getCurrentDate(),
                    spinner.getSelectedItem().toString());

//        GeocodingLocation locationAddress = new GeocodingLocation();
//        locationAddress.getAddressFromLocation(currentAddress.getText().toString(), getApplicationContext(), new GeocoderHandler());
//        Toast.makeText(getApplicationContext(), "Station: "+spinner.getSelectedItem().toString()+"Address: "+currentAddress.getText().toString()+" \n "+cause.getText().toString()+"\n"+description.getText().toString()+"\n Location: "+loca, Toast.LENGTH_SHORT).show();
    }



    public void clearField(View view) {
        Toast.makeText(getApplicationContext(), description.getText().toString(), Toast.LENGTH_SHORT).show();
        currentAddress.setText("");
        cause.setText("");
        description.setText("");
    }

    String loca="";

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //latLongTV.setText(locationAddress);
            loca=locationAddress;
        }
    }

}
