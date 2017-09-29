package com.example.user.muxic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aman on 9/8/2016.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    public int Type_Text = 1;
    Context r_context;

    DBHandler db;
    ArrayList<String> song;
    ArrayList<String> checklist;





    public MusicAdapter(Context context, List<String> songs) {
        r_context = context;
        song = new ArrayList<>(songs);
        checklist=new ArrayList<String>();
        Log.d("songs", String.valueOf(songs));
        db=new DBHandler(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ViewHolder vItem = null;

        if (viewType == Type_Text) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            vItem = new ViewHolder(v, viewType, parent.getContext());
        }

        Log.d("item123", String.valueOf(vItem));
        return vItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.HolderId == 1) {
            holder.Text.setText(song.get(position));
        }

        holder.Text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (holder.Text.isChecked()){
                    holder.Text.setChecked(true);
                    db.new_note(String.valueOf(song.get(position)));
                    Log.d("text12", String.valueOf(song.get(position)));
                    db.access_data();

                }

                else {
                    holder.Text.setChecked(false);


                }
            }

        });

    }

    @Override
    public int getItemCount() {
        Log.d("size", String.valueOf(song.size()));
        return song.size();

    }

    @Override
    public int getItemViewType(int position) {

        return Type_Text;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int HolderId;
        CheckBox Text;
        public ViewHolder(final View itemView, int View_Type, final Context context) {
            super(itemView);
            if (View_Type == Type_Text) {
                Text = (CheckBox) itemView.findViewById(R.id.songText);
                //Text = (TextView) itemView.findViewById(R.id.type1_text);
                HolderId = 1;
            }//If
        }//Constructor

    }//ViewHolder Class

}
