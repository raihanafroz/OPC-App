package com.example.hp.pollice;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class publicClass {
    String email = "";


//    String ip_address="192.168.43.159";    //phone
//    String ip_address="192.168.43.32";    //phone
//    String ip_address="192.168.0.111";  //Room WIFI
//    String ip_address="192.168.43.107";    //phone
//    String ip_address="6524cecf.ngrok.io";    //port for
    String ip_address="www.raihanafroz.info";  //Into a domain
    public String url_reg = "http://"+ip_address+"/New_folder/Pollice/server/insert_data.php";
    public String url_login = "http://"+ip_address+"/New_folder/Pollice/server/login.php";
    public String url_checkForForgetPassword = "http://"+ip_address+"/New_folder/Pollice/server/checkForForgetPassword.php";
    public String url_profile = "http://"+ip_address+"/New_folder/Pollice/server/profile.php";
    public String url_changePassword = "http://"+ip_address+"/New_folder/Pollice/server/changePassword.php";
    public String url_changePhoto = "http://"+ip_address+"/New_folder/Pollice/server/changePhoto.php";
    public String url_changeDetails = "http://"+ip_address+"/New_folder/Pollice/server/changeDetails.php";
    public String url_imgPath= "http://"+ip_address+"/New_folder/Pollice/server/Profile_Pic/";
    public String url_getPhoneNO = "http://"+ip_address+"/New_folder/Pollice/server/getPhoneNO.php";
    public String url_stationDetails = "http://"+ip_address+"/New_folder/Pollice/server/stationDetails.php";
    public String url_thanaList = "http://"+ip_address+"/New_folder/Pollice/server/thanaList.php";
    public String url_complain1 = "http://"+ip_address+"/New_folder/Pollice/server/complain1.php";
    public String url_complain2 = "http://"+ip_address+"/New_folder/Pollice/server/complain2.php";
    public String url_complain3 = "http://"+ip_address+"/New_folder/Pollice/server/complain3.php";
    public String url_complainList = "http://"+ip_address+"/New_folder/Pollice/server/complainList.php";
    public String url_adminPage = "http://"+ip_address+"/New_folder/Pollice/server/dataCountAdmin.php";
    public String url_add_station = "http://"+ip_address+"/New_folder/Pollice/server/insert_police_station.php";




    GPSTracker gps;
    protected double []getLocation(Context mContext, Activity mActivity){
        double []loc={0.0000000,0.0000000};
        gps = new GPSTracker(mContext, mActivity);
            // Check if GPS enabled
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                loc[0]=latitude;
                loc[1]=longitude;
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        return loc;
    }

    String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa"); //12-12-2019 08:12:12 PM
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //12-12-2019 20:12:12
        return mdformat.format(calendar.getTime());
    }

    public boolean checkInternetConnection(Context context){
        boolean status= false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            status = true;
        } else {
            new Alert_Builder().noInternet(context).show();
            status = false;
        }
        return status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
