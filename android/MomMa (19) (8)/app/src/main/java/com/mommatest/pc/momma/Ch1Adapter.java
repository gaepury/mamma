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

import com.mommatest.pc.momma.Model.ReviewRankList;

import java.util.ArrayList;


public class Ch1Adapter extends RecyclerView.Adapter<Ch1Adapter.ViewHolder> {
    //ArrayList<ItemData> itemDatas;
    View.OnClickListener clickListener;

    //ReviewRankResult.ResultData data;
    ArrayList<ReviewRankList> list;
    private Context context;
    public View view;
    private ArrayList<String> index = new ArrayList<>();

    // 생성자
    public Ch1Adapter(ArrayList<ReviewRankList> list,View.OnClickListener clickListener, Context context) {
        super();
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        Log.i("MyTag", "생성자 진입");
        for(int i=1; i<=this.list.size()+1;i++){
            index.add(""+i);
        }
    }

    public void append(ReviewRankList list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<ReviewRankList> list) {
        this.list = list;
        this.notifyDataSetChanged();
        Log.i("MyTag", "setSource들어옴");
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("MyTag", "onCreateViewHolder 들어옴");
        view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_review, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.i("MyTag", "onBindViewHolder 들어옴");
        ReviewRankList listitem = list.get(position);

        Glide.with(holder.reviewimg.getContext()).load(listitem.milk_image).into(holder.reviewimg);  //이미지
        holder.reviewmilkname.setText(listitem.milk_name);//이름
        if(listitem.stage == 5)
        holder.reviewstage.setText("특"); //이미지내의 단계
        else
            holder.reviewstage.setText(listitem.stage+"");
        holder.reviewcount.setText(index.get(position));        //리스트 각 카운트
        holder.reviewrating.setRating(listitem.milk_grade);     //평점
        Log.i("MyTag", "milk_name : " + listitem.milk_name);

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView reviewimg;
        public TextView reviewstage;
        public TextView reviewmilkname;
        public TextView reviewcount;
        public RatingBar reviewrating;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.i("MyTag", "ViewHolder 들어옴" );

            reviewcount = (TextView)itemView.findViewById(R.id.ReviewRecycle_Num_TV);
            reviewimg = (ImageView)itemView.findViewById(R.id.ReviewRecycle_Drymilk_IV);
            reviewstage = (TextView)itemView.findViewById(R.id.ReviewRecycle_Level_TV);
            reviewmilkname = (TextView)itemView.findViewById(R.id.ReviewRecycle_Drymilk_TV);
            reviewrating = (RatingBar) itemView.findViewById(R.id.ReviewRecycleGrade_RB);

        }
    }



}
