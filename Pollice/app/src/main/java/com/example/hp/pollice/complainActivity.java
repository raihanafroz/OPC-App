package com.example.hp.pollice;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class complainActivity extends AppCompatActivity {
    private String email,password;
    private double lat,lon, loca[];
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
            password=extra.getString("Password");
            lat=extra.getDouble("Latitude");
            lon=extra.getDouble("Longitude");
        }
        //btnSend=(Button)findViewById(R.id.btnSend);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_CODE_ASK_PERMISSIONS=123;
            ActivityCompat.requestPermissions(complainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

    }


    public void btnSend_click(View view) {
        //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "Lon: " + lon, Toast.LENGTH_LONG).show();

        //new complain1().execute("complain", email, email, String.valueOf(lat), String.valueOf(lon));

    }

    public void goToListView(View view) {
        Intent i = new Intent(getApplicationContext(), OffLineMode.class);
        i.putExtra("Email",email);
        i.putExtra("Password",password);
        startActivity(i);
    }


    public class complain1 extends AsyncTask<String, Void, String> {

        ProgressDialog pd;
        String eemail="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(complainActivity.this);
            pd.setTitle("Sending Complain");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_reg = "http://192.168.0.100/New_folder/Pollice/server/insert_data.php";

            String method = voids[0];
            if (method == "complain") {
                String currentemail = voids[1];
                String currentlat = voids[2];
                String currentlon = voids[3];
                String currentTime = voids[4];
                eemail=currentemail;
                try {
                    URL url = new URL(new publicClass().url_complain1);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(currentemail, "UTF-8") + "&" +
                            URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(currentlat, "UTF-8") + "&" +
                            URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(currentlon, "UTF-8") + "&" +
                            URLEncoder.encode("currentTime", "UTF-8") + "=" + URLEncoder.encode(currentTime, "UTF-8");
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
            if (result.equals("Successfully Complained.")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                if(eemail!=""){
                    new getPhoneNo().execute("PhoneNo",eemail);
                }

                //startActivity(new Intent(getApplicationContext(), complainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Sorry to complain", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class getPhoneNo extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(complainActivity.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_getPhoneNO = "http://192.168.0.100/New_folder/Pollice/server/profile.php";
            String method = voids[0];
            if (method.equals("PhoneNo")) { //        select data from database
                String user_email = voids[1];
                try {
                    URL url = new URL(new publicClass().url_getPhoneNO);
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
            if ((!result.isEmpty())) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("01797325129", null, "Hi, i'm attacked by someone at Lat: "+loca[0]+"& Long: "+loca[1]+". Please, help me.", null, null);
//                callPolice(result, complainActivity.this);

            } else {
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
        }
    }


        public void showComplainList(View view) {
//            Toast.makeText(getApplicationContext(), "List", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), Complain_list.class);
            i.putExtra("User_mail", email);
            startActivity(i);
        }

        public void complainForOther(View view) {
            Intent i = new Intent(getApplicationContext(), Complain_for_other.class);
            i.putExtra("User_mail", email);
            startActivity(i);
//            Toast.makeText(getApplicationContext(), "Other", Toast.LENGTH_LONG).show();
        }


        public void yourComplain(View view) {
            Intent i = new Intent(getApplicationContext(), yourComplain.class);
            i.putExtra("User_mail", email);
            startActivity(i);
            // Toast.makeText(getApplicationContext(), "Distence: "+new publicClass().calculateDistanceInMeter(23.748791, 90.407925, 23.745631, 90.406095), Toast.LENGTH_LONG).show();
        }


    public void immediateComplain(View view) {
        loca=new publicClass().getLocation(this, complainActivity.this);
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();
        new complain1().execute("complain", email, String.valueOf(lat), String.valueOf(lon), new publicClass().getCurrentDate());
    }



    void callPolice(final String number, Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        TextView title = new TextView(context);
        title.setText("Warning! !! !!!");
        title.setBackgroundResource(R.drawable.alert_background);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.green));
        title.setTextSize(40);
        builder1.setCustomTitle(title);
        builder1.setMessage("Do you want to call?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+88"+number));//change the number
                startActivity(callIntent);
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        TextView textView = (TextView) alert11.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }
}
