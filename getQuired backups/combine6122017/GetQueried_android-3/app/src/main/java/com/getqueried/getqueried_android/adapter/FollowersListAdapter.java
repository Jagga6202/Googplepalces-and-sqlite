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

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.create_query.helper.FollowersInterface;
import com.getqueried.getqueried_android.model.Metadata;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.FollowersListViewHolder> {

    private static final java.lang.String TAG = "FollowersListAdapter";
    private LayoutInflater inflater;
    private Context context;
    private List<Metadata> followers;
    private ArrayList<String> selectedFollowersIdList = new ArrayList<>();
    private boolean[] isSelected;

    public FollowersListAdapter(Context context, List<Metadata> data) {
        Crashlytics.log(Log.DEBUG, TAG, "Passed followers to adapter : " + data.size());
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.followers = data;
        isSelected = new boolean[data.size()];
        Arrays.fill(isSelected, false);
    }

    @Override
    public FollowersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_following_people, parent, false);
        return new FollowersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowersListViewHolder holder, int position) {
        holder.textViewUserName.setText(followers.get(position).name);
        holder.bindState();
        if (followers.get(position).imagePath.length() == 0) {
            holder.circleImageView.setImageResource(R.drawable.edit_profile_pic);
        } else {
            Picasso.with(context).load(followers.get(position).imagePath).into(holder.circleImageView);
        }
    }


    @Override
    public int getItemCount() {
        return followers.size();
    }

    public ArrayList<String> getSelectedFollowers() {
        return selectedFollowersIdList;
    }

    public class FollowersListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.find_people_layout)
        LinearLayout linearLayout;
        @Bind(R.id.profile_pic)
        CircleImageView circleImageView;
        @Bind(R.id.textView_user_name)
        TextView textViewUserName;

        @Bind(R.id.imageButton_add_user)
        ImageButton imageButtonCheckMark;

        @OnClick(R.id.imageButton_add_user)
        public void changeUserState(View view) {
            int pos = getAdapterPosition();
            isSelected[pos] = !isSelected[pos];
            if (isSelected[pos]) {
                selectedFollowersIdList.add(followers.get(pos).userID);
            } else {
                selectedFollowersIdList.remove(followers.get(pos).userID);
            }
            FollowersListAdapter.this.notifyItemChanged(pos);
        }

        public void bindState() {
            if (isSelected[getAdapterPosition()]) {
                imageButtonCheckMark.setImageResource(R.drawable.checkmark_green);
            } else {
                imageButtonCheckMark.setImageResource(R.drawable.checkmark_grey);
            }
        }

        public FollowersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
