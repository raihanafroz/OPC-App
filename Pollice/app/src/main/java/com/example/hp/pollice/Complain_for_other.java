package com.example.hp.pollice;

import android.app.ProgressDialog;
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
import java.util.List;

public class Complain_for_other extends AppCompatActivity {
    public List<String> item = new ArrayList<String>();
    private String email = "";
    private TextInputEditText name, phone, address, complainCuse, complainAddress;
    private EditText complainDescription;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_for_other);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            email = extra.getString("User_mail");
        }

        name=(TextInputEditText) findViewById(R.id.name);
        phone=(TextInputEditText) findViewById(R.id.phone);
        address=(TextInputEditText) findViewById(R.id.addres);
        complainCuse=(TextInputEditText) findViewById(R.id.complainCuse);
        complainAddress=(TextInputEditText) findViewById(R.id.complainAddress);
        complainDescription=(EditText) findViewById(R.id.complainDescription);
        spinner = (Spinner) findViewById(R.id.thana);
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        new getDataOfStation().execute("Station Details", email);
    }

    public void send_complain(View view) {
        new complain_for_other().execute("complain3", email, spinner.getSelectedItem().toString(), name.getText().toString(), phone.getText().toString(), address.getText().toString(),
                complainCuse.getText().toString(), complainAddress.getText().toString(), complainDescription.getText().toString(), new publicClass().getCurrentDate() );
    }

    public void clearField(View view) {

    }

    public class complain_for_other extends AsyncTask<String, Void, String> {

        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Complain_for_other.this);
            pd.setTitle("Sending Complain");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String url_reg = new publicClass().url_complain2;
//                    Log.i("json data sending", voids);
            String method = voids[0];

            if (method == "complain3") {
                String email = voids[1];
                String thanaName = voids[2];
                String name = voids[3];
                String phone = voids[4];
                String address = voids[5];
                String complainCuse = voids[6];
                String complainAddress = voids[7];
                String complainDescription = voids[8];
                String currentTime = voids[9];
                try {
                    URL url = new URL(new publicClass().url_complain3);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("thanaName", "UTF-8") + "=" + URLEncoder.encode(thanaName, "UTF-8") + "&" +
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&" +
                            URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                            URLEncoder.encode("complainCuse", "UTF-8") + "=" + URLEncoder.encode(complainCuse, "UTF-8") + "&" +
                            URLEncoder.encode("complainAddress", "UTF-8") + "=" + URLEncoder.encode(complainAddress, "UTF-8") + "&" +
                            URLEncoder.encode("complainDescription", "UTF-8") + "=" + URLEncoder.encode(complainDescription, "UTF-8") + "&" +
                            URLEncoder.encode("currentTime", "UTF-8") + "=" + URLEncoder.encode(currentTime, "UTF-8");
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
//            Log.i("json result", ">"+result+"<");

            if (result.equals("Successfully Complained\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), complainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Sorry to complain", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }

    }



    private class getDataOfStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Complain_for_other.this);
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
            if ((!result.isEmpty())) {

//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    for (int position = 0; position < ja.length(); position++) {
                        jo = ja.getJSONObject(position);
                        item.add(jo.getString("thanaName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Complain_for_other.this,
                    android.R.layout.simple_spinner_item, item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}

