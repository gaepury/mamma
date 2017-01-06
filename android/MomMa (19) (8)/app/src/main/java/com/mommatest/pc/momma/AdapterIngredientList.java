package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mommatest.pc.momma.Model.Detailingredient_rank;

import java.util.ArrayList;

/**
 * Created by uhees on 2016-12-30.
 */

public class AdapterIngredientList extends RecyclerView.Adapter<AdapterIngredientList.ViewHolder> {
    //ArrayList<ItemIngredientList> itemDatas;
    View.OnClickListener clickListener;

    ArrayList<Detailingredient_rank> list;
    private Context context;
    public View view;
    int convertint;
    String covertunit;

    public AdapterIngredientList(ArrayList<Detailingredient_rank> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    public void append(Detailingredient_rank list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<Detailingredient_rank> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_ingredientlist, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Detailingredient_rank listitem = list.get(position);

        holder.ingrename.setText(listitem.ingre_name);
        holder.ingrenum.setText(listitem.ingre_content);
        holder.ingreunit.setText(listitem.content_unit);


    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingrename;
        public TextView ingrenum;
        public TextView ingreunit;

        public ViewHolder(View itemView) {
            super(itemView);

            ingrename = (TextView) itemView.findViewById(R.id.IngredientName_TV);
            ingrenum = (TextView) itemView.findViewById(R.id.ingredientNum_TV);
            ingreunit = (TextView) itemView.findViewById(R.id.ingreunit);

        }
    }

}
