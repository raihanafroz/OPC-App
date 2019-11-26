package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class MySQLDatabaseHelper extends AsyncTask<String, Void, String> {
    private  String forgetPasswordEmail="",forgetPasswordContactNumber="";

    ProgressDialog pd;
    Context ctx;
    MySQLDatabaseHelper(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setTitle("Fatching Data");
        pd.setMessage("Please wait...");
        pd.show();
    }

    @Override
    protected String doInBackground(String... voids) {
        String url_reg = "http://192.168.0.100/New_folder/Pollice/server/insert_data.php";
        String url_login = "http://192.168.0.100/New_folder/Pollice/server/login.php";
        String url_checkForForgetPassword = "http://192.168.0.100/New_folder/Pollice/server/checkForForgetPassword.php";
        String url_changePassword = "http://192.168.0.100/New_folder/Pollice/server/changePassword.php";
        String method = voids[0];
        if (method == "register") {
            String fname = voids[1];
            String lname = voids[2];
            String email = voids[3];
            String address = voids[4];
            String contactNumber = voids[5];
            String gender = voids[6];
            String pass = voids[7];
            String image=voids[8];
            try {
                URL url = new URL(url_reg);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" +
                        URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                        URLEncoder.encode("contactNumber", "UTF-8") + "=" + URLEncoder.encode(contactNumber, "UTF-8") + "&" +
                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8") + "&" +
                        URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
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

                return respose;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                //return e.getMessage();
            }
        } else if (method.equals("login")) { //        login
            String user_email = voids[1];
            String user_password = voids[2];
            try {
                URL url = new URL(url_login);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
                        URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(user_password, "UTF-8");
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
                return respose;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                //return e.getMessage();
            }
        } else if (method.equals("checkForForgetPassword")) { //     check forget password user
            forgetPasswordEmail = voids[1];
            forgetPasswordContactNumber = voids[2];
            try {
                URL url = new URL(url_checkForForgetPassword);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(forgetPasswordEmail, "UTF-8") + "&" +
                        URLEncoder.encode("user_contact", "UTF-8") + "=" + URLEncoder.encode(forgetPasswordContactNumber, "UTF-8");
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
                return respose;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                //return e.getMessage();
            }
        }
        else if (method.equals("ChangePassword")) { //     Change password
            String email = voids[1];
            String oldPass = voids[2];
            String newPass = voids[3];
            String contactNumber = voids[4];
            try {
                URL url = new URL(url_changePassword);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("user_oldPass", "UTF-8") + "=" + URLEncoder.encode(oldPass, "UTF-8") + "&" +
                        URLEncoder.encode("user_newPass", "UTF-8") + "=" + URLEncoder.encode(newPass, "UTF-8") + "&" +
                        URLEncoder.encode("user_contactNumber", "UTF-8") + "=" + URLEncoder.encode(contactNumber, "UTF-8");
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
        if (result.equals("Data Added.")){
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            ctx.startActivity(new Intent(ctx, Main.class));
        }
        else if(result.equals("Duplicate e-mail")){
            Toast.makeText(ctx, "E-mail already used once", Toast.LENGTH_SHORT).show();
            ctx.startActivity(new Intent(ctx, Register.class));
        }
        else if (result.equals("Forget User Found")){
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(ctx, ChangePassword.class);
            i.putExtra("Email", forgetPasswordEmail);
            i.putExtra("ContactNumber", forgetPasswordContactNumber);
            i.putExtra("From", "ForgetPass");
            ctx.startActivity(i);
        }
        else if (result.equals("Forget User Not Found")){
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
        else if (result.equals("Successfully Changed")){
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            new SQLiteDatabaseHelper(ctx).drop();
            ctx.startActivity(new Intent(ctx, Login.class));
        }
        else if (result.equals("Password not changed")){
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
        else if(!result.equals("")) {//login
            parse(result);
            //Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(ctx, "Sorry", Toast.LENGTH_SHORT).show();
        }
    }
    private void parse(String data){
        try{
            JSONArray ja=new JSONArray(data);
            JSONObject jo=null;
            if(ja.length()==1){
                jo=ja.getJSONObject(0);
                Intent i=new Intent(ctx, Phofile.class);
                i.putExtra("Email",jo.getString("e-mail"));
                i.putExtra("Password",jo.getString("password"));
                //add user on SQLite Database to remember user
//                new SQLiteDatabaseHelper(ctx).create(jo.getString("e-mail"), jo.getString("password"));
                ctx.startActivity(i);
            }else{
                Toast.makeText(ctx, "Many User found.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
