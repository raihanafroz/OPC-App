package com.example.hp.pollice;

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
}
