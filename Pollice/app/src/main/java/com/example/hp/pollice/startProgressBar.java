package com.example.hp.pollice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class startProgressBar extends AppCompatActivity {


    private ProgressBar progressBar;
    private int pStatus=0;
    private Handler handler = new Handler();
    private TextView txtProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_progress_bar);

        txtProgress =(TextView)findViewById(R.id.txtProgress);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");

                        }
                    });
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(pStatus==100){
                        Intent i=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "Limite Over", Toast.LENGTH_SHORT).show();
                    }
                    pStatus++;
                }
            }
        }).start();

    }
}
