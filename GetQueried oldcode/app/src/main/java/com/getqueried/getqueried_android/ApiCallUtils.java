package com.getqueried.getqueried_android;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.profile.helper.InterfaceMediator;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.getqueried.getqueried_android.utils.Constants.User.DESCRIPTION;
import static com.getqueried.getqueried_android.utils.Constants.User.IMAGEPATH;
import static com.getqueried.getqueried_android.utils.Constants.User.NAME;
import static com.getqueried.getqueried_android.utils.Constants.User.SMALLIMAGEPATH;

/**
 * Created by Gaurav on 10/3/2016.
 */
public class ApiCallUtils extends NetworkActivity {

    private static final String TAG = "ApiCallUtils";

    public static void getUserMetadata(NetworkActivity networkActivity,
                                       String userId, SharedPrefUtils.UserProfile userProfile) {
        Crashlytics.log(Log.DEBUG, TAG, "getUserMetadata called !!! ");
        networkActivity.postStringUrlEncoded(Constants.URL.userMetadata, "[" + userId + "]", response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User Metadata Response : " + response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                String name = jsonObject.getString(NAME);
                String description = jsonObject.getString(DESCRIPTION);
                String imagePath = jsonObject.getString(IMAGEPATH);
                String imagePathSmall = jsonObject.getString(SMALLIMAGEPATH);
                userProfile.saveUserMetadata(
                        name, description, imagePath, imagePathSmall);
                SharedPrefUtils.saveUserProfile(networkActivity.getApplicationContext(), userProfile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }


    public static void followUser(NetworkActivity activity, View view, String userId, String name) {
        String followUserId = "[\"" + userId + "\"]";
        Crashlytics.log(Log.DEBUG, TAG, "followUserId : " + followUserId);
        activity.postStringUrlEncoded(Constants.URL.followUser, followUserId, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "Started following user id : " + followUserId);
            Snackbar.make(view, "Started following " + name, Snackbar.LENGTH_LONG).show();
            InterfaceMediator.notifyObservers(activity, followUserId);
        }, null);
    }

    private void unfollowUser(ArrayList<String> followingUserIdList) {
        if (followingUserIdList.size() > 0) {
            String unfollowUserId = "[\"" + followingUserIdList.get(0) + "\"]";
            postStringUrlEncoded(Constants.URL.unfollowUser, unfollowUserId, response -> {
                Crashlytics.log(Log.DEBUG, TAG, "Unfollowed user id : " + unfollowUserId);
            }, null);
        }
    }

    private ArrayList<String> followersUserIdList = new ArrayList<String>();

    //API call returns followers for this User
    private void getUserFollowers(NetworkActivity activity, String userId) {
        Crashlytics.log(Log.DEBUG, TAG, "userFollowers called !!! ");
        activity.postStringUrlEncoded(Constants.URL.userFollowers, userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Crashlytics.log(Log.DEBUG, TAG, "userFollowers response : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String followingUserId = jsonObject.getString(Constants.Follow.FOLLOWERS);
                    Crashlytics.log(Log.DEBUG, TAG, "followersUserId  : " + followingUserId);
                    String[] followersStringArray = followingUserId.split(",");
                    if (followersStringArray.length != 0) {
                        for (String aFollowStringArray : followersStringArray) {
                            Crashlytics.log(Log.DEBUG, TAG, "Followers user id : " + aFollowStringArray);
                            followersUserIdList.add(aFollowStringArray);
                        }
                    }
                    Crashlytics.log(Log.DEBUG, TAG, "Following user id list : " + followersUserIdList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }


    private ArrayList<String> followingUserIdList = new ArrayList<String>();

    //API call returns users followed by this user
    private void getUsersFollowing(NetworkActivity activity, String userId) {
        postStringUrlEncoded(Constants.URL.userFollowing, userId, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "userFollowing response : " + response);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String followingUserId = jsonObject.getString(Constants.Follow.FOLLOWING);
                Crashlytics.log(Log.DEBUG, TAG, "followingUserId  : " + followingUserId);
                String[] followingStringArray = followingUserId.split(",");
                for (String aFollowStringArray : followingStringArray) {
                    Crashlytics.log(Log.DEBUG, TAG, "Following user id : " + aFollowStringArray);
                    followingUserIdList.add(aFollowStringArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Crashlytics.log(Log.DEBUG, TAG, "Following user id list : " + followingUserIdList.toString());
        }, null);
    }

}
