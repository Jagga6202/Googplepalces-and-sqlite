package com.getqueried.getqueried_android.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.ApiCallUtils;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.DashBoardFragmentAdapter;
import com.getqueried.getqueried_android.model.FeedItem;
import com.getqueried.getqueried_android.profile.ProfileActivity;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.getqueried.getqueried_android.utils.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.getqueried.getqueried_android.utils.Constants.User.USER_ID;

public class DashboardActivity extends NetworkActivity {

    private static final String HEADER_TITLE = "Queries";
    private static final String TAG = "DashboardActivity";

    private String userId;

    @Bind(R.id.view_pager_dashboard)
    ViewPager viewPager;

    @Bind(R.id.tabs_dashboard)
    SlidingTabLayout slidingTabLayout;

    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Bind(R.id.profile_pic)
    ImageView profileImageView;

    private SharedPrefUtils.UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
        ButterKnife.bind(this);

        userProfile = SharedPrefUtils.getUserProfile(this);

        // Parse JWT token to get User UUID
        if (userProfile.access_token != null && userProfile.uuid == null)
            GeneralUtils.parseJwtToken(getApplicationContext(), userProfile.access_token, userProfile);

        // Set the dashboard with Sliding Tab Fragment
        viewPager.setAdapter(new DashBoardFragmentAdapter(this, getSupportFragmentManager()));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(position -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return getColor(R.color.white);
            else
                return getResources().getColor(R.color.white);
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("view_pager_position");
            viewPager.setCurrentItem(position);
        }

        slidingTabLayout.setViewPager(viewPager);

        userId = "\"" + userProfile.uuid + "\"";

        // API to pull user metadata
        ApiCallUtils.getUserMetadata(this, userId, userProfile);
    }

    @OnClick(R.id.profile_pic)
    public void startProfile() {
        GeneralUtils.route(this, ProfileActivity.class);
    }


    private void getPendingQueries() {
        getString(Constants.URL.getQueryIdList,
                response -> Crashlytics.log(Log.DEBUG, TAG, "Pull Pending Query Response : " + response),
                objectFailureHandler);
    }

    private Response.ErrorListener arrayFailureHandler = error -> {
        Crashlytics.log(Log.ERROR, TAG, "Error in arrayFailureHandler : " + error);
        if (error != null) {
            try {
                JSONArray errorJson = new JSONArray(error.getMessage());
                Crashlytics.log(Log.ERROR, TAG, "Error : " + errorJson.getString(0) + errorJson.getString(1));
                String errorDescription = errorJson.getString(1);
                if (errorDescription.equals("Access token invalid or malformed")) {
                    Crashlytics.log(Log.DEBUG, TAG, "Token has expired fetching new token.");
                    postTokenPassword(Constants.REFRESH, DashboardActivity.this, userProfile.email, userProfile.password);
                }
            } catch (JSONException e) {
                Crashlytics.log(Log.ERROR, TAG, "Failure Handler Exception message : " + e.getMessage());
            }
        }
    };

    private Response.ErrorListener objectFailureHandler = error -> {
        Crashlytics.log(Log.ERROR, TAG, "Error in objectFailureHandler : " + error);
        if (error != null) {
            try {
                JSONObject errorObject = new JSONObject(error.getMessage());
                Crashlytics.log(Log.ERROR, TAG, "Error Object : " + errorObject);
                if (errorObject.getString("error_description").equals("Access token invalid or malformed")) {
                    postTokenPassword(Constants.REFRESH, this, userProfile.email, userProfile.password);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
