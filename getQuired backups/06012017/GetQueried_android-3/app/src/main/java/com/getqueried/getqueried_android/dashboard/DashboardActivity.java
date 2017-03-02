package com.getqueried.getqueried_android.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.ApiCallUtils;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.DashBoardFragmentAdapter;
import com.getqueried.getqueried_android.create_query.AskQuestionActivity;
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
    Dialog settingsDialog;
    @Bind(R.id.view_pager_dashboard)
    ViewPager viewPager;

  /*  @Bind(R.id.tabs_dashboard)
    SlidingTabLayout slidingTabLayout;*/

    @Bind(R.id.tabs_dashboard)
    TabLayout slidingTabLayout;

    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Bind(R.id.profile_pic)
    ImageView profileImageView;

    private SharedPrefUtils.UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //=====================================================================popup code===========
        Bundle bundle= getIntent().getExtras();
        if (bundle!= null) {// to avoid the NullPointerException
            Boolean s = getIntent().getExtras().getBoolean("popshow");
            if (s) {
                settingsDialog = new Dialog(this);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.activity_tut, null));
                settingsDialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(settingsDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;;
                settingsDialog.getWindow().setAttributes(lp);
                ImageView fashionImg = (ImageView)settingsDialog.findViewById(R.id.myImage);
                // set a onclick listener for when the button gets clicked
                fashionImg.setOnClickListener(new View.OnClickListener() {
                    // Start new list activity
                    public void onClick(View v) {
                        // Toast.makeText(getApplicationContext(),"Clicked Image!!!",Toast.LENGTH_SHORT).show();
                        settingsDialog.dismiss();
                    }
                });
                FloatingActionButton fbtn = (FloatingActionButton)settingsDialog.findViewById(R.id.fab_nquery);
                // set a onclick listener for when the button gets clicked
                fbtn.setOnClickListener(new View.OnClickListener() {
                    // Start new list activity
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(),"Clicked Floating Button!!!",Toast.LENGTH_SHORT).show();
                        // GeneralUtils.route((AppCompatActivity) getApplicationContext(), AskQuestionActivity.class);
                        Intent i=new Intent(DashboardActivity.this,AskQuestionActivity.class);
                        startActivity(i);
                    }
                });
            }
        }
        //Kritika
        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
        ButterKnife.bind(this);

        userProfile = SharedPrefUtils.getUserProfile(this);

        // Parse JWT token to get User UUID
        if (userProfile.access_token != null && userProfile.uuid == null)
            GeneralUtils.parseJwtToken(getApplicationContext(), userProfile.access_token, userProfile);

        // Set the dashboard with Sliding Tab Fragment
        viewPager.setAdapter(new DashBoardFragmentAdapter(this, getSupportFragmentManager()));
      /*  slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(position -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return getColor(R.color.white);
            else
                return getResources().getColor(R.color.white);
        });*/
        slidingTabLayout.setupWithViewPager(viewPager);
      /*  slidingTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int tabIconColor = ContextCompat.getColor(DashboardActivity.this, R.color.tab_selector);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int tabIconColor = ContextCompat.getColor(DashboardActivity.this, R.color.tab_selector);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }


        });*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("view_pager_position");
            viewPager.setCurrentItem(position);
        }

        //slidingTabLayout.setViewPager(viewPager);

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
