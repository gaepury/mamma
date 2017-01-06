package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.Searchmilk;

import java.util.ArrayList;


public class AdapterSearchResult extends RecyclerView.Adapter<AdapterSearchResult.ViewHolder> {

    View.OnClickListener clickListener;
    ArrayList<Searchmilk> list;
    private Context context;
    public View view;
    public AdapterSearchResult(ArrayList<Searchmilk> list,View.OnClickListener clickListener, Context context) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    public void append(Searchmilk list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<Searchmilk> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_searchresult, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Searchmilk listitem = list.get(position);

        Glide.with(holder.searchmilkimg.getContext()).load(listitem.milk_image).into(holder.searchmilkimg);
        holder.searchmilkname.setText(listitem.milk_name);
        holder.searchmilkrating.setRating(listitem.milk_grade);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView searchmilkimg;
        public TextView searchmilkname;
        public RatingBar searchmilkrating;

        public ViewHolder(View itemView) {
            super(itemView);

            searchmilkimg = (ImageView)itemView.findViewById(R.id.SearchResultMilk_IV);
            searchmilkname = (TextView)itemView.findViewById(R.id.SearchResultMilk_TV);
            searchmilkrating = (RatingBar)itemView.findViewById(R.id.SearchResultGrade_RB);

        }
    }

}
