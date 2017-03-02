package com.getqueried.getqueried_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        if (peopleList.get(position).imagePath.length() == 0)
            holder.circleImageView.setImageResource(R.drawable.profile_placeholder_icon);
        else {
            Picasso.with(context).load(peopleList.get(position).imagePath)
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
}
