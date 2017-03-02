package com.example.ideafoundation.meetaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ideafoundation.meetaapp.R;
import com.example.ideafoundation.meetaapp.model.Need;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ideafoundation on 08/02/17.
 */

public class UserNeedAdapter  extends ArrayAdapter<Need> {

    private ArrayList<Need> dataSet;

    Context mContext;
    public UserNeedAdapter(ArrayList<Need> data, Context context) {
        super(context, R.layout.user_need_item, data);
        this.dataSet = data;
        this.mContext=context;

    }
    private static class ViewHolder {
        TextView place_item;


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
        UserNeedAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new UserNeedAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_need_item, parent, false);
            viewHolder.place_item = (TextView) convertView.findViewById(R.id.place_item1);



            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserNeedAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.place_item.setText(dataModel.getPlaces());
        // Return the completed view to render on screen
        return convertView;
    }
}
