package com.getqueried.getqueried_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getqueried.getqueried_android.ApiCallUtils;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.Metadata;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.RecyclerViewItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.getqueried.getqueried_android.utils.Constants.URL.getProfileImage;

/**
 * Created by Gaurav on 10/5/2016.
 */
public class FindPeopleFragmentAdapter extends RecyclerView.Adapter<FindPeopleFragmentAdapter.FindPeopleViewHolder> {

    //AddFollowerInterface addFollowerListener;
    View view;
    NetworkActivity networkActivity;
    List<Metadata> peopleList;
    Context context;

    public FindPeopleFragmentAdapter(Context context, View view, List<Metadata> peopleList, NetworkActivity activity) {
        this.peopleList = peopleList;
        this.context = context;
        this.networkActivity = activity;
        this.view = view;
        //this.addFollowerListener = listener;
    }


    @Override
    public FindPeopleFragmentAdapter.FindPeopleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_people, viewGroup, false);
        return new FindPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindPeopleViewHolder holder, int position) {

        holder.textView.setText(peopleList.get(position).name);
        holder.imageButton.setOnClickListener(v -> {
            ApiCallUtils.followUser(networkActivity, view, peopleList.get(position).userID, peopleList.get(position).name);
            removeAt(position);
        });

        if (peopleList.get(position).image_path.length() == 0)
        {
            holder.circleImageView.setImageResource(R.drawable.profile_placeholder_icon);
        }

        else
        {
            Log.e("pep",peopleList.get(position).image_path.toString());
            Picasso.with(context).load(getProfileImage+peopleList.get(position).image_path)
                    .placeholder(R.drawable.profile_placeholder_icon)
                    .into(holder.circleImageView);
        }

    }

    public void removeAt(int position) {
        peopleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, peopleList.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class FindPeopleViewHolder extends RecyclerView.ViewHolder {

        LinearLayout holderLayout;
        CircleImageView circleImageView;
        TextView textView;
        ImageButton imageButton;

        public FindPeopleViewHolder(View itemView) {
            super(itemView);
            holderLayout = (LinearLayout) itemView.findViewById(R.id.find_people_layout);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            textView = (TextView) itemView.findViewById(R.id.textView_user_name);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton_follow_user);
        }
    }
    public void setFilter(List<Metadata> peopleList,String query) {
       // peopleList = new ArrayList<>();
        List<Metadata> peopleList1= new ArrayList<>();
        for(Metadata metadata:peopleList)
        {
            final String text = metadata.name;
            if (text.contains(query)) {
                peopleList1.add(metadata);
            }
        }
       // peopleList1.addAll(peopleList);
        notifyDataSetChanged();
    }

}
