package com.getqueried.getqueried_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.create_query.fragments.CommunityFragment;
import com.getqueried.getqueried_android.create_query.fragments.FollowersFragment;

/**
 * Created by Gaurav on 10/7/2016.
 */
public class SendToFragmentAdapter extends FragmentPagerAdapter {

    String tabs[];

    public SendToFragmentAdapter(AppCompatActivity activity, FragmentManager fm) {
        super(fm);
        tabs = activity.getResources().getStringArray(R.array.send_to_tabs);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CommunityFragment.getInstance();//FollowersFragment.getInstance();
            case 1:
                return FollowersFragment.getInstance();//CommunityFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
