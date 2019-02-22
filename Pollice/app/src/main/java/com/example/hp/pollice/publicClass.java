package com.example.hp.pollice;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class publicClass {
    //String ip_address="192.168.43.32";    //phone
    String ip_address="192.168.0.100";  //Room WIFI
    //String ip_address="192.168.43.107";    //phone
    public String url_reg = "http://"+ip_address+"/New_folder/Pollice/server/insert_data.php";
    public String url_login = "http://"+ip_address+"/New_folder/Pollice/server/login.php";
    public String url_checkForForgetPassword = "http://"+ip_address+"/New_folder/Pollice/server/checkForForgetPassword.php";
    public String url_profile = "http://"+ip_address+"/New_folder/Pollice/server/profile.php";
    public String url_changePassword = "http://"+ip_address+"/New_folder/Pollice/server/changePassword.php";
    public String url_changePhoto = "http://"+ip_address+"/New_folder/Pollice/server/changePhoto.php";
    public String url_changeDetails = "http://"+ip_address+"/New_folder/Pollice/server/changeDetails.php";
    public String url_imgPath= "http://"+ip_address+"/New_folder/Pollice/server/Profile_Pic/";
    public String url_complain1 = "http://"+ip_address+"/New_folder/Pollice/server/complain1.php";




    GPSTracker gps;
    protected double []getLocation(Context mContext, Activity mActivity){
        double []loc={0.0000000,0.0000000};
        gps = new GPSTracker(mContext, mActivity);
        //Check wifi
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
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
        }else{
            //Wifi is disable
            wifi.setWifiEnabled(true);
            getLocation(mContext,mActivity);
        }
        return loc;
    }

    String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa"); //12-12-2019 08:12:12 PM
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //12-12-2019 20:12:12
        return mdformat.format(calendar.getTime());
    }






}
