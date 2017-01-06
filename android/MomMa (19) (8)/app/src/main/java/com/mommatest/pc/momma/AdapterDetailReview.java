package com.mommatest.pc.momma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;

/**
 * Created by uhees on 2016-12-29.
 */

public class AdapterDetailReview extends RecyclerView.Adapter<ViewHolders> {
    ArrayList<ItemDetailReview> itemDatas;
    View.OnClickListener clickListener;
    public AdapterDetailReview(ArrayList<ItemDetailReview> itemDatas, View.OnClickListener clickEvent) {
        this.itemDatas = itemDatas;
        this.clickListener = clickEvent;
    }


    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detailmilkreview, parent,false);
        ViewHolders viewHolder = new ViewHolders(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolders holder, int position) {
        holder.Num_DR_TV.setText(itemDatas.get(position).num);
        holder.Title_DR_TV.setText(itemDatas.get(position).title);
        holder.User_DR_TV.setText(itemDatas.get(position).user);
        holder.Rating_DR_RB.setRating(itemDatas.get(position).rate);
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}
