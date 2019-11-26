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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

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

public class ComplainForOther extends AppCompatActivity {
    public List<String> item = new ArrayList<String>();
    private String email = "";
    private String password = "";
    private TextInputEditText name, phone, address, complainCuse, complainAddress;
    private EditText complainDescription;
    private MaterialSpinner spinner;
    private Button saveBtn, clearBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_for_other);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
            password=extra.getString("Password");
        }

        if(new PublicClass().checkInternetConnection(ComplainForOther.this)) {
            new getDataOfStation().execute("Station Details", email);
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.complain_for_others_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("For others");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=(TextInputEditText) findViewById(R.id.complain_for_others_name);
        phone=(TextInputEditText) findViewById(R.id.complain_for_others_phone);
        address=(TextInputEditText) findViewById(R.id.complain_for_others_address);
        complainCuse=(TextInputEditText) findViewById(R.id.complain_for_others_cause);
        complainAddress=(TextInputEditText) findViewById(R.id.complain_for_others_accurrence_area);
        complainDescription=(EditText) findViewById(R.id.complain_for_others_description);
        spinner = (MaterialSpinner) findViewById(R.id.thana);
        spinner.setFocusableInTouchMode(true);
        spinner.setFocusable(true);
        spinner.requestFocus();
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                spinner.setError(null);
            }
        });

        saveBtn = (Button) findViewById(R.id.complain_for_others_save);
        clearBtn = (Button) findViewById(R.id.complain_for_others_clear);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeComplain();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                phone.setText("");
                address.setText("");
                complainCuse.setText("");
                complainAddress.setText("");
                complainDescription.setText("");
                name.setText("");
                spinner.setSelectedIndex(0);
                spinner.setFocusableInTouchMode(true);
                spinner.setFocusable(true);
                spinner.requestFocus();
                spinner.performClick();
            }
        });


        /*
         *   checking address valid or not
         **/
        complainCuse.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    complainCuse.setError(null);
                }
                if(!complainCuse.getText().toString().isEmpty()){
                    complainCuse.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking cause valid or not
         **/
        complainAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    complainAddress.setError(null);
                }
                if(!complainAddress.getText().toString().isEmpty()){
                    complainAddress.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking description valid or not
         **/
        complainDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    complainDescription.setError(null);
                }
                if(!complainDescription.getText().toString().isEmpty()){
                    complainDescription.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


//        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Complain.class);
        i.putExtra("User_mail", email);
        i.putExtra("Password",password);
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

    public void makeComplain() {
        double loca[]=new PublicClass().getLocation(this, ComplainForOther.this);
//        Toast.makeText(getApplicationContext(), new PublicClass().getCurrentDate()+"Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();
        if(spinner.getText().toString().equals("Select Police Station")){
            spinner.setError("Wrong option selected");
            spinner.setFocusableInTouchMode(true);
            spinner.setFocusable(true);
            spinner.requestFocus();
            spinner.performClick();
        }else {
            if (complainCuse.getText().toString().isEmpty()) {
                complainCuse.setError("Addrss required");
                complainCuse.requestFocus();
            } else {
                if (complainAddress.getText().toString().isEmpty()) {
                    complainAddress.setError("Cause required");
                    complainAddress.requestFocus();
                } else {
                    if (complainDescription.getText().toString().isEmpty()) {
                        complainDescription.setError("Description required");
                        complainDescription.requestFocus();
                    } else {
                        new complain_for_other().execute("complain3", email, spinner.getText().toString(), name.getText().toString(), phone.getText().toString(), address.getText().toString(),
                                complainCuse.getText().toString(), complainAddress.getText().toString(), complainDescription.getText().toString(), new PublicClass().getCurrentDate(), String.valueOf(loca[0]), String.valueOf(loca[1]));

                    }
                }
            }
        }

    }

    public class complain_for_other extends AsyncTask<String, Void, String> {

        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ComplainForOther.this);
            pd.setTitle("Sending Complain");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String url_reg = new PublicClass().url_complain2;
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
                String latitude = voids[10];
                String longitude = voids[11];
                try {
                    URL url = new URL(new PublicClass().url_complain3);
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
                            URLEncoder.encode("currentTime", "UTF-8") + "=" + URLEncoder.encode(currentTime, "UTF-8") + "&" +
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
//            Log.i("json result", ">"+result+"<");

            if (result.equals("Successfully Complained\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t")){
                onBackPressed();
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
            pd = new ProgressDialog(ComplainForOther.this);
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComplainForOther.this,
                    android.R.layout.simple_spinner_item, item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}

