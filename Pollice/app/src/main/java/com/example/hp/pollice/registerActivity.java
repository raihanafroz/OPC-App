package com.example.hp.pollice;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class registerActivity extends AppCompatActivity {
    private TextInputEditText firstName,lastName,email,address,contactNumber,password,confirmPassword;
    private RadioButton male,female;
    private Button chooseBTN;
    private ImageView chooseImage;
    private int ImgReq=1;
    private Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName=(TextInputEditText) findViewById(R.id.ResFirstName);
        lastName=(TextInputEditText) findViewById(R.id.ResLasstName);
        email=(TextInputEditText) findViewById(R.id.ResEmail);
        address=(TextInputEditText) findViewById(R.id.ResAddress);
        contactNumber=(TextInputEditText) findViewById(R.id.ResContactNumber);
        male=(RadioButton)findViewById(R.id.ResMale);
        female=(RadioButton)findViewById(R.id.ResFemale);
        chooseImage=(ImageView)findViewById(R.id.chooseImage);
        chooseBTN=(Button)findViewById(R.id.chooseBTN);
        password=(TextInputEditText) findViewById(R.id.ResPassword);
        confirmPassword=(TextInputEditText) findViewById(R.id.ResConfirmPassword);
    }

    public void choosePhoto(View view) {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,ImgReq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ImgReq && resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                chooseImage.setImageBitmap(bitmap);
                chooseImage.setVisibility(View.VISIBLE);
                chooseBTN.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String bitmap_to_string(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[]imbyte=baos.toByteArray();
        return Base64.encodeToString(imbyte, Base64.DEFAULT);
    }

    public void cancle(View view) {
        finish();
    }

    public void reset(View view) {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        address.setText("");
        contactNumber.setText("");
        male.setChecked(false);
        female.setChecked(false);
        password.setText("");
        confirmPassword.setText("");
    }

    public void save(View view) {
        if (check()){
            String gender="";
            if (male.isChecked()) {
                gender = "Male";
            } else if (female.isChecked()) {
                gender = "Female";
            }
            if(bitmap==null) {
                if (gender == "Male") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_male);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_female);
                }
            }

            new register_Android_to_Mysql().execute("register", firstName.getText().toString(), lastName.getText().toString(),
                    email.getText().toString(), address.getText().toString(), contactNumber.getText().toString(),
                    gender, new EncryptedText().encrypt(password.getText().toString()), bitmap_to_string(bitmap), new publicClass().getCurrentDate());
        }
    }
    private boolean check(){
        if(firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || address.getText().toString().isEmpty() || contactNumber.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "One or more field are empty", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if(!(male.isChecked())&&!(female.isChecked())){
                Toast.makeText(getApplicationContext(), "Select your gender", Toast.LENGTH_LONG).show();
                return false;
            }else{
                if(password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "One or more field are empty", Toast.LENGTH_LONG).show();
                    return false;
                }else{
                    if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();
                        password.setText("");
                        confirmPassword.setText("");
                        return false;
                    }else {
                        return true;
                    }
                }
            }
        }
    }

    public class register_Android_to_Mysql extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(registerActivity.this);
            pd.setTitle("Sending Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_reg = "http://192.168.0.100/New_folder/Pollice/server/insert_data.php";
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
                String currentTime=voids[9];
                try {
                    URL url = new URL(new publicClass().url_reg);
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
                            URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8") + "&" +
                            URLEncoder.encode("currentTime", "UTF-8") + "=" + URLEncoder.encode(currentTime, "UTF-8");
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
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else if(result.equals("Already have an account.")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                //new registerActivity().reset(getCurrentFocus());
            }
        }
    }
}
