package com.example.hp.pollice.ui.setings;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.hp.pollice.EncryptedText;
import com.example.hp.pollice.PublicClass;
import com.example.hp.pollice.R;
import com.example.hp.pollice.SQLiteDatabaseHelper;
import com.example.hp.pollice.Login;

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

public class ChangePasswordFragment extends Fragment {

    private TextInputEditText oldPassword, newPassword, confirmPassword;
    private Button save;
    View root;
    String email = "";
    private ViewGroup viewGroup;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.viewGroup = container;
        root = inflater.inflate(R.xml.fragment_change_password, container, false);
//        gettin users email
        if(new PublicClass().checkInternetConnection(getContext())){
            email= new PublicClass().checkUserData(getContext());
        }

        oldPassword = (TextInputEditText) root.findViewById(R.id.admin_change_password_old);
        newPassword = (TextInputEditText) root.findViewById(R.id.admin_change_password_new);
        confirmPassword = (TextInputEditText) root.findViewById(R.id.admin_change_password_confirm);
        save = (Button) root.findViewById(R.id.admin_change_password_btn);


        /*
        *
        * validation checking
        * */


        /*
         *   checking old password minimu 6 digit or not
         **/
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    oldPassword.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        oldPassword.setError(null);
                    } else {
                        if(oldPassword.getText().toString().equals("")){
                            oldPassword.setError(null);
                        }else {
                            oldPassword.setError("Minimum 6 characters");
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
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    newPassword.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        newPassword.setError(null);
                    } else {
                        if(newPassword.getText().toString().equals("")){
                            newPassword.setError(null);
                        }else {
                            newPassword.setError("Minimum 6 characters");
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i== 0 && i1 ==0 && i2 ==0){
                    confirmPassword.setError(null);
                }else {
                    if (charSequence.toString().length() >= 6) {
                        if (charSequence.toString().equals(newPassword.getText().toString())) {
                            confirmPassword.setError(null);
                        } else {
                            confirmPassword.setError("New password not match");
                        }
                    } else {
                        if(confirmPassword.getText().toString().equals("")){
                            confirmPassword.setError(null);
                        }else {
                            confirmPassword.setError("Minimum 6 characters");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldPassword.getText().toString().isEmpty()){
                    oldPassword.requestFocus();
                }else {
                    if (oldPassword.getText().toString().length() >= 6) {
                        if (newPassword.getText().toString().isEmpty()) {
                            newPassword.requestFocus();
                        } else {
                            if (newPassword.getText().toString().length() >= 6) {
                                if (confirmPassword.getText().toString().isEmpty()) {
                                    confirmPassword.requestFocus();
                                } else {
                                    if (confirmPassword.getText().toString().length() >= 6) {
                                        if (confirmPassword.getText().toString().equals(newPassword.getText().toString()))
                                            new change_Android_to_Mysql().execute("ChangePassword", email, new EncryptedText().encrypt(oldPassword.getText().toString()), new EncryptedText().encrypt(newPassword.getText().toString()), "");
                                        else {
                                            confirmPassword.setError("New password not match");
                                            confirmPassword.requestFocus();
                                        }
                                    } else {
                                        confirmPassword.setError("Minimum 6 characters");
                                        confirmPassword.requestFocus();
                                    }
                                }
                            } else {
                                newPassword.setError("Minimum 6 characters");
                                newPassword.requestFocus();
                            }
                        }
                    } else {
                        oldPassword.setError("Minimum 6 characters");
                        oldPassword.requestFocus();
                    }
                }
            }
        });
        return root;
    }


    public class change_Android_to_Mysql extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(viewGroup.getContext());
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
                    URL url = new URL(new PublicClass().url_changePassword);
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
                Toast.makeText(getContext(), "Password Changed"+result, Toast.LENGTH_SHORT).show();
                new SQLiteDatabaseHelper(getContext()).drop();
                startActivity(new Intent(getContext(), Login.class));
            } else {
                oldPassword.setError("Old Password Not Match");
                oldPassword.requestFocus();
                Toast.makeText(getContext(), result+"\nPassword not change", Toast.LENGTH_SHORT).show();
            }
        }
    }
}