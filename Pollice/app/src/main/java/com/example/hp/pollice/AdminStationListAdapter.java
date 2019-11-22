package com.example.hp.pollice;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdminStationListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] listName;
    private final String[] listID;
    private final String[] listPhone;
    private final String[] listLatitude;
    private final String[] listLongitude;
    private final String[] listThanaId;

    public AdminStationListAdapter(Activity context, String[] id, String[] name, String[] phone, String[] latitude, String[] longitude, String[] thanaID) {
        super(context, R.layout.admin_station_list_row, name);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.listID = id;
        this.listName = name;
        this.listPhone = phone;
        this.listLatitude = latitude;
        this.listLongitude = longitude;
        this.listThanaId = thanaID;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.admin_station_list_row, null,true);

        LinearLayout ly = (LinearLayout) rowView.findViewById(R.id.station_list_table_row) ;
        TextView id = (TextView) rowView.findViewById(R.id.station_list_table_row_station_id);
        TextView name = (TextView) rowView.findViewById(R.id.station_list_table_row_station_name);
        TextView phone = (TextView) rowView.findViewById(R.id.station_list_table_row_station_phone);
        TextView latitude = (TextView) rowView.findViewById(R.id.station_list_table_row_station_latitude);
        TextView longitude = (TextView) rowView.findViewById(R.id.station_list_table_row_station_longitude);

        if(position==0){
            ly.setBackgroundResource(R.drawable.table_header_border);
//            ly.setMinimumHeight(40);
//            ly.setPadding(5,5,5,5);
//            id.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            phone.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            latitude.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            longitude.setTypeface(longitude.getTypeface(), Typeface.BOLD);;
        }

        id.setText(String.valueOf(listID[position]));
        name.setText(listName[position]);
        phone.setText(listPhone[position]);
        longitude.setText(listLatitude[position]);
        latitude.setText(listLongitude[position]);

        return rowView;
    };
}