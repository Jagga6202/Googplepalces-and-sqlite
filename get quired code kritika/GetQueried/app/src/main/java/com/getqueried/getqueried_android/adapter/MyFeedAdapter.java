package com.getqueried.getqueried_android.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.FeedItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by just_me on 09.11.16.
 */
public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.FeedHolder> {

    List<FeedItem> feedItems;
    Context context;

    public MyFeedAdapter(List<FeedItem> feedItems, Context context) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        holder.questionTitle.setText(feedItems.get(position).targetID);
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_view, parent, false);
        return new FeedHolder(view);
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cv)
        CardView cv;
        @Bind(R.id.question_text)
        TextView questionTitle;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.profile_pic)
        ImageView profilePic;

        FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
