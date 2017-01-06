package com.mommatest.pc.momma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;

public class Ch1Adapter_s extends RecyclerView.Adapter<ViewHolders> {
    ArrayList<ItemData_s> itemDatas;
    View.OnClickListener clickListener;
    public Ch1Adapter_s(ArrayList<ItemData_s> itemDatas, View.OnClickListener clickEvent) {
        this.itemDatas = itemDatas;
        this.clickListener = clickEvent;
    }


    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_search, parent,false);
        ViewHolders viewHolder = new ViewHolders(itemView);

        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolders holder, int position) {
        holder.Num_S_TV.setText(itemDatas.get(position).num);
        holder.Drymilk_S_IV.setImageResource(itemDatas.get(position).image);
        holder.Drymilk_S_TV.setText(itemDatas.get(position).name);
        holder.Level_S_TV.setText(itemDatas.get(position).level);
        holder.Level_S_IV.setImageResource(itemDatas.get(position).imagelevel);
        holder.Rating_S_RB.setRating(itemDatas.get(position).rate);
    }

    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

}
