package com.getqueried.getqueried_android.create_query;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.SendToFragmentAdapter;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.getqueried.getqueried_android.utils.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuerySendToActivity extends NetworkActivity {

    private static final String HEADER_TITLE = "Send to";
    private static final String TAG = "QuerySendToActivity";

    @Bind(R.id.view_pager_send_query)
    ViewPager viewPager;

    @Bind(R.id.tabs_send_query)
    SlidingTabLayout slidingTabLayout;

    private SharedPrefUtils.UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_query_answerers_type);
        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
        GeneralUtils.setToolbarBackButton(this, AskQuestionActivity.class);

        ButterKnife.bind(this);
        userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());

        // Set the dashboard with Sliding Tab Fragment
        viewPager.setAdapter(new SendToFragmentAdapter(this, getSupportFragmentManager()));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(position -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return getColor(R.color.white);
            else
                return getResources().getColor(R.color.white);
        });

        slidingTabLayout.setViewPager(viewPager);
    }
}
