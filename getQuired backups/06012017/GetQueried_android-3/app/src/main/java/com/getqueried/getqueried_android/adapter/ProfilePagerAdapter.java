package com.getqueried.getqueried_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.profile.fragments.FindPeopleFragment;
import com.getqueried.getqueried_android.profile.fragments.YourFeedFragment;

/**
 * Created by Gaurav on 9/28/2016.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    String[] profileTabs;

    public ProfilePagerAdapter(AppCompatActivity activity, FragmentManager fm) {
        super(fm);
        profileTabs = activity.getResources().getStringArray(R.array.profile_tabs);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return profileTabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return YourFeedFragment.getInstance();

            case 1:
                return FindPeopleFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
