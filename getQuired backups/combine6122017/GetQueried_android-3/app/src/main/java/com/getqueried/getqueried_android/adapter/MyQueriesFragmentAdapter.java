package com.getqueried.getqueried_android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.Query;
import com.getqueried.getqueried_android.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gaurav on 9/30/2016.
 */
public class MyQueriesFragmentAdapter extends RecyclerView.Adapter<MyQueriesFragmentAdapter.MyQueriesViewHolder> {

    List<Query> queries;
    Context context;

    public MyQueriesFragmentAdapter(Context context, List<Query> queries) {
        this.queries = queries;
        this.context = context;
    }

    @Override
    public MyQueriesFragmentAdapter.MyQueriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card_view, viewGroup, false);
        return new MyQueriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyQueriesViewHolder holder, int position) {
        holder.questionTitle.setText(queries.get(position).getQuestion().replaceAll("\"", "").replaceAll("\\[|\\]", ""));
        Log.d("IMAGEEEE", "" + queries.get(position).getQueryPath()+""+queries.size());
        if (queries.get(position).getAvatarPath() != null) {

            Picasso.with(context).load(queries.get(position).getAvatarPath()).into(holder.profilePic);
        } else {
            holder.profilePic.setImageResource(R.drawable.profile_placeholder_icon);
        }
        String queryImagePath =Constants.URL.getQueryImage+queries.get(position).getQueryPath();
        Log.d("IMAGEEEE", "" + queryImagePath);
        if (queryImagePath.length() > 0) {
            String imageUrl = Constants.URL.getQueryImage + queryImagePath;
            Picasso.with(context).load(imageUrl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.picLayout.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        //TODO: bind data with view
        //holder.likeView.setText();
        //holder.shareView.setText();
        //holder.userNameView.setText();

        if (holder.progressBar != null) {
            // TODO: 03.11.16 holder should be not null
            holder.progressBar.setProgress(100);
        }
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyQueriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cv)
        CardView cv;
        @Bind(R.id.question_text)
        TextView questionTitle;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.profile_pic)
        ImageView profilePic;
        @Bind(R.id.textView_userName)
        TextView userNameView;
        @Bind(R.id.textView_likes)
        TextView likeView;
        @Bind(R.id.textView_shares)
        TextView shareView;
        @Bind(R.id.piclayout)
        LinearLayout picLayout;

        MyQueriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
