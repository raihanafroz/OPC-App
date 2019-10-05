package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class loginActivity extends AppCompatActivity {
    //EditText user_email;
    TextInputEditText user_pass,user_email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        user_email=(TextInputEditText) findViewById(R.id.login_email);
        user_pass=(TextInputEditText) findViewById(R.id.login_password);


        /*
        *   checking email valid or not
        **/
        user_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    user_email.setError(null);
                }else {
                    if (charSequence.toString().trim().matches(emailPattern)) {
                        user_email.setError(null);
                    } else {
                        user_email.setError("Invalid email address");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking password minimu 6 digit or not
         **/
        user_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    user_pass.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        user_pass.setError(null);
                    } else {
                        user_pass.setError("Minimum 6 letters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
    /*
    *
    * */
    public void cancle(View view) {
        /*Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);*/
        Toast.makeText(getApplicationContext(), new EncryptedText().encrypt(user_pass.getText().toString()), Toast.LENGTH_SHORT).show();
    }

    public void reset(View view) {
        user_email.setText("");
        user_pass.setText("");
        user_email.requestFocus();
    }


    void alart(){
/*            new Alert_Builder().create_alart_1_btn("Error","Add a valid e-mail.", loginActivity.this);
            if(user_pass.getText().toString().isEmpty()){
                new Alert_Builder().create_alart_1_btn("Error","Enter yout password.", loginActivity.this);
            }*/
        if(user_email.getText().toString().isEmpty()){
            user_email.requestFocus();
        }else{
            if(user_pass.getText().toString().isEmpty()) {
                user_pass.requestFocus();
            }else{
                if (user_email.getText().toString().trim().matches(emailPattern)) {
                    user_email.setError(null);
                    if (user_pass.getText().toString().length() >= 6) {
                        user_pass.setError(null);
                        new login_Android_to_Mysql().execute("login", user_email.getText().toString(), new EncryptedText().encrypt(user_pass.getText().toString()));
                    } else {
                        user_pass.setError("Minimum 6 letters");
                        user_pass.requestFocus();
                    }
                } else {
                    user_email.setError("Invalid email address");
                    user_email.requestFocus();
                }
            }
        }
    }


    public void SignIn(View view) {
        alart();
    }

    public void gotoforgetPage(View view) {
        startActivity(new Intent(getApplicationContext(), Forget_Password.class));
    }


    public class login_Android_to_Mysql extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(loginActivity.this);
            pd.setTitle("Fatching Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_login = "http://192.168.0.100/New_folder/Pollice/server/login.php";
            String method = voids[0];
            if (method.equals("login")) { //        login
                String user_email = voids[1];
                String user_password = voids[2];
                try {
                    URL url = new URL(new publicClass().url_login);
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
            if (result.equals("Failed") || result.equals(null)) {//login
                Toast.makeText(getApplicationContext(), "Sorry to login"+result, Toast.LENGTH_SHORT).show();
            } else {
                parse(result);
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
        private void parse(String data){
            try{
                JSONArray ja=new JSONArray(data);
                JSONObject jo=null;
                if(ja.length()==1){
                    jo=ja.getJSONObject(0);
                    Intent i=new Intent(getApplicationContext(), homeActivity.class);
                    i.putExtra("Email",jo.getString("e-mail"));
                    i.putExtra("Password",jo.getString("password"));
                    //add user on SQLite Database to remember user
                    //String name=jo.getString("first_name")+" "+jo.getString("last_name");
                    new SQLiteDatabaseHelper(getApplicationContext()).create(jo.getString("e-mail"), jo.getString("password"));
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Many User found.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
}
