package com.example.user.muxic;

/**
 * Created by Aman on 8/20/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MusicActivity extends Activity {
    ListView musiclist;
    Cursor cursor;
    int column_index;
    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        init_phone_music_grid();
    }

    private void init_phone_music_grid() {
        System.gc();
        String[] proj = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE };
        cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = cursor.getCount();
        musiclist = (ListView) findViewById(R.id.mulist);
        musiclist.setAdapter(new MusicAdapter(getApplicationContext()));


    }

    public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            System.gc();
            TextView tv = new TextView(mContext.getApplicationContext());
            String id = null;
            if (convertView == null) {

                column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                cursor.moveToPosition(position);
                id = cursor.getString(column_index);
                column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                cursor.moveToPosition(position);
                id += " Size(KB):" + cursor.getString(column_index);
                tv.setText(id);
            } else
                tv = (TextView) convertView;
            return tv;
        }
    }
}