package com.example.hp.pollice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.net.URLConnection;
import java.net.URLEncoder;

public class ChangePhoto extends AppCompatActivity {
    private int ImgReq=1;
    private LinearLayout secndLayout;
    private Button uploaBtn;
    private ImageView changePhotoImageview, oldPhoto;
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

        if(new publicClass().checkInternetConnection(ChangePhoto.this)) {
            new downloadImageFromServer(email).execute();
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.change_photo_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Photo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        oldPhoto = (ImageView)findViewById(R.id.old_photo_user);
        changePhotoImageview = (ImageView)findViewById(R.id.new_photo_user);
        changePhotoImageview.setVisibility(View.INVISIBLE);
        uploaBtn = (Button) findViewById(R.id.upload_new_photo);
        uploaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhotoBtn();
            }
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
            if(new publicClass().checkInternetConnection(ChangePhoto.this)) {
                new changePhoto().execute("changePhoto",bitmap_to_string(bitmap));
            }
        }else if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectPhotoBtn() {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,ImgReq);
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
                uploaBtn.setText("Change");
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
                Intent i=new Intent(getApplicationContext(), PhofileActivity.class);
                i.putExtra("Email",email);
                i.putExtra("Password","");
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }



    private class downloadImageFromServer extends AsyncTask<Void, Void, Bitmap>{
        String imageName="";
        public downloadImageFromServer(String imageName){
            this.imageName=imageName;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url=new publicClass().url_imgPath+imageName+".jpg";
            try {
                URLConnection connection=new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 60);
                connection.setReadTimeout(1000 * 60);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                //Toast.makeText(SeeImage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap!=null){
                oldPhoto.setImageBitmap(bitmap);
            }
        }
    }
}
