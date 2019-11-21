package com.example.hp.pollice.ui.policeStation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.hp.pollice.AdminStationListAdapter;
import com.example.hp.pollice.ComplainListAdapter;
import com.example.hp.pollice.R;
import com.example.hp.pollice.publicClass;
import com.example.hp.pollice.ui.slideshow.SlideshowViewModel;

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

    private SlideshowViewModel slideshowViewModel;
    ListView lv;
    //    TableLayout tl;
//    TableRow tr;
//    TextView stationId, stationName, stationPhone, stationLatitude, stationLongitude;
    View root;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.xml.fragment_view_station_list, container, false);
        lv = (ListView) root.findViewById(R.id.admin_station_listview);

        new getStation().execute("View Station");
//        init();
        return root;

    }

//
//    public void reoveTable(){
//
//        while (tl.getChildCount() > -1) {
//            TableRow row =  (TableRow)tl.getChildAt(1);
//            tl.removeView(row);
////            j=tl.getChildCount();
//        }
//    }


//    public void init(String data) {
//        tl.removeAllViews();
//        TableRow.LayoutParams tableHeaderSize = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 40, 11f);
////        TableRow.LayoutParams tableRowId = new TableRow.LayoutParams(10, TableRow.LayoutParams.MATCH_PARENT, 1f);
////        TableRow.LayoutParams tableHeaderSize = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.L);
//
//
//        //table header
//        TableRow tableHeader = new TableRow(getContext());
////            tableHeader.setWeightSum(11f);
//        tableHeader.setLayoutParams(tableHeaderSize);
//        tableHeader.setBackground(getResources().getDrawable(R.drawable.table_header_border));
//        tableHeader.setLayoutParams(tableHeaderSize);
//        tableHeader.setPadding(5, 5, 5, 5);
//
//        TextView columnId = new TextView(getContext());
////            columnId.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
//        columnId.setTextScaleX(0.9f);
//        columnId.setTypeface(null, Typeface.BOLD);
//        columnId.setText("#No");
//        tableHeader.addView(columnId);
//
//        TextView columnStationName = new TextView(getContext());
////            columnStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4f));
//        columnStationName.setTextScaleX(0.9f);
//        columnStationName.setTypeface(null, Typeface.BOLD);
//        columnStationName.setText("Police Station Name");
//        tableHeader.addView(columnStationName);
//
//        TextView columnStationPhone = new TextView(getContext());
////            columnStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//        columnStationPhone.setTextScaleX(0.9f);
//        columnStationPhone.setTypeface(null, Typeface.BOLD);
//        columnStationPhone.setText("Phone");
//        tableHeader.addView(columnStationPhone);
//
//        TextView columnLatitude = new TextView(getContext());
////            columnStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//        columnLatitude.setTextScaleX(0.9f);
//        columnLatitude.setTypeface(null, Typeface.BOLD);
//        columnLatitude.setText("Latitude");
//        tableHeader.addView(columnLatitude);
//
//        TextView columnLongitude = new TextView(getContext());
////            columnStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//        columnLongitude.setTextScaleX(0.9f);
//        columnLongitude.setTypeface(null, Typeface.BOLD);
//        columnLongitude.setText("Longitude");
//        tableHeader.addView(columnLongitude);
////
//        tl.addView(tableHeader, 0);
//        try {
//            JSONArray array = new JSONArray(data);
//
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = array.getJSONObject(i);
////                String thanaid = object.getString("thanaId");
//                String thananame = object.getString("thanaName");
//                String thanaphone = object.getString("phoneNo");
//                String thanalat = object.getString("latitude");
//                String thanalong = object.getString("longitude");
//
////            //table header
//            TableRow tableRow = new TableRow(getContext());
//            tableRow.setLayoutParams( new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 11f));
//            tableRow.setBackground(getResources().getDrawable(R.drawable.table_row_border));
//            tableRow.setLayoutParams(tableHeaderSize);
//            tableRow.setPadding(3, 3, 3, 3);
//
////            tableRow.set
//
//
//            TextView rowId = new TextView(getContext());
//    //            rowId.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
//            rowId.setTextScaleX(0.9f);
//            rowId.setText(String.valueOf(i+1));
//            tableRow.addView(rowId);
//
//            TextView rowStationName = new TextView(getContext());
//    //            rowStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4f));
//            rowStationName.setTextScaleX(0.9f);
//            rowStationName.setText(thananame);
//            tableRow.addView(rowStationName);
//
//            TextView rowStationPhone = new TextView(getContext());
//    //            rowStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//            rowStationPhone.setTextScaleX(0.9f);
//            rowStationPhone.setText(thanaphone);
//            tableRow.addView(rowStationPhone);
//
//            TextView rowLatitude = new TextView(getContext());
//    //            rowStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//            rowLatitude.setTextScaleX(0.9f);
//            rowLatitude.setText(thanalat);
//            tableRow.addView(rowLatitude);
//
//            TextView rowLongitude = new TextView(getContext());
//    //            rowStationName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
//            rowLongitude.setTextScaleX(0.9f);
//            rowLongitude.setText(thanalong);
//            tableRow.addView(rowLongitude);
//
//            tl.addView(tableRow, i+1);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


    public class getStation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(root.getContext());
            pd.setTitle("Fatching Data");
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
                    URL url = new URL(new publicClass().url_stationDetails);
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
            e.printStackTrace();
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
    }

}