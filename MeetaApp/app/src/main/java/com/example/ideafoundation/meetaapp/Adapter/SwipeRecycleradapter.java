package com.example.ideafoundation.meetaapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideafoundation.meetaapp.R;
import com.example.ideafoundation.meetaapp.model.Need;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ideafoundation on 20/02/17.
 */

public class SwipeRecycleradapter extends RecyclerView.Adapter<SwipeRecycleradapter.MyViewHolder>  {

    private ArrayList<Need> dataSet;

    Context mContext;



    public SwipeRecycleradapter(java.util.ArrayList<Need> data, Context context) {

        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.dataTextView.setText(dataSet.get(position).getPlaces());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void remove(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataTextView;
        MyViewHolder(View view) {
            super(view);
            dataTextView = (TextView) view.findViewById(R.id.txt_data);
        }
    }
}
