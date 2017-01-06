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
import com.mommatest.pc.momma.Model.MyPageMyReview;

import java.util.ArrayList;

/**
 * Created by uhees on 2017-01-01.
 */

public class AdapterMyreview extends RecyclerView.Adapter<AdapterMyreview.ViewHolder> {
    View.OnClickListener clickListener;
    ArrayList<MyPageMyReview> list;
    private Context context;
    public View view;
    public AdapterMyreview(ArrayList<MyPageMyReview> list,View.OnClickListener clickListener,Context context) {
        super();
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void append(MyPageMyReview list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<MyPageMyReview> list) {
        this.list = list;
        this.notifyDataSetChanged();
        Log.i("MyTag", "setSource들어옴");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_myreview, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyPageMyReview listitem = list.get(position);
        Glide.with(holder.myreview_IV.getContext()).load(listitem.milk_image).into(holder.myreview_IV);
        holder.myreview_TV.setText(listitem.milk_name);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myreview_IV;
        public TextView myreview_TV;

        public ViewHolder(View itemView) {
            super(itemView);
            myreview_IV = (ImageView)itemView.findViewById(R.id.taskmyreview_IV);
            myreview_TV = (TextView)itemView.findViewById(R.id.taskmyreview_TV);

        }
    }
    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

}