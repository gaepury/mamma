package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mommatest.pc.momma.Model.DetailInfo_review;

import java.util.ArrayList;

/**
 * Created by uhees on 2016-12-29.
 */

public class AdapterReview extends RecyclerView.Adapter<AdapterReview.ViewHolder> {
   // ArrayList<ItemDataReview> itemDatas;
    View.OnClickListener clickListener;

    ArrayList<DetailInfo_review> list;
    private Context context;
    public View view;
    private ArrayList<String> index = new ArrayList<>();

    public AdapterReview(ArrayList<DetailInfo_review> list, View.OnClickListener clickListener, Context context) {
        super();
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        for(int i=1; i<=this.list.size()+1;i++){
            index.add(""+i);
        }
    }

    public void append(DetailInfo_review list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<DetailInfo_review> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detailmilkreview, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DetailInfo_review listitem = list.get(position);

        holder.detailreviewtitle.setText(listitem.title);
        holder.detailreviewwriter.setText(listitem.review_writer);
        holder.detailreviewstage.setRating(listitem.review_grade);
        holder.detailreviewcount.setText(index.get(position));



    }
    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView detailreviewtitle;
        public RatingBar detailreviewstage;
        public TextView detailreviewwriter;
        public TextView detailreviewcount;

        public ViewHolder(View itemView) {
            super(itemView);
            detailreviewcount = (TextView) itemView.findViewById(R.id.detailmilknum) ;
            detailreviewtitle = (TextView) itemView.findViewById(R.id.oneline_TV);
            detailreviewwriter = (TextView)itemView.findViewById(R.id.user_TV);
            detailreviewstage = (RatingBar)itemView.findViewById(R.id.ReviewGrade_RB);


        }
    }
}


