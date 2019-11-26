package com.example.hp.pollice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.pollice.R;

public class UserGridAdapter  extends BaseAdapter {
    private Context context;
    private final String[] cardTitle;
    private final String[] cardLabel;

    public UserGridAdapter(Context context, String[] title, String[] label) {
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
            gridView = inflater.inflate(R.layout.user_grid_view_item, null);

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.user_grid_item_title);
            TextView textLabel = (TextView) gridView.findViewById(R.id.user_grid_item_label);
            textView.setText(cardTitle[position]);
            textLabel.setText(cardLabel[position]);

            //        Log.d("json adapter", cardLabel[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.user_grid_item_image);

//            String mobile = cardTitle[position];

            if (cardTitle[position].equals("Immediate Complain")) {
                imageView.setImageResource(R.drawable.complain1);
            } else if (cardTitle[position].equals("Complain For Me")) {
                imageView.setImageResource(R.drawable.complain2);
            } else if (cardTitle[position].equals("Complain For Other")) {
                imageView.setImageResource(R.drawable.complain3);
            } else {
                imageView.setImageResource(R.drawable.total);
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
