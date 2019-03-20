package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class homeActivity extends AppCompatActivity {

    Context mContext;
    private TextView profileName,profileEmail,profileGender,profileAddress,profileContactNO,testCase;
    private ImageView profilePic;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext=this;

        profileName=(TextView)findViewById(R.id.profileName);
        profileEmail=(TextView)findViewById(R.id.profileEmail);
        profileGender=(TextView)findViewById(R.id.profileGender);
        profileAddress=(TextView)findViewById(R.id.profileAddress);
        profileContactNO=(TextView)findViewById(R.id.profileContactNO);
        profilePic=(ImageView)findViewById(R.id.profilePic);
        testCase=(TextView)findViewById(R.id.testCase);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            password=extra.getString("Password");
        }
        //Toast.makeText(getApplicationContext(), "E-mail: "+email+"\nPass: "+password, Toast.LENGTH_SHORT ).show();
        new setProfile().execute("Profile",email);
        new downloadImageFromServer(email).execute();
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(null).setTitle("Closing App Warning!!").setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                homeActivity.this.finishAffinity();     //minimum sdk 16
                System.exit(0);
            }
        }).setNegativeButton("No", null).show();
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
            Intent i =new Intent(getApplicationContext(), Change_Password.class);
            i.putExtra("Email", email);
            i.putExtra("From", "HomeActivity");
            startActivity(i);
        }else if (item.getItemId()==R.id.profileDetailsMenu){
            Intent i =new Intent(getApplicationContext(), changeDetails.class);
            i.putExtra("Email", email);
            i.putExtra("From", "HomeActivity");
            startActivity(i);
        }else if (item.getItemId()==R.id.profileSignOut){
            new SQLiteDatabaseHelper(getApplicationContext()).drop();
            Intent i =new Intent(getApplicationContext(), loginActivity.class);
            startActivity(i);
        }else if (item.getItemId()==R.id.share){
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sub="Auto Text Size";
            String body="Help for android development\n com.example.hp.autotextsize;";
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent,"Share With"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void goto_complaine(View view) {
        double loca[]=new publicClass().getLocation(mContext, homeActivity.this);
        if(loca[0]!=0 && loca[1]!=0){
            Intent i=new Intent(getApplicationContext(),complainActivity.class);
            i.putExtra("User_mail", email);
            i.putExtra("Password", password);
            i.putExtra("Latitude",loca[0]);
            i.putExtra("Longitude",loca[1]);
            startActivity(i);
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1], Toast.LENGTH_LONG).show();
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

    public void ChangePassBtn(View view) {
        Intent i=new Intent(getApplicationContext(), Change_Password.class);
        i.putExtra("Email", email);
        i.putExtra("From", "");
        startActivity(i);
    }

    public void goto_exit(View view) {
        moveTaskToBack(true);
    }

    private class setProfile extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(homeActivity.this);
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
                    URL url = new URL(new publicClass().url_profile);
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
                    profileName.setText(jo.getString("first_name")+" "+jo.getString("last_name"));
                    profileEmail.setText(jo.getString("e-mail"));
                    profileGender.setText(jo.getString("gender"));
                    profileAddress.setText(jo.getString("address"));
                    profileContactNO.setText(jo.getString("contact_number"));
                }else{
                    Toast.makeText(getApplicationContext(), "Many User found.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
            String url=new publicClass().url_imgPath+imageName+".JPG";
            try {
                URLConnection connection=new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 60);
                connection.setReadTimeout(1000 * 60);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
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
