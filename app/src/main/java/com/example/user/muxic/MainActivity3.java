package com.example.user.muxic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.net.Uri;
import android.provider.MediaStore;
import android.database.Cursor;

public class MainActivity3 extends AppCompatActivity {

    ListView list;

    int[] images = {R.drawable.bale,
            R.drawable.ic_attach_file_black_48dp,
            R.drawable.ic_border_all_black_48dp,
            R.drawable.ic_format_align_justify_black_48dp,
            R.drawable.ic_insert_chart_black_48dp,
            R.drawable.ic_border_outer_black_48dp,
            R.drawable.ic_format_size_black_48dp};

    String[] titles = {"Aman Gupta","Attachment","Window","Stripes","Bars","Dots","Font"};

    String[] Tabs = {"CALLS","CHATS","CONTACTS"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button switchButton= (Button)findViewById(R.id.mn);
        switchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MusicActivity.class);
                startActivity(intent);

            }
        });

        list = (ListView) findViewById(R.id.list);
        myadapter adapter = new myadapter(getApplicationContext(),titles,images);
        list.setAdapter((ListAdapter) adapter);





    }
}
