package com.example.hp.pollice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String mobile = cardTitle[position];

//            if (mobile.equals("Windows")) {
                imageView.setImageResource(R.drawable.user_male);
//            } else if (mobile.equals("iOS")) {
//                imageView.setImageResource(R.drawable.ios_logo);
//            } else if (mobile.equals("Blackberry")) {
//                imageView.setImageResource(R.drawable.blackberry_logo);
//            } else {
//                imageView.setImageResource(R.drawable.android_logo);
//            }

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
