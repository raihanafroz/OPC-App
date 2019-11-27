package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.pollice.Adapter.AdminImmediateComplainListAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

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
import java.util.List;

public class AdminImmediateComplainView extends AppCompatActivity {
    public List<String> item = new ArrayList<String>();

    ListView lv;
    private MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_immedite_complain_view);

        lv = (ListView) findViewById(R.id.admin_immediate_complain_listview);
        spinner = (MaterialSpinner) findViewById(R.id.admin_immediate_complain_spinner);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(spinner.getText().toString().equals("Select a user")){
                    new getImmediateComplainData().execute("View Immediate Complain", "");
                }else{
                    new getImmediateComplainData().execute("View Immediate Complain", spinner.getText().toString());
                }
//                Toast.makeText(getApplicationContext(), spinner.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });


        if(new PublicClass().checkInternetConnection(AdminImmediateComplainView.this)) {
            new getDataOfUser().execute("User List");
            new getImmediateComplainData().execute("View Immediate Complain", "");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_immediate_complain_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Immediate Complain");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private class getDataOfUser extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminImmediateComplainView.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String url_profile = new PublicClass().url_thanaList;
            String method = voids[0];
            if (method.equals("User List")) {
                try {
                    URL url = new URL(new PublicClass().url_adminUserList);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("GET");

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
            item.add("Select a user");
            if ((!result.isEmpty())) {
                try {
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    for (int position = 0; position < ja.length(); position++) {
                        jo = ja.getJSONObject(position);
                        item.add(jo.getString("e-mail"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No User found.", Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminImmediateComplainView.this,
                    android.R.layout.simple_spinner_item, item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    public class getImmediateComplainData extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminImmediateComplainView.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("View Immediate Complain")) {
                String email = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_adminImmediateComplainList);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
            if (result == null || result.isEmpty() || result.equals("Access denied")) {
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                init(result);
            }
        }

    }

    public void init(String data) {
        ArrayList<String> listID = new ArrayList<>();
        ArrayList<String> listUserName = new ArrayList<>();
        ArrayList<String> listEmail= new ArrayList<>();
        ArrayList<String> listLatitude= new ArrayList<>();
        ArrayList<String> listLongitude= new ArrayList<>();
        ArrayList<String> listTime= new ArrayList<>();
        ArrayList<String> listStationName = new ArrayList<>();
        ArrayList<String> listStationId = new ArrayList<>();
        ArrayList<String> listAddress = new ArrayList<>();
        ArrayList<String> listGender = new ArrayList<>();
        ArrayList<String> listUserPhone = new ArrayList<>();

        listID.add("#ID");
        listUserName.add("User Name");
        listEmail.add("Email");
        listLatitude.add("Latitude");
        listLongitude.add("Longitude");
        listTime.add("Time");
        listStationName.add("Station Name");
        listStationId.add("Station Id");
        listAddress.add("Address");
        listGender.add("Gender");
        listUserPhone.add("User Phone No");
        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                listID.add(String.valueOf(i+1));
//                Log.d("json data", object.getString("email"));
                listUserName.add(object.getString("userName"));
                listEmail.add(object.getString("email"));
                listLatitude.add(object.getString("latitude"));
                listLongitude.add(object.getString("longitude"));
                listTime.add(object.getString("complainTime"));
                listStationName.add(object.getString("thanaName"));
                listStationId.add(object.getString("thanaId"));
                listAddress.add(object.getString("address"));
                listGender.add(object.getString("gender"));
                listUserPhone.add(object.getString("contact_number"));
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        String listArrayID[] = new String[listID.size()];
        String listArrayUserName[] = new String[listUserName.size()];
        String listArrayEmail[] = new String[listEmail.size()];
        String listArrayLatitude[] = new String[listLatitude.size()];
        String listArrayLongitude[] = new String[listLongitude.size()];
        String listArrayTime[] = new String[listTime.size()];
        String listArrayStationName[] = new String[listStationName.size()];
        String listArrayStationId[] = new String[listStationId.size()];
        String listArrayAddress[] = new String[listAddress.size()];
        String listArrayGender[] = new String[listGender.size()];
        String listArrayUserPhone[] = new String[listUserPhone.size()];

        listArrayID = listID.toArray(listArrayID);
        listArrayUserName = listUserName.toArray(listArrayUserName);
        listArrayEmail = listEmail.toArray(listArrayEmail);
        listArrayLatitude = listLatitude.toArray(listArrayLatitude);
        listArrayLongitude = listLongitude.toArray(listArrayLongitude);
        listArrayTime = listTime.toArray(listArrayTime);
        listArrayStationName = listStationName.toArray(listArrayStationName);
        listArrayStationId = listStationId.toArray(listArrayStationId);
        listArrayAddress = listAddress.toArray(listArrayAddress);
        listArrayGender = listGender.toArray(listArrayGender);
        listArrayUserPhone = listUserPhone.toArray(listArrayUserPhone);

        AdminImmediateComplainListAdapter adapter=new AdminImmediateComplainListAdapter(
            AdminImmediateComplainView.this,
                listArrayID,
                listArrayUserName,
                listArrayEmail,
                listArrayLatitude,
                listArrayLongitude,
                listArrayTime,
                listArrayStationName,
                listArrayStationId,
                listArrayAddress,
                listArrayGender,
                listArrayUserPhone
        );
        lv.setAdapter(adapter);
    }
}
