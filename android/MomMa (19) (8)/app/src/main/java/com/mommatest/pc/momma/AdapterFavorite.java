package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.MyPageBookMark;

import java.util.ArrayList;

/**
 * Created by uhees on 2017-01-01.
 */

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
   // ArrayList<ItemFavorite> itemDatas;
    View.OnClickListener clickListener;

    ArrayList<MyPageBookMark> list;
    private Context context;
    public View view;
    public AdapterFavorite(ArrayList<MyPageBookMark> list,View.OnClickListener clickListener, Context context) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void append(MyPageBookMark list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<MyPageBookMark> list) {
        this.list = list;
        this.notifyDataSetChanged();
        Log.i("MyTag", "setSource들어옴");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_favorite, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyPageBookMark listitem = list.get(position);

        Glide.with(holder.myfavorite_IV.getContext()).load(listitem.milk_image).into(holder.myfavorite_IV);
        holder.myfavorite_TV.setText(listitem.milk_name);
    }


    @Override
    public int getItemCount() {return (list != null) ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myfavorite_IV;
        public TextView myfavorite_TV;

        public ViewHolder(View itemView) {
            super(itemView);
            myfavorite_IV = (ImageView)itemView.findViewById(R.id.taskfavorite_IV);
            myfavorite_TV = (TextView)itemView.findViewById(R.id.taskfavorite_TV);



        }
    }
}