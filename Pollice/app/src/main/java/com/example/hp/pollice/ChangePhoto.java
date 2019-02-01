package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.BitSet;

public class ChangePhoto extends AppCompatActivity {
    private int ImgReq=1;
    private LinearLayout secndLayout;
    private ImageView changePhotoImageview;
    private Bitmap bitmap;
    private String email="",state="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            email=extra.getString("Email");
            state=extra.getString("From");
        }
        secndLayout=(LinearLayout)findViewById(R.id.LayoutofChangePhoto);
        changePhotoImageview=(ImageView)findViewById(R.id.selectPrototToChange);
        secndLayout.setVisibility(View.INVISIBLE);
    }

    public void selectPhotoBtn(View view) {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,ImgReq);
    }

    public void cancleBtn(View view) {
        finish();
    }

    public void savePhotoBtn(View view) {
        new changePhoto().execute("changePhoto",bitmap_to_string(bitmap));
    }

    private String bitmap_to_string(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[]imbyte=baos.toByteArray();
        return Base64.encodeToString(imbyte, Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ImgReq && resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                changePhotoImageview.setImageBitmap(bitmap);
                changePhotoImageview.setVisibility(View.VISIBLE);
                secndLayout.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class changePhoto extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ChangePhoto.this);
            pd.setTitle("Sending Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            //String url_reg = "http://192.168.0.100/New_folder/Pollice/server/insert_data.php";
            String method = voids[0];
            if (method == "changePhoto") {
                String image=voids[1];
                try {
                    URL url = new URL(new publicClass().url_changePhoto);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
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

                    return respose.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.getMessage();
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
            if(result.equals(null)){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else if (result.equals("File deleted")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(), homeActivity.class);
                i.putExtra("Email",email);
                i.putExtra("Password","");
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
