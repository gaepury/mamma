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
import com.mommatest.pc.momma.Model.SearchRankList;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    // ArrayList<ItemData_s> itemDatas;
    View.OnClickListener clickListener;
    ArrayList<SearchRankList> list;
    private Context context;
    private ArrayList<String> index = new ArrayList<>();


    public AdapterSearch(ArrayList<SearchRankList> list,View.OnClickListener clickListener, Context context) {
        super();
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        for(int i=1; i<=this.list.size()+1;i++){
            index.add(""+i);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_review, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchRankList listitem = list.get(position);

        Glide.with(holder.searchimg.getContext()).load(listitem.milk_image).into(holder.searchimg);
        holder.searchmilkname.setText(listitem.milk_name);
        if(listitem.stage == 5)
            holder.searchstage.setText("특"); //이미지내의 단계
        else
            holder.searchstage.setText(listitem.stage+"");
        holder.searchcount.setText(index.get(position));
        holder.searchrating.setRating(listitem.milk_grade);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView searchimg;
        public TextView searchstage;
        public TextView searchmilkname;
        public TextView searchcount;
        public RatingBar searchrating;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.i("MyTag", "ViewHolder 들어옴");
            searchcount = (TextView)itemView.findViewById(R.id.ReviewRecycle_Num_TV);
            searchimg = (ImageView)itemView.findViewById(R.id.ReviewRecycle_Drymilk_IV);
            searchstage = (TextView)itemView.findViewById(R.id.ReviewRecycle_Level_TV);
            searchmilkname = (TextView)itemView.findViewById(R.id.ReviewRecycle_Drymilk_TV);
            searchrating = (RatingBar) itemView.findViewById(R.id.ReviewRecycleGrade_RB);

        }
    }
}