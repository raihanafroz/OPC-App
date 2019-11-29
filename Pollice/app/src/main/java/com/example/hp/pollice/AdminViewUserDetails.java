package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AdminViewUserDetails extends AppCompatActivity {
    String title, userId, userName, userEmail, userPhone, userAddress, userGender, userTime;
    TextView name, email, phone, address, gender, time, immediate, complain,otherComplain, total;
    Button suapenBtn;
    ImageView userPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user_details);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            title = extra.getString("Title");
            userId = extra.getString("UserID");
            userName = extra.getString("UserName");
            userPhone = extra.getString("UserPhone");
            userGender = extra.getString("UserGender");
            userEmail = extra.getString("UserEmail");
            userAddress = extra.getString("UserAddress");
            userTime = extra.getString("Time");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_user_details_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.admin_user_details_Name);
        email = (TextView) findViewById(R.id.admin_user_details__email);
        phone = (TextView) findViewById(R.id.admin_user_details_phone);
        address = (TextView) findViewById(R.id.admin_user_details_address);
        gender = (TextView) findViewById(R.id.admin_user_details_gender);
        time = (TextView) findViewById(R.id.admin_user_details_time);
        immediate = (TextView) findViewById(R.id.admin_user_details_immediate_count);
        complain = (TextView) findViewById(R.id.admin_user_details_complain_count);
        otherComplain = (TextView) findViewById(R.id.admin_user_details_complain_other_count);
        total = (TextView) findViewById(R.id.admin_user_details_complain_total_count);
        userPic = (ImageView) findViewById(R.id.admin_user_details_pic);
        suapenBtn = (Button) findViewById(R.id.user_suspend);
        suapenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteUser().execute("Delete User", userId);
            }
        });

        name.setText(userName);
        email.setText(userEmail);
        phone.setText(userPhone);
        address.setText(userAddress);
        gender.setText(userGender);
        time.setText(userTime);

        if(new PublicClass().checkInternetConnection(AdminViewUserDetails.this)) {
            new downloadImageFromServer(userEmail).execute();
            new gettingData().execute("UsaeData", userEmail);
        }


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

    private class downloadImageFromServer extends AsyncTask<Void, Void, Bitmap> {
        String imageName="";
        public downloadImageFromServer(String imageName){
            this.imageName=imageName;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url=new PublicClass().url_imgPath+imageName+".jpg";
            try {
                URLConnection connection=new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 60);
                connection.setReadTimeout(1000 * 60);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
            } catch (IOException e) {
//                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap!=null){
                userPic.setImageBitmap(bitmap);
            }
        }
    }

    public class gettingData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminViewUserDetails.this);
            pd.setTitle("Fetching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("UsaeData")) {
            String e_mail = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_userPage);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(e_mail, "UTF-8");
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
//                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
//                    e.printStackTrace();
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
            if (result.equals("Access defined.") || result.equals(null)) {
//                Toast.makeText(getApplicationContext(), "Sorry to login"+result, Toast.LENGTH_SHORT).show();
            } else {
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    jo=ja.getJSONObject(0);
                    immediate.setText(String.valueOf(jo.getString("immediate_complain")));
                    complain.setText(String.valueOf(jo.getString("complain_for_me")));
                    otherComplain.setText(String.valueOf(jo.getString("complain_for_others")));
                    total.setText(String.valueOf(jo.getString("total_complain")));
                } catch (JSONException e) {
//                    e.printStackTrace();
                }
            }
        }
    }

    public class deleteUser extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminViewUserDetails.this);
            pd.setTitle("Sending Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method == "Delete User") {
                String id = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_adminDeleteUser);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
//                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
//                    e.printStackTrace();
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
