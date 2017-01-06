package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.Searchmanufactor;

import java.util.ArrayList;


public class AdapterSearchResult_manu extends RecyclerView.Adapter<AdapterSearchResult_manu.ViewHolder> {

    View.OnClickListener clickListener;
    ArrayList<Searchmanufactor> list;
    private Context context;
    public View view;
    public AdapterSearchResult_manu(ArrayList<Searchmanufactor> list,View.OnClickListener clickListener, Context context) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void append(Searchmanufactor list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<Searchmanufactor> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_searchmanufactor, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Searchmanufactor listitem = list.get(position);

        Glide.with(holder.searchcompanyimg.getContext()).load(listitem.manu_image).into(holder.searchcompanyimg);
        holder.searchcompanyname.setText(listitem.manu_name);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView searchcompanyimg;
        public TextView searchcompanyname;

        public ViewHolder(View itemView) {
            super(itemView);

            searchcompanyimg = (ImageView)itemView.findViewById(R.id.searchcompany_IV);
            searchcompanyname = (TextView)itemView.findViewById(R.id.searchcompany_TV);

        }
    }

}
