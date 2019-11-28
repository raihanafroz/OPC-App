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

import com.example.hp.pollice.R;
import com.example.hp.pollice.AdminViewUserDetails;


public class AdminUserListViewListAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final String[] listID, listUserId, listName, listPhone, listEmail, listAddress, listGender, listTime ;

    public AdminUserListViewListAdapter(
        Activity activity,
        String[] listArrayID,
        String[] listArrayUserId,
        String[] listArrayName,
        String[] listArrayPhone,
        String[] listArrayEmail,
        String[] listArrayAddress,
        String[] listArrayGender,
        String[] listArrayTime ) {

        super(activity, R.layout.admin_immediate_complain_view_list_row, listArrayID);
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.listID = listArrayID;
        this.listUserId = listArrayUserId;
        this.listName = listArrayName;
        this.listPhone = listArrayPhone;
        this.listEmail = listArrayEmail;
        this.listAddress = listArrayAddress;
        this.listGender = listArrayGender;
        this.listTime  = listArrayTime ;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.admin_userlist_view_list_row, null,true);

        LinearLayout ly = (LinearLayout) rowView.findViewById(R.id.admin_userlist_row_list_table_row);
        TextView id = (TextView) rowView.findViewById(R.id.admin_userlist_row_id);
        TextView email = (TextView) rowView.findViewById(R.id.admin_userlist_row_email);
        TextView name = (TextView) rowView.findViewById(R.id.admin_userlist_row_name);

        if(position==0){
            ly.setBackgroundResource(R.drawable.table_header_border);
            ly.setPadding(5,5,5,5);
            id.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            email.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity, AdminViewUserDetails.class);
                    i.putExtra("Title", "User Details");
                    i.putExtra("UserID", listUserId[position]);
                    i.putExtra("UserName", listName[position]);
                    i.putExtra("UserPhone", listPhone[position]);
                    i.putExtra("UserGender", listGender[position]);
                    i.putExtra("UserEmail", listEmail[position]);
                    i.putExtra("UserAddress", listAddress[position]);
                    i.putExtra("Time", listTime[position]);
                    activity.startActivity(i);
                }
            });
        }



        id.setText(String.valueOf(listID[position]));
        email.setText(listEmail[position]);
        name.setText(listName[position]);

        return rowView;
    };
}
