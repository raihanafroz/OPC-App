package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class changeDetails extends AppCompatActivity {
    private TextInputEditText fname,lname,contactNO,address;
    private String email="",state="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            state=extra.getString("From");
        }
        fname=(TextInputEditText)findViewById(R.id.CngFirstName);
        lname=(TextInputEditText)findViewById(R.id.CngLastName);
        contactNO=(TextInputEditText)findViewById(R.id.CngContactNO);
        address=(TextInputEditText)findViewById(R.id.CngAddress);
    }

    public void save(View view) {
        new changeProfileDetails().execute("ChangeDetails",email,fname.getText().toString(),lname.getText().toString(),contactNO.getText().toString(),address.getText().toString());
    }

    public void cancel(View view) {
        finish();

    }

    public class changeProfileDetails extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(changeDetails.this);
            pd.setTitle("Tring to change");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_changePassword = "http://192.168.0.100/New_folder/Pollice/server/changePassword.php";
            String method = voids[0];
            if (method.equals("ChangeDetails")) { //     Change Details
                String email = voids[1];
                String fname = voids[2];
                String lname = voids[3];
                String contactNumber = voids[4];
                String address = voids[5];
                try {
                    URL url = new URL(new publicClass().url_changeDetails);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
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
            if (result.equals("Successfully Change")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(changeDetails.this, homeActivity.class);
                i.putExtra("Email",email);
                i.putExtra("Password","");
                startActivity(i);
            } else if(result.equals("Nothing to change")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                /*Intent i=new Intent(changeDetails.this, homeActivity.class);
                i.putExtra("Email",email);
                i.putExtra("Password","");
                startActivity(i);*/
                finish();
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
