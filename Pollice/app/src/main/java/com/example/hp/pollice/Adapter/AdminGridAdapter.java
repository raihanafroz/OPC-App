package com.example.hp.pollice.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.pollice.R;

public class AdminGridAdapter extends BaseAdapter {
    private Context context;
    private final String[] cardTitle;
    private final String[] cardLabel;

    public AdminGridAdapter(Context context, String[] title, String[] label) {
        this.context = context;
        this.cardTitle = title;
        this.cardLabel = label;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.admin_grid_view_item, null);

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_title);
            TextView textLabel = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(cardTitle[position]);
            textLabel.setText(cardLabel[position]);

            //        Log.d("json adapter", cardLabel[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

//            String mobile = cardTitle[position];

            if (cardTitle[position].equals("Users")) {
                imageView.setImageResource(R.drawable.user_male);
            } else if (cardTitle[position].equals("Police Station")) {
                imageView.setImageResource(R.drawable.police_station);
            } else if (cardTitle[position].equals("Immediate Complain")) {
                imageView.setImageResource(R.drawable.complain1);
            } else if (cardTitle[position].equals("Complain For Themself")) {
                imageView.setImageResource(R.drawable.complain2);
            } else if (cardTitle[position].equals("Complain For Other")) {
                imageView.setImageResource(R.drawable.complain3);
            } else {
                imageView.setImageResource(R.drawable.user_24dp);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return cardTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
