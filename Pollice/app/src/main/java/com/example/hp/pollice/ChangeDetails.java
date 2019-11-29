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

public class ChangeDetails extends AppCompatActivity {
    TextInputEditText fname,lname,contactNO,address;
    String email="",state="", userId="";
    String namePattern = "[a-zA-Z\\s]+";
    String addressPattern = "^[#.0-9a-zA-Z\\s,-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            userId=extra.getString("Id");
            state=extra.getString("From");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.change_password_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(new PublicClass().checkInternetConnection(ChangeDetails.this)) {
            new setProfile().execute("Profile", email);
        }

        fname=(TextInputEditText)findViewById(R.id.changeFirstName);
        lname=(TextInputEditText)findViewById(R.id.changeLastName);
        contactNO=(TextInputEditText)findViewById(R.id.changePhone);
        address=(TextInputEditText)findViewById(R.id.changeAddress);


        /*
         *   checking firstname valid or not
         **/
        fname.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    fname.setError(null);
                }else {
                    if (charSequence.toString().length() > 1) {
                        if (charSequence.toString().trim().matches(namePattern)) {
                            fname.setError(null);
                        } else {
                            fname.setError("Wrong input");
                        }
                    } else {
                        fname.setError("Minimum 2 characters");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking lastname valid or not
         **/
        lname.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    lname.setError(null);
                }else {
                    if (charSequence.toString().length() > 2) {
                        if (charSequence.toString().trim().matches(namePattern)) {
                            lname.setError(null);
                        } else {
                            lname.setError("Wrong input");
                        }
                    } else {
                        lname.setError("Minimum 3 characters");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking Contact Number minimu 11 digit or not
         **/
        contactNO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    contactNO.setError(null);
                }else {
                    if (charSequence.toString().length() >= 11) {
                        contactNO.setError(null);
                    } else {
                        contactNO.setError("Minimum 11 characters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking address valid or not
         **/
        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    address.setError(null);
                }else {
                    if (charSequence.toString().trim().matches(addressPattern)) {
                        address.setError(null);
                    } else {
                        address.setError("Wrong input");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
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
            if(new PublicClass().checkInternetConnection(ChangeDetails.this)) {
                if(validationField()){
                    new changeProfileDetails().execute("ChangeDetails",userId,fname.getText().toString(),lname.getText().toString(),contactNO.getText().toString(),address.getText().toString());
                }
            }
        }else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validationField() {
        int count = 0;
        if(fname.getText().toString().length() < 2 && fname.getText().toString().trim().matches(namePattern)){
            fname.setError("Wrong input");
            fname.requestFocus();
            return false;
        }else{ count++;}
        if(lname.getText().toString().length() < 3 && lname.getText().toString().trim().matches(namePattern)){
            lname.setError("Wrong input");
            lname.requestFocus();
            return false;
        }else{ count++;}
        if(address.getText().toString().trim().matches(addressPattern)) {
            count++;
        } else {
            address.setError("Wrong input");
            address.requestFocus();
            return false;
        }
        if(contactNO.getText().toString().length() < 11 ){
            contactNO.setError("Required minimum 11 characters");
            contactNO.requestFocus();
            return false;
        }else{ count++;}
        if(count == 4){
            return true;
        }
        return false;
    }


    public class changeProfileDetails extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ChangeDetails.this);
            pd.setTitle("Trying to change");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("ChangeDetails")) { //     Change Details
                String id = voids[1];
                String fname = voids[2];
                String lname = voids[3];
                String contactNumber = voids[4];
                String address = voids[5];
                try {
                    URL url = new URL(new PublicClass().url_changeDetails);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                            URLEncoder.encode("user_firstName", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" +
                            URLEncoder.encode("user_lastName", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8") + "&" +
                            URLEncoder.encode("user_contactNumber", "UTF-8") + "=" + URLEncoder.encode(contactNumber, "UTF-8") + "&" +
                            URLEncoder.encode("user_address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
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
//                    e.printStackTrace();
//                    return e.getMessage();
                } catch (IOException e) {
//                    e.printStackTrace();
//                    return e.getMessage();
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

            if (result.equals("Successfully Change")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ChangeDetails.this, Profile.class);
                i.putExtra("Email",email);
                i.putExtra("Id",userId);
                startActivity(i);
            } else if(result.equals("Nothing to change")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                /*Intent i=new Intent(ChangeDetails.this, Profile.class);
                i.putExtra("Email",email);
                i.putExtra("Password","");
                startActivity(i);*/
                finish();
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class setProfile extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ChangeDetails.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_profile = "http://192.168.0.100/New_folder/Pollice/server/profile.php";
            String method = voids[0];
            if (method.equals("Profile")) { //        select data from database
                String user_email = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_profile);
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
            if ((!result.isEmpty())){
                parse(result);
            }else{
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
        }
        private void parse(String data){
            try{
                JSONArray ja=new JSONArray(data);
                JSONObject jo=null;
                if(ja.length()==1){
                    jo=ja.getJSONObject(0);
                    fname.setText(jo.getString("first_name"));
                    lname.setText(jo.getString("last_name"));
                    address.setText(jo.getString("address"));
                    contactNO.setText(jo.getString("contact_number"));
                }else{
                    Toast.makeText(getApplicationContext(), "Many User found.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
