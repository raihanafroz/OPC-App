package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.pollice.Adapter.PoliceStationListAdapter;

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

public class PoliceStationList extends AppCompatActivity {
    private String email,userId;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_line_mode);

        listview=(ListView)findViewById(R.id.listView);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            userId=extra.getString("Id");
        }

        if(new PublicClass().checkInternetConnection(PoliceStationList.this)) {
            new getDataOfStation().execute("Station Details",email);
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.police_station_list_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Police station list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Complain.class);
        i.putExtra("User_mail", email);
        i.putExtra("Id",userId);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private class getDataOfStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(PoliceStationList.this);
            pd.setTitle("Downloading Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
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
        PoliceStationList om=new PoliceStationList();
        private void parse(String data){
            int t=0;
            ArrayList<String> thanaName=new ArrayList<>();
            ArrayList<String> phoneNo=new ArrayList<>();
            try{
                JSONArray ja=new JSONArray(data);
                JSONObject jo=null;
                for(int position=0;position<ja.length();position++){
                    jo=ja.getJSONObject(position);
                    thanaName.add(jo.getString("thanaName"));

                    phoneNo.add(jo.getString("phoneNo"));

                    t++;


                }
            } catch (JSONException e) {
//                e.printStackTrace();
            }
            setListItem(thanaName,phoneNo);
            //Toast.makeText(getApplicationContext(), String.valueOf(t),Toast.LENGTH_SHORT).show();
        }
        public void setListItem(ArrayList<String> stationName,ArrayList<String> phoneNo){
            String listArray[] = new String[stationName.size()];
            String listArray1[] = new String[phoneNo.size()];
            listArray = stationName.toArray(listArray);
            listArray1 = phoneNo.toArray(listArray1);

            PoliceStationListAdapter adapter=new PoliceStationListAdapter(PoliceStationList.this, listArray, listArray1, R.drawable.bangladesh_police_government);
            listview.setAdapter(adapter);
        }
    }
}
