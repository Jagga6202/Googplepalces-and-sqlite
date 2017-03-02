package com.getqueried.getqueried_android;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.facebook.Profile;
import com.getqueried.getqueried_android.registration.LoginActivity;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

public class SplashScreenActivity extends NetworkActivity {

    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        routeActivity();
    }

    private void routeActivity() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
            Profile profile = Profile.getCurrentProfile();
            if (profile != null || userProfile.access_token != null) {
                getUserDemography();
            } else
                GeneralUtils.routeWithFinish(this, WelcomeActivity.class);
        }, Constants.ONE_SECOND);
    }
}
