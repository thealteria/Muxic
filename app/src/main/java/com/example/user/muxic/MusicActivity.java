package com.example.user.muxic;

/**
 * Created by Aman on 8/20/2016.
 */
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity {
    private final String MEDIA_PATH = new String("/sdcard/");
    private List<String> song = new ArrayList<String>();
    ListView musiclist;
    Cursor cursor;
    int column_index;
    int count;
    private List<String> songs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        init_phone_music_grid();
        initList();
    }

    private void initList() {

        RecyclerView list = (RecyclerView) findViewById(R.id.mulist);

        MusicAdapter madapter = new MusicAdapter(getApplicationContext(),songs);
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
