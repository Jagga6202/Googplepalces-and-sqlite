package com.example.ideafoundation.listcheckboxdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 08/02/17.
 */

public class NewAdapter extends BaseAdapter {
    ArrayList<Service> actorList;
    LayoutInflater vi;
    Context context;

    public NewAdapter(Context context,ArrayList<Service> objects) {
        this.context= context;
        Log.e("size",""+objects.size());
        this.vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.actorList = objects;
    }

    @Override
    public int getCount() {
        return actorList.size();
    }

    @Override
    public Object getItem(int position) {
        return actorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new NewAdapter.ViewHolder();
            convertView = vi.inflate(R.layout.list_new, null);

            holder.tvName = (TextView) convertView.findViewById(R.id.textView2);

            convertView.setTag(holder);
        } else
            holder = (NewAdapter.ViewHolder) convertView.getTag();
Log.e("values",""+actorList.get(position).getName());
        holder.tvName.setText(actorList.get(position).getName());


        return convertView;

    }
    static class ViewHolder {

        public TextView tvName;


    }
}
