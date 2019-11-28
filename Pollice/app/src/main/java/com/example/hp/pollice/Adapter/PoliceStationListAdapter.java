package com.example.hp.pollice.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.pollice.R;

public class PoliceStationListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maintitle;
    private final String[] sub_title;
    Integer image;

    public PoliceStationListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer img) {
        super(context, R.layout.list_view_row_item, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.sub_title=subtitle;
        this.image=img;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view_row_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView callBtn = (ImageView) rowView.findViewById(R.id.call);
        ImageView messageBtn = (ImageView) rowView.findViewById(R.id.message);
        final String number = sub_title[position];
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                context.startActivity(surf);
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri sms_uri = Uri.parse("smsto:" + number);
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", "hi, ");
                context.startActivity(sms_intent);
            }
        });
        titleText.setText(maintitle[position]);
        imageView.setImageResource(image);
        subtitleText.setText(sub_title[position]);

        return rowView;

    };
}
