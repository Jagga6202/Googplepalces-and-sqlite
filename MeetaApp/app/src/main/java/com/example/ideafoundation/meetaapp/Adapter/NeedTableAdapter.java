package com.example.ideafoundation.meetaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.example.ideafoundation.meetaapp.R;
import com.example.ideafoundation.meetaapp.model.Need;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ideafoundation on 31/01/17.
 */

public class NeedTableAdapter extends ArrayAdapter<Need> {

    private ArrayList<Need> dataSet;
    public ArrayList<Need> orig;
    Context mContext;
    public NeedTableAdapter(ArrayList<Need> data, Context context) {
        super(context, R.layout.need_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.orig=new ArrayList<Need>();
        this.orig.addAll(data);
    }
    private static class ViewHolder {
        TextView place_item;
        CheckBox checkBox;

    }

    @Override
    public int getCount() {
        return dataSet.size();
    }



    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Need dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.need_item, parent, false);
            viewHolder.place_item = (TextView) convertView.findViewById(R.id.place_item);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cBox);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.place_item.setText(dataModel.getPlaces());
        viewHolder.checkBox.setChecked(dataSet.get(position).isSelected());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = ((CheckBox)v).isChecked();
                dataSet.get(position).setSelected(isSelected);
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(orig);
        } else {
            for (Need wp : orig) {
                if (wp.getPlaces().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    dataSet.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Need> getSelectActorList(){
        ArrayList<Need> list = new ArrayList<>();
        for(int i=0;i<dataSet.size();i++){
            if(dataSet.get(i).isSelected())
                list.add(dataSet.get(i));
        }
        return list;
    }
}
