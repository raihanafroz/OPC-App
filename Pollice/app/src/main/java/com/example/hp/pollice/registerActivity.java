package com.example.hp.pollice;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


import com.jaredrummler.materialspinner.MaterialSpinner;

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
    private MaterialSpinner spinner;
    private TextInputEditText firstName,lastName,email,address,contactNumber,password,confirmPassword;
    private RadioButton male,female;
    private Button chooseBTN;
    private ImageView chooseImage;
    private int ImgReq=1;
    private Bitmap bitmap=null;
    String namePattern = "[a-zA-Z]+";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String addressPattern = "^[#.0-9a-zA-Z\\s,-]+$";
    String gender = "Gender";
//    String[] SPINNER_DATA = {"Male","Female"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName=(TextInputEditText) findViewById(R.id.signup_first_name);
        lastName=(TextInputEditText) findViewById(R.id.signup_last_name);
        email=(TextInputEditText) findViewById(R.id.signup_email);
        address=(TextInputEditText) findViewById(R.id.signup_address);
        contactNumber=(TextInputEditText) findViewById(R.id.signup_phone);

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setHint("Gender");
        spinner.setItems("Gender", "Male", "Female");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item == "Gender"){
                    spinner.setError("Wrong input selected");
                }
                gender = spinner.getText().toString();
            }
        });


//        male=(RadioButton)findViewById(R.id.ResMale);
//        female=(RadioButton)findViewById(R.id.ResFemale);
        chooseImage=(ImageView)findViewById(R.id.chooseImage);
        chooseBTN=(Button)findViewById(R.id.chooseBTN);
        password=(TextInputEditText) findViewById(R.id.signup_password);
        confirmPassword=(TextInputEditText) findViewById(R.id.signup_confirm_password);

        /*
         *   checking firstname valid or not
         **/
        firstName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    firstName.setError(null);
                }else {
                    if (charSequence.toString().length() > 1) {
                        if (charSequence.toString().trim().matches(namePattern)) {
                            firstName.setError(null);
                        } else {
                            firstName.setError("Wrong input");
                        }
                    } else {
                        firstName.setError("Minimum 2 characters");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking lastname valid or not
         **/
        lastName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    lastName.setError(null);
                }else {
                    if (charSequence.toString().length() > 2) {
                        if (charSequence.toString().trim().matches(namePattern)) {
                            lastName.setError(null);
                        } else {
                            lastName.setError("Wrong input");
                        }
                    } else {
                        lastName.setError("Minimum 3 characters");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking email valid or not
         **/
        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    email.setError(null);
                }else {
                    if (charSequence.toString().trim().matches(emailPattern)) {
                        email.setError(null);
                    } else {
                        email.setError("Invalid email address");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking address valid or not
         **/
        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(getApplicationContext(), "i="+i+" i2="+i1+" i2="+i2, Toast.LENGTH_LONG).show();
                if(i== 0 && i1 ==0 && i2 ==0){
                    address.setError(null);
                }else {
                    if (charSequence.toString().trim().matches(addressPattern)) {
                        address.setError(null);
                    } else {
                        address.setError("Wrong input");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking Contact Number minimu 11 digit or not
         **/
        contactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    contactNumber.setError(null);
                }else {
                    if (charSequence.toString().length() >= 11) {
                        contactNumber.setError(null);
                    } else {
                        contactNumber.setError("Minimum 11 characters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking old password minimu 6 digit or not
         **/
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    password.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        password.setError(null);
                    } else {
                        password.setError("Minimum 6 characters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        /*
         *   checking old password minimu 6 digit or not
         **/
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    confirmPassword.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        if (charSequence.toString().equals(password.getText().toString())) {
                            confirmPassword.setError(null);
                        } else {
                            confirmPassword.setError("Confirm password not match");
                        }
                    } else {
                        confirmPassword.setError("Minimum 6 characters");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

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


    public void save(View view) {
        if (check()){
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

    //  checking field validation
    private boolean check(){
        int count = 0;
        if(firstName.getText().toString().length() < 2 && firstName.getText().toString().trim().matches(namePattern)){
            firstName.setError("Wrong input");
            firstName.requestFocus();
            return false;
        }else{ count++;}
        if(lastName.getText().toString().length() < 3 && lastName.getText().toString().trim().matches(namePattern)){
            lastName.setError("Wrong input");
            lastName.requestFocus();
            return false;
        }else{ count++;}
        if(email.getText().toString().trim().matches(emailPattern)) {
            count++;
        } else {
            email.setError("Invalid email address");
            email.requestFocus();
            return false;
        }
        if(address.getText().toString().trim().matches(addressPattern)) {
            count++;
        } else {
            address.setError("Wrong input");
            address.requestFocus();
            return false;
        }
        if(gender != "Gender"){
            count++;
        }else{
            spinner.setError("Wrong input selected");
            spinner.setFocusableInTouchMode(true);
            spinner.setFocusable(true);
            spinner.requestFocus();
            spinner.performClick();
        }
        if(contactNumber.getText().toString().length() < 11 ){
            contactNumber.setError("Required minimum 11 characters");
            contactNumber.requestFocus();
            return false;
        }else{ count++;}
        if(password.getText().toString().length() < 6 ){
            password.setError("Required minimum 6 characters");
            password.requestFocus();
            return false;
        }else{ count++;}

        if(confirmPassword.getText().toString().length() < 6 ){
            confirmPassword.setError("Required minimum 6 characters");
            confirmPassword.requestFocus();
            return false;
        }else{
            if(confirmPassword.getText().toString().equals(password.getText().toString())){
                count++;
            }else{
                confirmPassword.setError("Confirm password not match");
                confirmPassword.requestFocus();
                return false;
            }
        }
        if(count == 8){
            return true;
        }
        return false;
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

                    Log.i("json data sending", respose+" <<==>>");

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
                Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            } else if(result.equals("Already have an account.")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                //new registerActivity().reset(getCurrentFocus());
            }
        }
    }
}
