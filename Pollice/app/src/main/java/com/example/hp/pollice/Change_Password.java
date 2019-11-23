package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.change_password_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oldpass=(TextInputEditText)findViewById(R.id.change_password_old);
        newpass=(TextInputEditText)findViewById(R.id.change_password_new);
        confirmpass=(TextInputEditText)findViewById(R.id.change_password_confirm);
        layer=(TextInputLayout)findViewById(R.id.checkFromWhere);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            state=extra.getString("From");
        }
        if (state.equals("ForgetPass")){
            layer.setVisibility(View.INVISIBLE);
            oldpass.setVisibility(View.INVISIBLE);
            contactNumber=extra.getString("ContactNumber");
        }else{
            layer.setVisibility(View.VISIBLE);
            oldpass.setVisibility(View.VISIBLE);
        }



        /*
         *   checking old password minimu 6 digit or not
         **/
        oldpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    oldpass.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        oldpass.setError(null);
                    } else {
                        if(oldpass.getText().toString().equals("")){
                            oldpass.setError(null);
                        }else {
                            oldpass.setError("Minimum 6 characters");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking old password minimu 6 digit or not
         **/
        newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    newpass.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        newpass.setError(null);
                    } else {
                        if(newpass.getText().toString().equals("")){
                            newpass.setError(null);
                        }else {
                            newpass.setError("Minimum 6 characters");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        /*
         *   checking old password minimu 6 digit or not
         **/
        confirmpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    confirmpass.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        if (charSequence.toString().equals(newpass.getText().toString())) {
                            confirmpass.setError(null);
                        } else {
                            confirmpass.setError("New password not match");
                        }
                    } else {
                        if(confirmpass.getText().toString().equals("")){
                            confirmpass.setError(null);
                        }else {
                            confirmpass.setError("Minimum 6 characters");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.forget_password_app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.app_bar_save_btn){
//            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
            SaveBtn();
        }else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancle(View view) {
//        finish();
        if(oldpass.getVisibility() == View.VISIBLE) {
            Toast.makeText(getApplicationContext(), "visiable", Toast.LENGTH_SHORT).show();
        }
    }


    public void SaveBtn() {
//        MySQLDatabaseHelper mdh =new MySQLDatabaseHelper(this);
        if(oldpass.getVisibility() == View.INVISIBLE) {
            if(newpass.getText().toString().isEmpty()){
                newpass.requestFocus();
            }else{
                if(newpass.getText().toString().length() >=6){
                    if(confirmpass.getText().toString().isEmpty()){
                        confirmpass.requestFocus();
                    }else{
                        if(confirmpass.getText().toString().length() >=6){
                            if(confirmpass.getText().toString().equals(newpass.getText().toString())){
                                new change_Android_to_Mysql().execute("ChangePassword", email, oldpass.getText().toString(), new EncryptedText().encrypt(newpass.getText().toString()), contactNumber);
                            }else{
                                confirmpass.setError("New password not match");
                                confirmpass.requestFocus();
                            }
                        }else{
                            confirmpass.setError("Minimum 6 characters");
                            confirmpass.requestFocus();

                        }
                    }
                }else{
                    newpass.setError("Minimum 6 characters");
                    newpass.requestFocus();
                }
            }
        }else{
            if(oldpass.getText().toString().isEmpty()){
                oldpass.requestFocus();
            }else {
                if (oldpass.getText().toString().length() >= 6) {
                    if (newpass.getText().toString().isEmpty()) {
                        newpass.requestFocus();
                    } else {
                        if (newpass.getText().toString().length() >= 6) {
                            if (confirmpass.getText().toString().isEmpty()) {
                                confirmpass.requestFocus();
                            } else {
                                if (confirmpass.getText().toString().length() >= 6) {
                                    if (confirmpass.getText().toString().equals(newpass.getText().toString())) {
                                        new change_Android_to_Mysql().execute("ChangePassword", email, new EncryptedText().encrypt(oldpass.getText().toString()), new EncryptedText().encrypt(newpass.getText().toString()), contactNumber);
                                    } else {
                                        confirmpass.setError("New password not match");
                                        confirmpass.requestFocus();
                                    }
                                } else {
                                    confirmpass.setError("Minimum 6 characters");
                                    confirmpass.requestFocus();
                                }
                            }
                        } else {
                            newpass.setError("Minimum 6 characters");
                            newpass.requestFocus();
                        }
                    }
                } else {
                    oldpass.setError("Minimum 6 characters");
                    oldpass.requestFocus();
                }
            }
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
                oldpass.setError("Old Password Not Match");
                oldpass.requestFocus();
                Toast.makeText(getApplicationContext(), result+"\nPassword not change", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
