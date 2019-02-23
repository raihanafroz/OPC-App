package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


public class yourComplain extends AppCompatActivity {
    private String email="";
    private TextInputEditText currentAddress,cause;
    private EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_complain);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
        }

        currentAddress=(TextInputEditText) findViewById(R.id.yourCurrentAddress);
        cause=(TextInputEditText) findViewById(R.id.yourCause);
        description=(EditText) findViewById(R.id.yourDescription);
    }

    public class complain2 extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

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
            //String url_reg = "http://192.168.0.100/New_folder/Pollice/server/insert_data.php";
            String method = voids[0];
            if (method == "complain2") {
                String currentemail = voids[1];
                String complainAddress = voids[2];
                String complainCuse = voids[3];
                String complainDescription = voids[4];
                String currentTime = voids[5];
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
                            /*URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(currentlat, "UTF-8") + "&" +
                            URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(currentlon, "UTF-8") + "&" +*/
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
                //startActivity(new Intent(getApplicationContext(), complainActivity.class));
            } else {
                //Toast.makeText(getApplicationContext(), "Sorry to complain", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void go(View view) {
        //double loca[]=new publicClass().getLocation(this, yourComplain.this);
        //Toast.makeText(getApplicationContext(), new publicClass().getCurrentDate()+"Your Location is - \nLat: " + loca[0] + "Lon: " + loca[1]+"\n"+email, Toast.LENGTH_LONG).show();
        if(currentAddress.getText().toString().isEmpty() || cause.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "One or more field are empty.", Toast.LENGTH_SHORT).show();
        }else {
            new complain2().execute("complain2", email, currentAddress.getText().toString(), cause.getText().toString(), description.getText().toString(), new publicClass().getCurrentDate());
        }
    }

    public void clearField(View view) {
        Toast.makeText(getApplicationContext(), description.getText().toString(), Toast.LENGTH_SHORT).show();
        currentAddress.setText("");
        cause.setText("");
        description.setText("");
    }
}
