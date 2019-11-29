package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class Profile extends AppCompatActivity {

    Context mContext;
    private TextView profileName,profileEmail,profileGender,profileAddress,profileContactNO,testCase;
    private ImageView profilePic;
    private String email,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext=this;

        profileName=(TextView)findViewById(R.id.profileName);
        profileEmail=(TextView)findViewById(R.id.profileEmail);
        profileGender=(TextView)findViewById(R.id.profileGender);
        profileAddress=(TextView)findViewById(R.id.profileAddress);
        profileContactNO=(TextView)findViewById(R.id.profileContactNO);
        profilePic=(ImageView)findViewById(R.id.profilePic);
//        testCase=(TextView)findViewById(R.id.testCase);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            userId=extra.getString("Id");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Toast.makeText(getApplicationContext(), "E-mail: "+email+"\nPass: "+userId, Toast.LENGTH_SHORT ).show();
        if(new PublicClass().checkInternetConnection(Profile.this)) {
            new setProfile().execute("Profile", email);
            new downloadImageFromServer(email).execute();
        }else{
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), UserHome.class);
        i.putExtra("Email", email);
        i.putExtra("Id", userId);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.home_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.profileEditPicMenu){
            Intent i =new Intent(getApplicationContext(), ChangePhoto.class);
            i.putExtra("Email", email);
            i.putExtra("From", "HomeActivity");
            startActivity(i);
        }else if (item.getItemId()==R.id.profileEditPassMenu){
            Intent i =new Intent(getApplicationContext(), ChangePassword.class);
            i.putExtra("Email", email);
            i.putExtra("Id", userId);
            i.putExtra("From", "HomeActivity");
            startActivity(i);
        }else if (item.getItemId()==R.id.profileDetailsMenu){
            Intent i =new Intent(getApplicationContext(), ChangeDetails.class);
            i.putExtra("Email", email);
            i.putExtra("Id", userId);
            i.putExtra("From", "HomeActivity");
            startActivity(i);
        }else if (item.getItemId()==R.id.profileSignOut){
            new SQLiteDatabaseHelper(getApplicationContext()).drop();
            Intent i =new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }else if (item.getItemId()==R.id.share){
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sub="Auto Text Size";
            String body="Help for android development\n com.example.hp.autotextsize;";
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent,"Share With"));
        }else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goto_complaine(View view) {
        if(new PublicClass().checkInternetConnection(this)) {
            double loca[] = new PublicClass().getLocation(mContext, Profile.this);
            if (loca[0] != 0 && loca[1] != 0) {
                Intent i = new Intent(getApplicationContext(), Complain.class);
                i.putExtra("User_mail", email);
                i.putExtra("Id", userId);
                i.putExtra("Latitude", loca[0]);
                i.putExtra("Longitude", loca[1]);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1], Toast.LENGTH_LONG).show();
            }
        }else{
            new AlertBuilder().settingAlert(this, Profile.this, false);
        }
    }


    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    private float distanceBetween(double userLat, double userLng,
                                                     double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (AVERAGE_RADIUS_OF_EARTH_KM * c);
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private class setProfile extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Profile.this);
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
                    profileName.setText(jo.getString("first_name")+" "+jo.getString("last_name"));
                    profileEmail.setText(jo.getString("e-mail"));
                    profileGender.setText(jo.getString("gender"));
                    profileAddress.setText(jo.getString("address"));
                    profileContactNO.setText(jo.getString("contact_number"));
                }else{
                    Toast.makeText(getApplicationContext(), "Many User found.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
//                e.printStackTrace();
            }
        }
    }

    private class downloadImageFromServer extends AsyncTask<Void, Void, Bitmap>{
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
                //Toast.makeText(SeeImage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap!=null){
                profilePic.setImageBitmap(bitmap);
            }
        }
    }
}
