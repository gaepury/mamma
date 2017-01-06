package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.TabIngredientRank;

import java.util.ArrayList;

/**
 * Created by uhees on 2016-12-29.
 */

public class Adapterlevel extends RecyclerView.Adapter<Adapterlevel.ViewHolder> {
    ArrayList<ItemDataLevel> itemDatas;
    View.OnClickListener clickListener;
    ArrayList<TabIngredientRank> list;
    private Context context;
    public View view;
    private ArrayList<String> index = new ArrayList<>();

    public Adapterlevel(ArrayList<TabIngredientRank> list,View.OnClickListener clickListener, Context context) {
        super();
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        for(int i=1; i<=this.list.size()+1;i++){
            index.add(""+i);
        }
    }

    public void append(TabIngredientRank list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<TabIngredientRank> list) {
        this.list = list;
        this.notifyDataSetChanged();
        Log.i("MyTag", "setSource들어옴");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_review, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TabIngredientRank listitem = list.get(position);

        Glide.with(holder.milkimage.getContext()).load(listitem.milk_image).into(holder.milkimage);
        holder.milkname.setText(listitem.milk_name);
        holder.count.setText(index.get(position));
        holder.milkgrade.setRating(listitem.milk_grade);
        holder.stage.setText(listitem.stage+"");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView milkimage;
        public TextView milkname;
        public RatingBar milkgrade;
        public TextView stage;
        public TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            milkimage = (ImageView) itemView.findViewById(R.id.ReviewRecycle_Drymilk_IV);
            milkname = (TextView) itemView.findViewById(R.id.ReviewRecycle_Drymilk_TV);
            milkgrade = (RatingBar) itemView.findViewById(R.id.ReviewRecycleGrade_RB);
            count = (TextView) itemView.findViewById(R.id.ReviewRecycle_Num_TV);
            stage = (TextView) itemView.findViewById(R.id.ReviewRecycle_Level_TV);
        }
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

}