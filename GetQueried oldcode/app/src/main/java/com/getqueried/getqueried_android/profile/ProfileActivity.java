package com.getqueried.getqueried_android.profile;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.ProfilePagerAdapter;
import com.getqueried.getqueried_android.create_query.fragments.SelectImageDialog;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.profile.helper.AddFollowerInterface;
import com.getqueried.getqueried_android.profile.helper.InterfaceMediator;
import com.getqueried.getqueried_android.registration.LoginActivity;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ImageUtils;
import com.getqueried.getqueried_android.utils.MPermissionChecker;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.getqueried.getqueried_android.utils.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends NetworkActivity implements
        DrawerLayout.DrawerListener,
        NavigationView.OnNavigationItemSelectedListener,
        AddFollowerInterface,
        SelectImageDialog.SelectedItemListener {

    private static final String TAG = "ProfileActivity";
    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1111;
    private static final int SELECT_IMAGE_FROM_GALLERY = 1112;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private String userId;
    private View activityView;
    private String selectedPhotoPath;

    public DrawerLayout mDrawerLayout;

    @OnClick(R.id.imageView_dashboard)
    void openDashBoard() {
        GeneralUtils.routeAndClearBackStack(ProfileActivity.this, DashboardActivity.class);
    }


    @Bind(R.id.view_pager_profile)
    ViewPager viewPager;

    @Bind(R.id.tabs_profile)
    SlidingTabLayout slidingTabLayout;

    @Bind(R.id.editText_user_name)
    EditText editUserNameView;

    @Bind(R.id.textView_user_name)
    TextView userNameView;

    @Bind(R.id.textView_user_description)
    TextView userDescriptionView;

    @Bind(R.id.editText_user_description)
    EditText editUserDescriptionView;

    @Bind(R.id.profile_image)
    CircleImageView profileImageView;

    @Bind(R.id.layout_edit_profile_details)
    LinearLayout editDetailsLayout;

    @Bind(R.id.layout_display_profile_details)
    LinearLayout displayDetialsLayout;

    Profile userFbProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        displayDetialsLayout.setVisibility(View.VISIBLE);
        editDetailsLayout.setVisibility(View.GONE);

        InterfaceMediator.addObserver(this);

        activityView = findViewById(R.id.drawer_layout);

        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        userId = "\"" + userProfile.uuid + "\"";

        // Set profile image
        String profileImagePath = userProfile.image_path;
        String imageUrl = Constants.URL.getProfileImage + profileImagePath;
        Crashlytics.log(Log.DEBUG, TAG, "Image path : " + profileImagePath + " Image URL : " + imageUrl);
        userFbProfile = Profile.getCurrentProfile();

        if (profileImagePath.length() > 0)
            Picasso.with(this).load(imageUrl).into(profileImageView);
        else if (userFbProfile != null) {
            Log.d(TAG, "FB profile is not null");
            Uri profileImageUri = userFbProfile.getProfilePictureUri(128, 128);
            Picasso.with(this).load(profileImageUri).into(profileImageView);
        } else {
            Log.d(TAG, "FB profile/imagePath is null display profile pic placeholder");
            profileImageView.setImageResource(R.drawable.profile_placeholder_icon);
        }


        // API to get user stats
        getUserStats();

        userNameView.setOnClickListener(view -> {
            displayDetialsLayout.setVisibility(View.GONE);
            editDetailsLayout.setVisibility(View.VISIBLE);
        });

        userDescriptionView.setOnClickListener(view -> {
            displayDetialsLayout.setVisibility(View.GONE);
            editDetailsLayout.setVisibility(View.VISIBLE);
        });
        /*editUserNameView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Crashlytics.log(Log.DEBUG, TAG, "editUserNameView action done!!! ");
                editDetailsLayout.setVisibility(View.VISIBLE);
                displayDetialsLayout.setVisibility(View.GONE);
                return true;
            }
            return false;
        });

        editUserDescriptionView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editDetailsLayout.setVisibility(View.VISIBLE);
                displayDetialsLayout.setVisibility(View.GONE);
                return true;
            }
            return false;
        });*/

        // Set the Sliding Tab Fragment
        viewPager.setAdapter(new ProfilePagerAdapter(this, getSupportFragmentManager()));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(position -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return getColor(R.color.white);
            else
                return getResources().getColor(R.color.white);
        });
        slidingTabLayout.setViewPager(viewPager);

        userNameView.setText(userProfile.user_name);
        if (userProfile.description.length() > 0)
            userDescriptionView.setText(userProfile.description);

    }

    @OnClick(R.id.btn_save_edit_changes)
    void saveDetailsChanged() {
        GeneralUtils.hideSoftKeyboard(this, displayDetialsLayout);
        String name = editUserNameView.getText().toString();
        String description = editUserDescriptionView.getText().toString();
        updateUserName(name, description);
    }

    @OnClick(R.id.btn_cancel_edit_changes)
    void cancelChanges() {
        editDetailsLayout.setVisibility(View.GONE);
        displayDetialsLayout.setVisibility(View.VISIBLE);
        GeneralUtils.hideSoftKeyboard(this, displayDetialsLayout);
    }

    /*@OnClick(R.id.textView_user_name)
    void changeUserName() {
        userNameView.setVisibility(View.GONE);
        editUserNameView.setVisibility(View.VISIBLE);
        editUserNameView.setFocusable(true);
        //editUserNameView.setOnFocusChangeListener(this);
    }

    @OnClick(R.id.textView_user_description)
    void changeUserDescription() {
        userDescriptionView.setVisibility(View.GONE);
        editUserDescriptionView.setVisibility(View.VISIBLE);
        editUserDescriptionView.setFocusable(true);
        //editUserDescriptionView.setOnFocusChangeListener(this);
    }*/


    @OnClick(R.id.profile_image)
    void uploadProfileImage() {
        FragmentManager fm = getFragmentManager();
        SelectImageDialog dialogFragment = new SelectImageDialog(this);
        dialogFragment.show(fm, "SelectImageDialogFragment");
    }

    //  SelectImageDialog.SelectedItemListener interface callback method
    @Override
    public void manageItemSelected(boolean openCamera) {
        if (openCamera) {
            if (MPermissionChecker.grantCameraAccess(this, REQUEST_CAMERA))
                selectedPhotoPath = ImageUtils.intentCamera(this, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
        } else {
            if (MPermissionChecker.grantGalleryAccess(this, REQUEST_GALLERY))
                ImageUtils.intentGallery(this, SELECT_IMAGE_FROM_GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            String imagePath;
            Uri imageUri = null;
            //Check that request code matches ours:
            if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
                imagePath = selectedPhotoPath;
                GeneralUtils.fixImageOrientation(imagePath);

                new UploadImageAsyncTask(this, imagePath).execute();

            } else if (requestCode == SELECT_IMAGE_FROM_GALLERY) {

                try {
                    imageUri = intent.getData();
                    imagePath = GeneralUtils.getRealPathFromURI(this, imageUri);
                    Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);

                } catch (Exception e) {
                    Crashlytics.log(Log.ERROR, TAG, "Caught Exception message : " + e.getMessage());
                    imagePath = GeneralUtils.getRealPathFromURI_API19(this, imageUri);
                    Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);
                }
                GeneralUtils.fixImageOrientation(imagePath);

                new UploadImageAsyncTask(this, imagePath).execute();
            }
        }
    }

    private void updateUserName(String name, String description) {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        String userName = "\"" + name + "\"";
        Log.d(TAG, "Converted user name : " + userName);
        // API call to update user name
        postStringUrlEncoded(Constants.URL.updateUserName, userName, response -> {
            userProfile.updateUserName(name);
            SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);
            updateUserDescription(description);
        }, null);
    }


    private void updateUserDescription(String description) {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        String userDescription = "\"" + description + "\"";
        // API call to update user description
        postStringUrlEncoded(Constants.URL.updateUserDescription, userDescription, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User description updated successfully !!!");
            userProfile.updateUserDescription(description);
            SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);
            editDetailsLayout.setVisibility(View.GONE);
            displayDetialsLayout.setVisibility(View.VISIBLE);
            userNameView.setText(userProfile.name);
            userDescriptionView.setText(userProfile.description);
        }, null);
    }

    /*private void updateUserDescription(String description) {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        String userDescription = "\"" + description + "\"";
        // API call to update user description
        postStringUrlEncoded(Constants.URL.updateUserDescription, userDescription, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User description updated successfully !!!");
            userProfile.updateUserDescription(description);
            SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);
            editUserDescriptionView.setVisibility(View.GONE);
            userDescriptionView.setVisibility(View.VISIBLE);
            userDescriptionView.setText(userProfile.description);
        }, null);
    }

    private void updateUserName(String name) {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        String userName = "\"" + name + "\"";
        Crashlytics.log(Log.DEBUG, TAG, "Converted user name : " + userName);
        // API call to update user name
        postStringUrlEncoded(Constants.URL.updateUserName, userName, response -> {
            userProfile.updateUserName(name);
            SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);
            editUserNameView.setVisibility(View.GONE);
            userNameView.setVisibility(View.VISIBLE);
            userNameView.setText(userProfile.name);
        }, null);
    }*/

    // Slide menu
    @OnClick(R.id.imageView_settings)
    void openSettings() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null) {
            mDrawerLayout.addDrawerListener(this);
        }

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    // Interface callback method for navigation drawer
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            // FB logout
            if (Profile.getCurrentProfile() != null) {
                LoginManager.getInstance().logOut();
                Intent logout = new Intent(ProfileActivity.this, LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();
            } else {
                SharedPrefUtils.getUserPref(this).edit().clear().apply();
                Crashlytics.log(Log.DEBUG, TAG, "Access Token : " + SharedPrefUtils.getUserProfile(this).access_token);
                Intent logout = new Intent(ProfileActivity.this, LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserStats() {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        Crashlytics.log(Log.DEBUG, TAG, "getUserStats called !!! ");
        postStringUrlEncoded(Constants.URL.userStats, userId, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User Stats Response : " + response);
            JSONObject responseObject;
            try {
                responseObject = new JSONObject(response);
                userProfile.saveUserStats(responseObject.getString(Constants.User.USER_ID),
                        responseObject.getString(Constants.UserStats.NUMBERFOLLOWERS),
                        responseObject.getString(Constants.UserStats.NUMBERFOLLOWING),
                        responseObject.getString(Constants.UserStats.POINTSCURRENT),
                        responseObject.getString(Constants.UserStats.POINTSEVER),
                        responseObject.getString(Constants.UserStats.TOKENS),
                        responseObject.getString(Constants.UserStats.ANSWERS),
                        responseObject.getString(Constants.UserStats.QUERIES)
                );
                SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);
                updateUserStartUI();
            } catch (JSONException e) {
                Crashlytics.log(Log.ERROR, TAG, "userStats Exception message : " + e.getMessage());
            }
        }, null);
    }

    @Bind(R.id.count_followers)
    TextView followerCountView;

    @Bind(R.id.count_following)
    TextView followingCountView;

    @Bind(R.id.count_prize_token)
    TextView prizeTokenView;

    private void updateUserStartUI() {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getApplicationContext());
        followingCountView.setText(userProfile.number_following);
        followerCountView.setText(userProfile.number_followers);
        prizeTokenView.setText(userProfile.tokens);
    }

    // Interface callback method
    @Override
    public void onFollowerAdded(Object sender, Object followerId) {
        Crashlytics.log(Log.DEBUG, TAG, "onFollowerAdded called. Getting user stats");
        getUserStats();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InterfaceMediator.removeObserver(this);
    }

    // Image upload for profile
    private class UploadImageAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private static final String TAG = "UploadImageAsyncTask";
        final Context context;
        final String profileImagePath;

        public UploadImageAsyncTask(Context context, String profileImagePath) {
            this.context = context;
            this.profileImagePath = profileImagePath;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Crashlytics.log(Log.DEBUG, TAG, "Get image mime type : " + ImageUtils.getMimeType(profileImagePath));
            try {
                return ImageUtils.uploadProfileImage(ProfileActivity.this, Constants.URL.updateProfileImage,
                        BitmapFactory.decodeFile(profileImagePath), profileImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (!isSuccess)
                Snackbar.make(activityView, "Image upload failed", Snackbar.LENGTH_LONG);
            else {
                Snackbar.make(activityView, "Image uploaded successfully", Snackbar.LENGTH_LONG);
                profileImageView.setImageDrawable(Drawable.createFromPath(profileImagePath));
            }
        }
    }
}

