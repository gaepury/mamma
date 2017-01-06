package com.mommatest.pc.momma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mommatest.pc.momma.Model.Searchingredient;

import java.util.ArrayList;


public class AdapterSearchResult_ingre extends RecyclerView.Adapter<AdapterSearchResult_ingre.ViewHolder> {

    View.OnClickListener clickListener;
    ArrayList<Searchingredient> list;
    private Context context;
    public View view;
    public AdapterSearchResult_ingre(ArrayList<Searchingredient> list,View.OnClickListener clickListener, Context context) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    public void append(Searchingredient list) {
        this.list.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource( ArrayList<Searchingredient> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_searchingredient, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Searchingredient listitem = list.get(position);

        holder.searchingre_name.setText(listitem.ingre_name);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView searchingre_name;

        public ViewHolder(View itemView) {
            super(itemView);

            searchingre_name = (TextView)itemView.findViewById(R.id.searchIngredientName_TV);


        }
    }

}
