package com.example.user.muxic;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity  {

    Cursor cursor;
    int count;
    Button send,bluetooth1;
    private List<String> songs = new ArrayList<>();
    MusicAdapter madapter;
    DBHandler db;
    public String path=Environment.getExternalStorageDirectory().getAbsolutePath() ;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MusicActivity.this,choice.class);
        startActivity(intent);
        return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        init_phone_music_grid();
        initList();
        db=new DBHandler(getApplicationContext());
        db.resetTable_Records();
        send= (Button) findViewById(R.id.send);

        final File file=new File(path + "/myfile.txt");

        bluetooth1= (Button) findViewById(R.id.bluetooth);
        bluetooth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MusicActivity.this,bluetooth.class);
                startActivity(intent);
                finish();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> data= db.access_data();
                Log.d("data123", String.valueOf(data));


                try {
                    FileOutputStream fo=new FileOutputStream(file);
                    fo.write(String.valueOf(data).getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(file.exists()) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(data));
                    sendIntent.setType("text/*");
                    startActivity(sendIntent);
                }

            }
        });

    }


    private void initList() {

        RecyclerView list = (RecyclerView) findViewById(R.id.mulist);


        madapter = new MusicAdapter(getApplicationContext(),songs);
        list.setAdapter(madapter);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(lm);
        list.setItemAnimator(new DefaultItemAnimator());

    }


    private void init_phone_music_grid() {
        String[] proj = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE };
        cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = cursor.getCount();
        for (int i =0;i<count ;i++)
        {
            int column_index = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            cursor.moveToPosition(i);
            Log.d("songsname",cursor.getString(column_index));
            songs.add(cursor.getString(column_index));
        }
    }

}
