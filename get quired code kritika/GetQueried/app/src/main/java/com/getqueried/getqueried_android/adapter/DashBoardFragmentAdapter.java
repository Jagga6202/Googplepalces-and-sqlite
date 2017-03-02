package com.getqueried.getqueried_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.dashboard.fragments.FragmentType;
import com.getqueried.getqueried_android.dashboard.fragments.DashboardFragment;

public class DashBoardFragmentAdapter extends FragmentPagerAdapter {

    String[] tabs;
    Context context;

    public DashBoardFragmentAdapter(AppCompatActivity activity, FragmentManager fm) {
        super(fm);
        context = activity;
        tabs = activity.getResources().getStringArray(R.array.tabs);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


    // TODO: 07.11.16 should be just one fragment
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DashboardFragment.getInstance(context, FragmentType.NEW_QUERIES);

            case 1:
                return DashboardFragment.getInstance(context, FragmentType.MY_ANSWERS);

            case 2:
                return DashboardFragment.getInstance(context, FragmentType.MY_QUERIES);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}