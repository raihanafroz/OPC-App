package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class Change_Password extends AppCompatActivity {
    TextInputEditText oldpass,newpass,confirmpass;
    TextInputLayout layer;
    String email="",state="",contactNumber="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);
        oldpass=(TextInputEditText)findViewById(R.id.changeOldPassword);
        newpass=(TextInputEditText)findViewById(R.id.changeNewPassword);
        confirmpass=(TextInputEditText)findViewById(R.id.changeConfirmPassword);
        layer=(TextInputLayout)findViewById(R.id.checkFromWhere);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            state=extra.getString("From");
        }
        if (state.equals("ForgetPass")){
            layer.setVisibility(View.INVISIBLE);
            contactNumber=extra.getString("ContactNumber");
        }else{
            layer.setVisibility(View.VISIBLE);
        }

    }

    public void cancle(View view) {
        finish();
    }

    public void SaveBtn(View view) {
        MySQLDatabaseHelper mdh =new MySQLDatabaseHelper(this);
        if (newpass.getText().toString().equals(confirmpass.getText().toString())){
            if(oldpass.getText().toString().isEmpty()) {
                new change_Android_to_Mysql().execute("ChangePassword", email, oldpass.getText().toString(), new EncryptedText().encrypt(newpass.getText().toString()), contactNumber);
            }else{
                new change_Android_to_Mysql().execute("ChangePassword", email, new EncryptedText().encrypt(oldpass.getText().toString()), new EncryptedText().encrypt(newpass.getText().toString()), contactNumber);
            }
        }else{
            Toast.makeText(getApplicationContext(), "New password not match", Toast.LENGTH_SHORT).show();
        }
    }
    public class change_Android_to_Mysql extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Change_Password.this);
            pd.setTitle("Tring to change");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_changePassword = "http://192.168.0.100/New_folder/Pollice/server/changePassword.php";
            String method = voids[0];
            if (method.equals("ChangePassword")) { //     Change password
                String email = voids[1];
                String oldPass = voids[2];
                String newPass = voids[3];
                String contactNumber = voids[4];
                try {
                    URL url = new URL(new publicClass().url_changePassword);
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
            if (result.equals("Successfully")) {
                Toast.makeText(getApplicationContext(), "Password Changed"+result, Toast.LENGTH_SHORT).show();
                new SQLiteDatabaseHelper(getApplicationContext()).drop();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), result+"\nPassword not change", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
