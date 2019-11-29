package com.example.hp.pollice.AdminFragment.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.Adapter.AdminGridAdapter;
import com.example.hp.pollice.R;
import com.example.hp.pollice.PublicClass;

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

public class HomeFragment extends Fragment {

    GridView gridView;
    static final String[] MOBILE_OS = new String[] {
            "Users", "Police Station", "Immediate Complain", "Complain For Themself", "Complain For Other"};
    String[] MOBILE = new String[] {
            "10022", "100","2001", "5674", "98765" };

    View root;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.xml.fragment_home, container, false);

        gridView = (GridView) root.findViewById(R.id.gridview);
//        gridView.setAdapter(new AdminGridAdapter(getActivity(), MOBILE_OS, MOBILE));
        new gettingData().execute("adminPage");

        return root;
    }


    public class gettingData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
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
//                    e.printStackTrace();
                    //return e.getMessage();
                } catch (IOException e) {
//                    e.printStackTrace();
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
//                    e.printStackTrace();
                }
            }

            String data1[] = new String[data.size()];
            data1= data.toArray(data1);
            gridView.setAdapter(new AdminGridAdapter(getActivity(), MOBILE_OS, data1));

        }
    }


}