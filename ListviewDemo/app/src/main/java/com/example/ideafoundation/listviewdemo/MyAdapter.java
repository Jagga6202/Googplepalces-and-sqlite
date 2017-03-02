package com.example.ideafoundation.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 21/02/17.
 */

public class MyAdapter extends ArrayAdapter<Model> {

    private ArrayList<Model> arrayList;
    private Context context;
    public MyAdapter(Context context, ArrayList<Model> arrayList) {
        super(context,R.layout.list_item,arrayList);
        this.context=context;
        this.arrayList=arrayList;

    }

    private static class ViewHolder {
        TextView place_item;
        ImageView imageView;


    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        Model dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        MyAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new MyAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.place_item = (TextView) convertView.findViewById(R.id.textview);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);



            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.place_item.setText(dataModel.getName());
        // Return the completed view to render on screen
        return convertView;
    }
    public void remove(int position)
    {
        arrayList.remove(position);
        notifyDataSetChanged();
    }
}
