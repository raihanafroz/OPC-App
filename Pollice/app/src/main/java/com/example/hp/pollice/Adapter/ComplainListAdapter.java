package com.example.hp.pollice.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.pollice.R;

public class ComplainListAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] serialList;
    private final String[] typeList;
    private final String[] emailList;
    private final String[] causeList;
    private final String[] timeList;
    private final String[] complainNoList;
    private final String[] complainStatusList;
    Integer image;


    public ComplainListAdapter(Activity context, String[] serial, String[] type, String[] email, String[] cause, String[] time, String[] complainNo, String[] complainStatus) {
        super(context, R.layout.complain_list_row, type);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.serialList=serial;
        this.typeList=type;
        this.emailList=email;
        this.causeList=cause;
        this.timeList=time;
        this.complainNoList=complainNo;
        this.complainStatusList=complainStatus;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.complain_list_row, null,true);

        TextView serial = (TextView) rowView.findViewById(R.id.serial);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView type = (TextView) rowView.findViewById(R.id.complainType);
        TextView cause = (TextView) rowView.findViewById(R.id.cause);
        TextView time = (TextView) rowView.findViewById(R.id.complainTime);
        TextView status = (TextView) rowView.findViewById(R.id.complainStatus);


        serial.setText(serialList[position]);
        type.setText(typeList[position]);
        cause.setText(causeList[position]);
        time.setText(timeList[position]);
        String complainStatus = complainStatusList[position];
        if(complainStatus.equals("Sent")){
            status.setTextColor(ContextCompat.getColor(context, R.color.color_sent));
        }else if(complainStatus.equals("Working")){
            status.setTextColor(ContextCompat.getColor(context, R.color.color_working));
        }else if(complainStatus.equals("Reject")){
            status.setTextColor(ContextCompat.getColor(context, R.color.color_reject));
        }else if(complainStatus.equals("Done")){
            status.setTextColor(ContextCompat.getColor(context, R.color.color_done));
        }
        status.setText(complainStatus);

        return rowView;

    };
}
