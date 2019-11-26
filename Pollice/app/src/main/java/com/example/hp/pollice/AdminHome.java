package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hp.pollice.Adapter.AdminGridAdapter;

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

public class AdminHome extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    GridView gridView;
    TextView navHeaderName, navHeaderEmail;
    ImageView navHeaderImage;
    static final String[] MOBILE_OS = new String[] {
            "Users", "Police Station", "Immediate Complain", "Complain For Themself", "Complain For Other"};
    String[] MOBILE = new String[] {
            "10022", "100","2001", "5674", "98765" };
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            password=extra.getString("Password");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View navHeader = navigationView.getHeaderView(0);

        navHeaderImage = (ImageView) navHeader.findViewById(R.id.nav_header_image);
        navHeaderName = (TextView) navHeader.findViewById(R.id.nav_header_name);
        navHeaderEmail = (TextView) navHeader.findViewById(R.id.nav_header_email);
        navHeaderEmail.setText(email);

        new setProfile().execute("Profile", email);
        new downloadImageFromServer(email).execute();


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_complain, R.id.nav_admin_logout, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // app bar configuer
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.admin_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

//        gridView = (GridView) findViewById(R.id.gridview);

//        new gettingData().execute("adminPage");

    }

//    /*
//    * creating app bar right menu
//    * */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater mi=getMenuInflater();
//        mi.inflate(R.menu.admin_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    /*
    * app bar left menu clickable and drawer_layout visiable
    * */

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.profileEditPassMenu){
            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
//            SaveBtn();
        }else if (item.getItemId()==R.id.nav_admin_logout){
            new SQLiteDatabaseHelper(getApplicationContext()).drop();
            Intent i =new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
//        else if(item.getItemId() == android.R.id.home){
//
//            Toast.makeText(getApplicationContext(), "savedvwefsdcvsa fa fadsfsf afa fafafa ", Toast.LENGTH_SHORT).show();
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(null).setTitle("Warning!!").setMessage("Are you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                AdminHome.this.finishAffinity();     //minimum sdk 16
                System.exit(0);
            }
        }).setNegativeButton("No", null).show();
    }


    private class setProfile extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminHome.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
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
                    navHeaderName.setText(jo.getString("first_name")+" "+jo.getString("last_name"));
                    navHeaderEmail.setText(jo.getString("e-mail"));
//                    profileGender.setText(jo.getString("gender"));
//                    profileAddress.setText(jo.getString("address"));
//                    profileContactNO.setText(jo.getString("contact_number"));
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
            String url=new PublicClass().url_imgPath+imageName+".jpg";
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
                navHeaderImage.setImageBitmap(bitmap);
            }
        }
    }


    public class gettingData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminHome.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("adminPage")) {
                try {
                    URL url = new URL(new PublicClass().url_adminPage);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
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
                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
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
            ArrayList<String> data=new ArrayList<>();

            if (result.equals("Failed") || result.equals(null)) {
                data.add("0");data.add("0");data.add("0");data.add("0");data.add("0");
//                Toast.makeText(getApplicationContext(), "Sorry to login"+result, Toast.LENGTH_SHORT).show();
            } else {
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    jo=ja.getJSONObject(0);

                    data.add(String.valueOf(jo.getString("users")));
                    data.add(String.valueOf(jo.getString("police_station")));
                    data.add(String.valueOf(jo.getString("immediate_complain")));
                    data.add(String.valueOf(jo.getString("complain_for_me")));
                    data.add(String.valueOf(jo.getString("complain_for__others")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String data1[] = new String[data.size()];
            data1= data.toArray(data1);
            gridView.setAdapter(new AdminGridAdapter(AdminHome.this, MOBILE_OS, data1));
        }
    }

}
