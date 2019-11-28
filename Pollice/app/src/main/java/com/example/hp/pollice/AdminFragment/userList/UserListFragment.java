package com.example.hp.pollice.AdminFragment.userList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.pollice.Adapter.AdminUserListViewListAdapter;
import com.example.hp.pollice.AdminComplainForOthersView;
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


public class UserListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    ListView lv;
    View root;
    SwipeRefreshLayout swipeContainer;
    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.xml.fragment_user_list, container, false);

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
                    new getDataOfUser().execute("User List");
                }
            }
        });

        lv = (ListView) root.findViewById(R.id.admin_user_view_listview);


        if(new PublicClass().checkInternetConnection(root.getContext())) {
            new getDataOfUser().execute("User List");
        }

        return root;
    }


    private class getDataOfUser extends AsyncTask<String, Void, String> {
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
            if ((!result.isEmpty())) {
                init(result);
            } else {
                Snackbar.make(root, result, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    public void init(String data) {
        ArrayList<String> listID = new ArrayList<>();
        ArrayList<String> listUserId = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<String> listPhone = new ArrayList<>();
        ArrayList<String> listEmail = new ArrayList<>();
        ArrayList<String> listAddress = new ArrayList<>();
        ArrayList<String> listGender = new ArrayList<>();
        ArrayList<String> listTime = new ArrayList<>();

        listID.add("#ID");
        listUserId.add("User ID");
        listName.add("Name");
        listPhone.add("Phone");
        listEmail.add("Email");
        listAddress.add("Address");
        listGender.add("Gender");
        listTime.add("Time");
        try {
            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                listID.add(String.valueOf(i+1));
                listUserId.add(object.getString("id"));
                listName.add(object.getString("first_name")+" "+object.getString("last_name"));
                listPhone.add(object.getString("contact_number"));
                listEmail.add(object.getString("e-mail"));
                listAddress.add(object.getString("address"));
                listGender.add(object.getString("gender"));
                listTime.add(object.getString("createTime"));
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        String listArrayID[] = new String[listID.size()];
        String listArrayUserId[] = new String[listUserId.size()];
        String listArrayName[] = new String[listName.size()];
        String listArrayPhone[] = new String[listPhone.size()];
        String listArrayEmail[] = new String[listEmail.size()];
        String listArrayAddress[] = new String[listAddress.size()];
        String listArrayGender[] = new String[listGender.size()];
        String listArrayTime[] = new String[listTime.size()];

        listArrayID = listID.toArray(listArrayID);
        listArrayUserId = listUserId.toArray(listArrayUserId);
        listArrayName = listName.toArray(listArrayName);
        listArrayPhone = listPhone.toArray(listArrayPhone);
        listArrayEmail = listEmail.toArray(listArrayEmail);
        listArrayAddress = listAddress.toArray(listArrayAddress);
        listArrayGender = listGender.toArray(listArrayGender);
        listArrayTime = listTime.toArray(listArrayTime);

        AdminUserListViewListAdapter adapter=new AdminUserListViewListAdapter(getActivity(), listArrayID, listArrayUserId, listArrayName, listArrayPhone, listArrayEmail, listArrayAddress, listArrayGender, listArrayTime);
        lv.setAdapter(adapter);
        swipeContainer.setRefreshing(false);
    }


}
