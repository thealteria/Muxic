package com.example.user.muxic;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aman on 9/8/2016.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    public int Type_Text = 1;
    Context r_context;

    List<String> songs;

    public MusicAdapter (Context context, List<String> songs){
        r_context=context;
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v; ViewHolder vItem = null;

        if (viewType==Type_Text){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            vItem = new ViewHolder(v,viewType,parent.getContext());
        }

        return vItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        String[] Text_name;

        holder.setIsRecyclable(false);

        if(holder.HolderId==1){
            holder.Text.setText(songs.get(position));
          }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public int getItemViewType (int position){
        return Type_Text;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int HolderId; TextView Text;

        public ViewHolder(View itemView, int View_Type, Context context) {
            super(itemView);

            if (View_Type==Type_Text){
                //Text = (TextView) itemView.findViewById(R.id.type1_text);
                HolderId = 1;
            }//If

        }//Constructor

    }//ViewHolder Class

}//MusicAdapter Class
