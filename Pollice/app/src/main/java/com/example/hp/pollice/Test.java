package com.example.hp.pollice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test extends AppCompatActivity {
    String title, userId, userName, userEmail, userPhone, userAddress, userGender, userTime;
    TextView name, email, phone, address, gender, time;
    ImageView userPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            title = extra.getString("Title");
            userId = extra.getString("UserID");
            userName = extra.getString("UserName");
            userPhone = extra.getString("UserPhone");
            userGender = extra.getString("UserGender");
            userEmail = extra.getString("UserEmail");
            userAddress = extra.getString("UserAddress");
            userTime = extra.getString("Time");
        }

        // app bar configuer
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_user_details_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.admin_user_details_Name);
        email = (TextView) findViewById(R.id.admin_user_details__email);
        phone = (TextView) findViewById(R.id.admin_user_details_phone);
        address = (TextView) findViewById(R.id.admin_user_details_address);
        gender = (TextView) findViewById(R.id.admin_user_details_gender);
        time = (TextView) findViewById(R.id.admin_user_details_time);
        userPic = (ImageView) findViewById(R.id.admin_user_details_pic);

        name.setText(userName);
        email.setText(userEmail);
        phone.setText(userPhone);
        address.setText(userAddress);
        gender.setText(userGender);
        time.setText(userTime);

        if(new PublicClass().checkInternetConnection(Test.this)) {
            new downloadImageFromServer(userEmail).execute();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class downloadImageFromServer extends AsyncTask<Void, Void, Bitmap> {
        String imageName="";
        public downloadImageFromServer(String imageName){
            this.imageName=imageName;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String url=new PublicClass().url_imgPath+imageName+".jpg";
            try {
                URLConnection connection=new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 60);
                connection.setReadTimeout(1000 * 60);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap!=null){
                userPic.setImageBitmap(bitmap);
            }
        }
    }

}
