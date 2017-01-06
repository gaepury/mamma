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
import com.mommatest.pc.momma.Model.Detailmanufactor_drymilk;

import java.util.ArrayList;



public class AdapterDetailcompany_milk extends RecyclerView.Adapter<AdapterDetailcompany_milk.ViewHolder> {

    View.OnClickListener clickListener;
    ArrayList<Detailmanufactor_drymilk> list;
    private Context context;
    public View view;
    public AdapterDetailcompany_milk(ArrayList<Detailmanufactor_drymilk> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void append(Detailmanufactor_drymilk list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<Detailmanufactor_drymilk> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detailcompany_search, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Detailmanufactor_drymilk listitem = list.get(position);

        Glide.with(holder.detailcompamymilkimg.getContext()).load(listitem.milk_image).into(holder.detailcompamymilkimg);
        holder.detailcompanymilkname.setText(listitem.milk_name);
        if(listitem.milk_stage==5)
            holder.detailcompanymilkstage.setText("특");
        else {
            holder.detailcompanymilkstage.setText(listitem.milk_stage + "");
        }
        holder.detailcompanymilkrating.setRating(listitem.milk_grade);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView detailcompamymilkimg;
        public TextView detailcompanymilkname;
        public RatingBar detailcompanymilkrating;
        public TextView detailcompanymilkstage;

        public ViewHolder(View itemView) {
            super(itemView);

            detailcompamymilkimg = (ImageView)itemView.findViewById(R.id.detailcompany_milk_img);
            detailcompanymilkname = (TextView)itemView.findViewById(R.id.detailcompany_milk_name);
            detailcompanymilkstage = (TextView)itemView.findViewById(R.id.detailcompany_milk_stage);
            detailcompanymilkrating = (RatingBar)itemView.findViewById(R.id.detailcompany_milk_rating);

        }
    }

}
