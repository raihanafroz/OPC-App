package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
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
import java.net.URLEncoder;
import java.util.ArrayList;

public class Complain_list extends AppCompatActivity {
    private String email;
    private String password;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_list);

        listview=(ListView)findViewById(R.id.complainList);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("User_mail");
            password=extra.getString("Password");
        }

        if(new publicClass().checkInternetConnection(Complain_list.this)) {
            new getDataOfComplain().execute("Complain List",email);
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.complain_list_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complain list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), complainActivity.class);
        i.putExtra("User_mail", email);
        i.putExtra("Password",password);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class getDataOfComplain extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Complain_list.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("Complain List")) { //        select data from database
                String user_email = voids[1];
                try {
                    URL url = new URL(new publicClass().url_complainList);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8");
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
            Log.i("json result  ", result);
            if ((!result.isEmpty())){

                parse(result);

            }else{
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
        }
//        OffLineMode om=new OffLineMode();
        private void parse(String data){
            ArrayList<String> serial=new ArrayList<>();
            ArrayList<String> type=new ArrayList<>();
            ArrayList<String> email=new ArrayList<>();
            ArrayList<String> cause=new ArrayList<>();
            ArrayList<String> time=new ArrayList<>();
            ArrayList<String> complainNo=new ArrayList<>();
            try{
                JSONArray ja=new JSONArray(data);
                JSONObject jo=null;
                for(int position=0;position<ja.length();position++){
                    jo=ja.getJSONObject(position);
                    serial.add(String.valueOf(position+1));

                    type.add(jo.getString("type"));
                    email.add(jo.getString("email"));
                    cause.add(jo.getString("cause"));
                    time.add(jo.getString("time"));
                    complainNo.add(jo.getString("complainNo"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setListItem(serial,type, email, cause, time, complainNo);
            //Toast.makeText(getApplicationContext(), String.valueOf(t),Toast.LENGTH_SHORT).show();
        }
        public void setListItem(ArrayList<String> serial,ArrayList<String> type, ArrayList<String> email, ArrayList<String> cause, ArrayList<String> time, ArrayList<String> complainNo){
            String listArray[] = new String[serial.size()];
            String listArray1[] = new String[type.size()];
            String listArray2[] = new String[email.size()];
            String listArray3[] = new String[cause.size()];
            String listArray4[] = new String[time.size()];
            String listArray5[] = new String[complainNo.size()];
            listArray = serial.toArray(listArray);
            listArray1 = type.toArray(listArray1);
            listArray2 = email.toArray(listArray2);
            listArray3 = cause.toArray(listArray3);
            listArray4 = time.toArray(listArray4);
            listArray5 = complainNo.toArray(listArray5);

            ComplainListAdapter adapter=new ComplainListAdapter(Complain_list.this, listArray, listArray1, listArray2, listArray3, listArray4, listArray5);
            listview.setAdapter(adapter);
        }
    }
}
