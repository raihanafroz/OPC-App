package com.example.hp.pollice.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.pollice.AdminViewComplainDetails;
import com.example.hp.pollice.R;

public class AdminComplainForOtherViewListAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final String[] listID, listOtherName, listOtherPhone, listOtherAddress, listUserName, listEmail, listLatitude, listLongitude, listCause, listComplainAddress, listDescription, listTime, listStationName, listStationId, listAddress, listGender, listUserPhone;

    public AdminComplainForOtherViewListAdapter(
        Activity activity,
        String[] listArrayID,
        String[] listArrayOtherName,
        String[] listArrayOtherPhone,
        String[] listArrayOtherAddres,
        String[] listArrayUserName,
        String[] listArrayEmail,
        String[] listArrayLatitude,
        String[] listArrayLongitude,
        String[] listArrayCause,
        String[] listArrayComplainAddress,
        String[] listArrayDescription,
        String[] listArrayTime,
        String[] listArrayStationName,
        String[] listArrayStationId,
        String[] listArrayAddress,
        String[] listArrayGender,
        String[] listArrayUserPhone) {

        super(activity, R.layout.admin_immediate_complain_view_list_row, listArrayID);
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.listID = listArrayID;
        this.listOtherName = listArrayOtherName;
        this.listOtherPhone = listArrayOtherPhone;
        this.listOtherAddress = listArrayOtherAddres;
        this.listUserName = listArrayUserName;
        this.listEmail = listArrayEmail;
        this.listLatitude = listArrayLatitude;
        this.listLongitude = listArrayLongitude;
        this.listCause = listArrayCause;
        this.listComplainAddress = listArrayComplainAddress;
        this.listDescription = listArrayDescription;
        this.listTime = listArrayTime;
        this.listStationName = listArrayStationName;
        this.listStationId = listArrayStationId;
        this.listAddress = listArrayAddress;
        this.listGender = listArrayGender;
        this.listUserPhone = listArrayUserPhone;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.admin_complain_for_other_view_list_row, null,true);

        LinearLayout ly = (LinearLayout) rowView.findViewById(R.id.admin_complain_for_other_view_list_table_row) ;
        TextView id = (TextView) rowView.findViewById(R.id.admin_complain_for_other_view_id);
        TextView email = (TextView) rowView.findViewById(R.id.admin_complain_for_other_view_email);
        TextView station = (TextView) rowView.findViewById(R.id.admin_complain_for_other_view_station);
        TextView time = (TextView) rowView.findViewById(R.id.admin_complain_for_other_view_time);
        final String complainDetails = "Time: "+listTime[position]+"\nLatitude: "+listLatitude[position]+"\nLongitude: "
                +listLongitude[position]+"\nVictim Name: "+listOtherName[position]+"\nVictim Phone: "+listOtherPhone[position]
                +"\nVictim Address: "+listOtherAddress[position]+"\nCause: "+listCause[position]+"\nComplain Area: "
                +listComplainAddress[position]+"\nDescription: "+listDescription[position];
        if(position==0){
            ly.setBackgroundResource(R.drawable.table_header_border);
            ly.setPadding(5,5,5,5);
            id.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            email.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            station.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity, AdminViewComplainDetails.class);
                    i.putExtra("Title", "Complain");
                    i.putExtra("UserName", "Name: "+listUserName[position]);
                    i.putExtra("UserPhone", "NO: "+listUserPhone[position]);
                    i.putExtra("UserGender", "Gender: "+listGender[position]);
                    i.putExtra("UserEmail", "Email: "+listEmail[position]);
                    i.putExtra("UserAddress", "Address: "+listAddress[position]);
                    i.putExtra("StationName", "Station Name: "+listStationName[position]);
                    i.putExtra("ComplainDetails", complainDetails);
                    activity.startActivity(i);
                }
            });
        }



        id.setText(String.valueOf(listID[position]));
        email.setText(listEmail[position]);
        station.setText(listStationName[position]);
        time.setText(listTime[position]);

        return rowView;
    };
}
