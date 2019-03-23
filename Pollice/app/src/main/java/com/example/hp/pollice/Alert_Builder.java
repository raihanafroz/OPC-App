package com.example.hp.pollice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

public class Alert_Builder {
    void create_alart_1_btn(String ttl, String msg, Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        TextView title = new TextView(context);
        title.setText(ttl);
        title.setBackgroundResource(R.drawable.alert_background);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.green));
        title.setTextSize(40);
        builder1.setCustomTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        /*builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
        TextView textView = (TextView) alert11.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }
    void create_alart_2_btn(String ttl, String msg, Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        TextView title = new TextView(context);
        title.setText(ttl);
        title.setBackgroundResource(R.drawable.alert_background);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.green));
        title.setTextSize(40);
        builder1.setCustomTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        TextView textView = (TextView) alert11.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }
    void settingAlert(Context context, final Activity activity, final boolean CloseStatus) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        TextView title = new TextView(context);
        title.setText("No Internet");
        title.setBackgroundResource(R.drawable.alert_background);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.green));
        title.setTextSize(40);
        builder1.setCustomTitle(title);
        builder1.setMessage("Do You Want To Enable Internet");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(CloseStatus) {
                    activity.finishAffinity();     //minimum sdk 16
                    System.exit(0);
                }else {
                    dialog.cancel();
                }
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        TextView textView = (TextView) alert11.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }
}
