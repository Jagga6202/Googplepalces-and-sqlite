package com.example.ideafoundation.meetaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ideafoundation.meetaapp.R;
import com.example.ideafoundation.meetaapp.model.Contact;
import com.example.ideafoundation.meetaapp.model.Need;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ideafoundation on 06/02/17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {


    private ArrayList<Contact> dataSet;
    private ArrayList<Contact> orig;
    Context mContext;
    public ContactAdapter(ArrayList<Contact> data, Context context) {
        super(context, R.layout.list_contact, data);
        this.dataSet = data;
        this.mContext=context;
        this.orig=new ArrayList<Contact>();
        this.orig.addAll(data);
;
    }
    private static class ViewHolder {
        TextView place_item,title_place;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ContactAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ContactAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_contact, parent, false);
            viewHolder.place_item = (TextView) convertView.findViewById(R.id.contacttext);
            viewHolder.title_place = (TextView) convertView.findViewById(R.id.title_place);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContactAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.place_item.setText(dataModel.getName());
        viewHolder.title_place.setText(dataModel.getFirstWord());

        // Return the completed view to render on screen
        return convertView;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(orig);
        } else {
            for (Contact wp : orig) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    dataSet.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
