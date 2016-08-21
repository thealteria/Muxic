package com.example.user.muxic;

import android.content.Context;

/**
 * Created by Aman on 8/20/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class myadapter extends ArrayAdapter {
    Context context;
    private String[] name;
    private int[] image;

    public myadapter(Context context, String[] name, int[] image) {
        super(context,R.layout.list1,name);
        this.context=context;
        this.name=name;
        this.image=image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(position==0){
            View rowview = inflater.inflate(R.layout.listitem1, parent, false);
            TextView text1 = (TextView) rowview.findViewById(R.id.text);
            ImageView image1= (ImageView) rowview.findViewById(R.id.image);
            text1.setText(name[position]);
            image1.setImageResource(image[position]);

            return rowview;

        }
        else {
            View rowview = inflater.inflate(R.layout.list12, parent, false);
            TextView text1 = (TextView) rowview.findViewById(R.id.text);
            text1.setText(name[position]);
            ImageView image1= (ImageView) rowview.findViewById(R.id.image);
            image1.setImageResource(image[position]);

            return rowview;

        }


    }


}
