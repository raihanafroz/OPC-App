package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.pollice.Adapter.AdminComplainViewListAdapter;
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

public class AdminComplainView extends AppCompatActivity {



    public List<String> item = new ArrayList<String>();
    ListView lv;
    private MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complain_view);

        lv = (ListView) findViewById(R.id.admin_complain_view_listview);
        spinner = (MaterialSpinner) findViewById(R.id.admin_complain_view_spinner);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(spinner.getText().toString().equals("Select a user")){
                    new getImmediateComplainData().execute("View Complain", "");
                }else{
                    new getImmediateComplainData().execute("View Complain", spinner.getText().toString());
                }
//                Toast.makeText(getApplicationContext(), spinner.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });


        if(new PublicClass().checkInternetConnection(AdminComplainView.this)) {
            new getDataOfUser().execute("User List");
            new getImmediateComplainData().execute("View Complain", "");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_complain_view_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complain");
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
            pd = new ProgressDialog(AdminComplainView.this);
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminComplainView.this,
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
            pd = new ProgressDialog(AdminComplainView.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("View Complain")) {
                String email = voids[1];
                try {
                    URL url = new URL(new PublicClass().url_adminComplainList);
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
//            Log.d("json admin", result);
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (result == null || result.isEmpty() || result.equals("Access denied") || result.equals(null)) {
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
        ArrayList<String> listCause= new ArrayList<>();
        ArrayList<String> listComplainAddress = new ArrayList<>();
        ArrayList<String> listDescription= new ArrayList<>();
        ArrayList<String> listTime= new ArrayList<>();
        ArrayList<String> listStationName = new ArrayList<>();
        ArrayList<String> listStationId = new ArrayList<>();
        ArrayList<String> listAddress = new ArrayList<>();
        ArrayList<String> listGender = new ArrayList<>();
        ArrayList<String> listUserPhone = new ArrayList<>();
        ArrayList<String> listComplainStatus = new ArrayList<>();

        listID.add("#ID");
        listUserName.add("User Name");
        listEmail.add("Email");
        listLatitude.add("Latitude");
        listLongitude.add("Longitude");
        listCause.add("Cause");
        listComplainAddress.add("Complain Address");
        listDescription.add("Description");
        listTime.add("Time");
        listStationName.add("Station Name");
        listStationId.add("Station Id");
        listAddress.add("Address");
        listGender.add("Gender");
        listUserPhone.add("User Phone No");
        listComplainStatus.add("Status");
        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                listID.add(String.valueOf(i+1));
//                Log.d("json data", object.getString("email"));
                listUserName.add(object.getString("first_name") + " " + object.getString("last_name"));
                listEmail.add(object.getString("email"));
                listLatitude.add(object.getString("latitude"));
                listLongitude.add(object.getString("longitude"));
                listCause.add(object.getString("cause"));
                listComplainAddress.add(object.getString("currentAddress"));
                listDescription.add(object.getString("description"));
                listTime.add(object.getString("complainTime"));
                listStationName.add(object.getString("thanaName"));
                listStationId.add(object.getString("thanaId"));
                listAddress.add(object.getString("address"));
                listGender.add(object.getString("gender"));
                listUserPhone.add(object.getString("contact_number"));
                listComplainStatus.add(object.getString("status"));
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
        String listArrayCause[] = new String[listCause.size()];
        String listArrayComplainAddress[] = new String[listComplainAddress.size()];
        String listArrayDescription[] = new String[listDescription.size()];
        String listArrayTime[] = new String[listTime.size()];
        String listArrayStationName[] = new String[listStationName.size()];
        String listArrayStationId[] = new String[listStationId.size()];
        String listArrayAddress[] = new String[listAddress.size()];
        String listArrayGender[] = new String[listGender.size()];
        String listArrayUserPhone[] = new String[listUserPhone.size()];
        String listArrayComplainStatus[] = new String[listComplainStatus.size()];

        listArrayID = listID.toArray(listArrayID);
        listArrayUserName = listUserName.toArray(listArrayUserName);
        listArrayEmail = listEmail.toArray(listArrayEmail);
        listArrayLatitude = listLatitude.toArray(listArrayLatitude);
        listArrayLongitude = listLongitude.toArray(listArrayLongitude);
        listArrayCause = listCause.toArray(listArrayCause);
        listArrayComplainAddress = listComplainAddress.toArray(listArrayComplainAddress);
        listArrayDescription = listDescription.toArray(listArrayDescription);
        listArrayTime = listTime.toArray(listArrayTime);
        listArrayStationName = listStationName.toArray(listArrayStationName);
        listArrayStationId = listStationId.toArray(listArrayStationId);
        listArrayAddress = listAddress.toArray(listArrayAddress);
        listArrayGender = listGender.toArray(listArrayGender);
        listArrayUserPhone = listUserPhone.toArray(listArrayUserPhone);
        listArrayComplainStatus = listComplainStatus.toArray(listArrayComplainStatus);

        AdminComplainViewListAdapter adapter=new AdminComplainViewListAdapter(
                AdminComplainView.this,
                listArrayID,
                listArrayUserName,
                listArrayEmail,
                listArrayLatitude,
                listArrayLongitude,
                listArrayCause,
                listArrayComplainAddress,
                listArrayDescription,
                listArrayTime,
                listArrayStationName,
                listArrayStationId,
                listArrayAddress,
                listArrayGender,
                listArrayUserPhone,
                listArrayComplainStatus
        );
        lv.setAdapter(adapter);
    }
}
