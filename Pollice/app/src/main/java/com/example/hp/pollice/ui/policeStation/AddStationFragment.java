package com.example.hp.pollice.ui.policeStation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.example.hp.pollice.R;
import com.example.hp.pollice.publicClass;

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

public class AddStationFragment extends Fragment {

    private AddStationViewModel addStationViewModel;
    private TextInputEditText thanaName, latitude, logitude, phoneNo;
    private Button save, clear;
    String email = "raihanafroz9@gmail.com";
    View root;
    ViewGroup viewGroup;
    String namePattern = "[a-zA-Z\\s]+";
    SharedPreferences prf;


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.viewGroup = container;
//        prf = PreferenceManager.getDefaultSharedPreferences(container.getContext());
//        email = prf.getString("email", null);
//        email = new publicClass().getEmail();

        root = inflater.inflate(R.xml.fragment_add_station, container, false);

        thanaName = (TextInputEditText) root.findViewById(R.id.add_station_name);
        phoneNo = (TextInputEditText) root.findViewById(R.id.add_station_phone);
        latitude = (TextInputEditText) root.findViewById(R.id.add_station_latitude);
        logitude = (TextInputEditText) root.findViewById(R.id.add_station_longitude);
        save = (Button) root.findViewById(R.id.add_station_btn_save);
        clear = (Button) root.findViewById(R.id.add_station_btn_clear);

        /*
         *   checking thanaName valid or not
         **/
        thanaName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    thanaName.setError(null);
                }else {

                    if (charSequence.toString().trim().matches(namePattern)) {
                        thanaName.setError(null);
                    } else {
                        if(thanaName.getText().toString().isEmpty()){
                            thanaName.setError(null);
                        }else {
                            thanaName.setError("Wrong input");
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking phoneNo valid or not
         **/
        phoneNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    phoneNo.setError(null);
                }else {
                    if (charSequence.toString().length() >= 11) {
                        phoneNo.setError(null);
                    } else {
                        if(phoneNo.getText().toString().isEmpty()){
                            phoneNo.setError(null);
                        }else {
                            phoneNo.setError("Minimum 11 digit");
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking latitude valid or not
         **/
        latitude.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    latitude.setError(null);
                }else {
                    if (!charSequence.toString().isEmpty()) {
                        latitude.setError(null);
                    } else {
                        latitude.setError("Value required");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking logitude valid or not
         **/
        logitude.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    logitude.setError(null);
                }else {
                    if (!charSequence.toString().isEmpty()) {
                        logitude.setError(null);
                    } else {
                        logitude.setError("Value required");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Log.d("json", prf.getString("email", null).toString());
                if(checkValidation()) {
                    new addStation().execute("Add Station", email, thanaName.getText().toString(), phoneNo.getText().toString(),
                          latitude.getText().toString(), logitude.getText().toString());
                }
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearField();
            }
        });
        return root;
    }

    public void clearField(){
//        Log.d("json", "clear field");
        thanaName.setText("");
        thanaName.setError(null);
        thanaName.requestFocus();
        phoneNo.setText("");
        phoneNo.setError(null);
        latitude.setText("");
        latitude.setError(null);
        logitude.setText("");
        logitude.setError(null);
    }

    boolean checkValidation(){
        boolean b = false;
        if(new publicClass().checkInternetConnection(viewGroup.getContext())) {
            if (thanaName.getText().toString().isEmpty()) {
                thanaName.requestFocus();
            } else {
                thanaName.setError(null);
                if (phoneNo.getText().toString().length() >= 6) {
                    phoneNo.setError(null);
                    if(latitude.getText().toString().isEmpty()){
                        latitude.requestFocus();
                    }else{
                        latitude.setError(null);
                        if(logitude.getText().toString().isEmpty()){
                            logitude.requestFocus();
                        }else{
                            b = true;
                        }
                    }
                } else {
                    phoneNo.setError("Minimum 6 letters");
                    phoneNo.requestFocus();
                }
            }
        }
        return b;
    }

    public class addStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(viewGroup.getContext());
            pd.setTitle("Sending Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("Add Station")) { //        login
                String user_email = voids[1];
                String stationName = voids[2];
                String stationPhone = voids[3];
                String stationLatitude = voids[4];
                String stationLongitude = voids[5];
                try {
                    URL url = new URL(new publicClass().url_add_station);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
                            URLEncoder.encode("thanaName", "UTF-8") + "=" + URLEncoder.encode(stationName, "UTF-8") + "&" +
                            URLEncoder.encode("thanaNo", "UTF-8") + "=" + URLEncoder.encode(stationPhone, "UTF-8") + "&" +
                            URLEncoder.encode("thanaLatitude", "UTF-8") + "=" + URLEncoder.encode(stationLatitude, "UTF-8") + "&" +
                            URLEncoder.encode("thanaLongitude", "UTF-8") + "=" + URLEncoder.encode(stationLongitude, "UTF-8");
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

            if (result.equals("Data added")) {
                clearField();
            }
            Snackbar.make(root, result, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

    }

}