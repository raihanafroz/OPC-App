package com.example.hp.pollice.AdminFragment.policeStation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.Adapter.AdminStationListAdapter;
import com.example.hp.pollice.PublicClass;
import com.example.hp.pollice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ViewPoliceStationFragment extends Fragment {

    ListView lv;
    //    TableLayout tl;
//    TableRow tr;
//    TextView stationId, stationName, stationPhone, stationLatitude, stationLongitude;
    View root;
    SwipeRefreshLayout swipeContainer;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.xml.fragment_view_station_list, container, false);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                if(new PublicClass().checkInternetConnection(root.getContext())) {
                    new getStation().execute("View Station");
                }
            }
        });
        lv = (ListView) root.findViewById(R.id.admin_station_listview);
        if(new PublicClass().checkInternetConnection(root.getContext())) {
            new getStation().execute("View Station");
        }


//        init();
        return root;

    }

    public class getStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(root.getContext());
            pd.setTitle("Fetching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];
            if (method.equals("View Station")) { //        login
//                String user_email = voids[1];
//                String stationName = voids[2];
//                String stationPhone = voids[3];
//                String stationLatitude = voids[4];
//                String stationLongitude = voids[5];
                try {
                    URL url = new URL(new PublicClass().url_stationDetails);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("GET");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
//                    OutputStream os = huc.getOutputStream();
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
//                            URLEncoder.encode("thanaName", "UTF-8") + "=" + URLEncoder.encode(stationName, "UTF-8") + "&" +
//                            URLEncoder.encode("thanaNo", "UTF-8") + "=" + URLEncoder.encode(stationPhone, "UTF-8") + "&" +
//                            URLEncoder.encode("thanaLatitude", "UTF-8") + "=" + URLEncoder.encode(stationLatitude, "UTF-8") + "&" +
//                            URLEncoder.encode("thanaLongitude", "UTF-8") + "=" + URLEncoder.encode(stationLongitude, "UTF-8");
//                    bw.write(data);
//                    bw.flush();
//                    bw.close();
//                    os.close();

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
            Log.i("json result", result);

            if (result == null || result.isEmpty()) {
                Snackbar.make(root, result, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            } else {
                init(result);
            }
        }

    }

    public void init(String data) {
        ArrayList<String> listID = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<String> listPhone = new ArrayList<>();
        ArrayList<String> listLatitude = new ArrayList<>();
        ArrayList<String> listLongitude = new ArrayList<>();
        ArrayList<String> listThanaId = new ArrayList<>();
        listID.add("#ID");
        listName.add("Name");
        listPhone.add("Phone");
        listLatitude.add("Latitude");
        listLongitude.add("Longitude");
        listThanaId.add("Thana Id");
        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                listID.add(String.valueOf(i+1));
                listName.add(object.getString("thanaName"));
                listPhone.add(object.getString("phoneNo"));
                listLatitude.add(object.getString("latitude"));
                listLongitude.add(object.getString("longitude"));
                listThanaId.add(object.getString("thanaId"));
            }

        } catch (
                JSONException e) {
//            e.printStackTrace();
        }

        String listArray[] = new String[listID.size()];
        String listArray1[] = new String[listName.size()];
        String listArray2[] = new String[listPhone.size()];
        String listArray3[] = new String[listLatitude.size()];
        String listArray4[] = new String[listLongitude.size()];
        String listArray5[] = new String[listThanaId.size()];

        listArray = listID.toArray(listArray);
        listArray1 = listName.toArray(listArray1);
        listArray2 = listPhone.toArray(listArray2);
        listArray3 = listLatitude.toArray(listArray3);
        listArray4 = listLongitude.toArray(listArray4);
        listArray5 = listThanaId.toArray(listArray5);

        AdminStationListAdapter adapter=new AdminStationListAdapter(getActivity(), listArray, listArray1, listArray2, listArray3, listArray4, listArray5);
        lv.setAdapter(adapter);
        swipeContainer.setRefreshing(false);

    }

}