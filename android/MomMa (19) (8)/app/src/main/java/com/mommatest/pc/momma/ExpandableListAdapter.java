package com.mommatest.pc.momma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private ViewHolder viewHolder;

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    private Context context;
    private ArrayList<String> groups;
    private ArrayList<ArrayList<ExpendItemDatas>> children;


    public ExpandableListAdapter(Context context, ArrayList<String> groups,
                                 ArrayList<ArrayList<ExpendItemDatas>> children){//, ImageView imageView02) {
        this.context = context;
        this.groups = groups;
        this.children = children;
        //this.imageView02 = imageView02;
    }

    public void addItem(ExpendItemDatas epdata) {
        if (!groups.contains(epdata.getGroup())) {
            groups.add(epdata.getGroup());
        }
        int index = groups.indexOf(epdata.getGroup());
        if (children.size() < index + 1) {
            children.add(new ArrayList<ExpendItemDatas>());
        }
        children.get(index).add(epdata);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Return a child view. You can load your custom layout here.
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        ExpendItemDatas epdata = (ExpendItemDatas) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText("   " + epdata.getName());

        // Depending upon the child type, set the imageTextView01
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Return a group view. You can load your custom layout here.
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView,
                             ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_layout, null);

            viewHolder.iconImageView = (ImageView)convertView.findViewById(R.id.ImageView01);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.ImageView02);

            convertView.setTag(viewHolder);
        } else {
           viewHolder = (ViewHolder)convertView.getTag();
        }
        if (isExpanded) {
            viewHolder.getImageView().setImageResource(R.drawable.ingredient_minus);}
        else{
            viewHolder.getImageView().setImageResource(R.drawable.ingredient_add);
        }

        final String group = (String) getGroup(groupPosition);

        if(groupPosition == 0){
            viewHolder.getIconImageView().setImageResource(children.get(groupPosition).get(0).getImageResource());
        }
        else if(groupPosition == 1){
            viewHolder.getIconImageView().setImageResource(children.get(groupPosition).get(0).getImageResource());
        }
        else if(groupPosition == 2){
            viewHolder.getIconImageView().setImageResource(children.get(groupPosition).get(0).getImageResource());
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);

        tv.setText(group);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}
