package com.getqueried.getqueried_android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static com.getqueried.getqueried_android.utils.Constants.TokenPayload.EXP;
import static com.getqueried.getqueried_android.utils.Constants.TokenPayload.IAT;
import static com.getqueried.getqueried_android.utils.Constants.TokenPayload.ISS;
import static com.getqueried.getqueried_android.utils.Constants.TokenPayload.KID;
import static com.getqueried.getqueried_android.utils.Constants.TokenPayload.UUID;
import static com.getqueried.getqueried_android.utils.Constants.User.ACCESS_TOKEN;
import static com.getqueried.getqueried_android.utils.Constants.User.BIRTH;
import static com.getqueried.getqueried_android.utils.Constants.User.COUNTRYOFRESIDENCE;
import static com.getqueried.getqueried_android.utils.Constants.User.DESCRIPTION;
import static com.getqueried.getqueried_android.utils.Constants.User.EDUCATIONLEVEL;
import static com.getqueried.getqueried_android.utils.Constants.User.EMAIL;
import static com.getqueried.getqueried_android.utils.Constants.User.FB_ID;
import static com.getqueried.getqueried_android.utils.Constants.User.FB_TOKEN;
import static com.getqueried.getqueried_android.utils.Constants.User.GENDER;
import static com.getqueried.getqueried_android.utils.Constants.User.IMAGEPATH;
import static com.getqueried.getqueried_android.utils.Constants.User.NAME;
import static com.getqueried.getqueried_android.utils.Constants.User.PASSWORD;
import static com.getqueried.getqueried_android.utils.Constants.User.REFRESH_TOKEN;
import static com.getqueried.getqueried_android.utils.Constants.User.SCOPE;
import static com.getqueried.getqueried_android.utils.Constants.User.SMALLIMAGEPATH;
import static com.getqueried.getqueried_android.utils.Constants.User.TOKEN_TYPE;
import static com.getqueried.getqueried_android.utils.Constants.User.UPDATED;
import static com.getqueried.getqueried_android.utils.Constants.User.USER_ID;
import static com.getqueried.getqueried_android.utils.Constants.User.USER_NAME;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.ANSWERS;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.NUMBERFOLLOWERS;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.NUMBERFOLLOWING;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.POINTSCURRENT;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.POINTSEVER;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.QUERIES;
import static com.getqueried.getqueried_android.utils.Constants.UserStats.TOKENS;

/**
 * Created by serverpc on 16/9/16.
 */
public class SharedPrefUtils {

    private static final String USER_PREFS = "user";
    private static final String QUERY_PREFS = "query";
    private static final String REGISTER_PREF = "register";

    public static void clearSharedPreferences(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public static SharedPreferences getRegisterPref(Context context) {
        return context.getSharedPreferences(REGISTER_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor putRegisterPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REGISTER_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    public static SharedPreferences getQueryPref(Context context) {
        return context.getSharedPreferences(QUERY_PREFS, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor putQueryPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(QUERY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }


    public static SharedPreferences getUserPref(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }


    public static class UserProfile implements Serializable {

        private static final String TAG = "UserProfile";

        public String fb_access_token;
        public String fb_id;

        public String access_token;
        public String refresh_token;
        public String token_type;
        public String scope;

        public long exp;
        public long iat;
        public String iss;
        public String kid;
        public String uuid;

        public String user_name;
        public String email;
        public String password;

        public String birth;
        public String gender;
        public String education_level;
        public String country_of_residence;
        public String updated;

        public String user_Id;
        public String number_followers;
        public String number_following;
        public String points_current;
        public String points_ever;
        public String tokens;
        public String answers;
        public String queries;

        public String name;
        public String description;
        public String imagePath;
        public String image_path;


        public ArrayList<String> users_not_following = new ArrayList<String>();

        public UserProfile() {
        }

        public void saveJwtTokenPayloadData(long exp, long iat, String iss, String kid, String uuid) {
            this.exp = exp;
            this.iat = iat;
            this.iss = iss;
            this.kid = kid;
            this.uuid = uuid;
        }

        public void saveUsersNotFollowing(String users_not_following) {
            this.users_not_following.add(users_not_following);
        }

        public void updateUserDescription(String description) {
            this.description = description;
        }

        public void updateUserName(String name) {
            this.name = name;
        }

        public void saveUserMetadata(String name, String description, String imagePath, String image_path) {
            this.name = name;
            this.description = description;
            this.imagePath = imagePath;
            this.image_path = image_path;
        }

        public void saveUserStats(String user_Id, String number_followers, String number_following, String points_current,
                                  String points_ever, String tokens, String answers, String queries) {
            this.user_Id = user_Id;
            this.number_followers = number_followers;
            this.number_following = number_following;
            this.points_current = points_current;
            this.points_ever = points_ever;
            this.tokens = tokens;
            this.answers = answers;
            this.queries = queries;
        }

        public void saveUserDemography(String birth, String gender, String education_level, String country_of_residence, String updated) {
            this.birth = birth;
            this.gender = gender;
            this.education_level = education_level;
            this.country_of_residence = country_of_residence;
            this.updated = updated;
        }

        public void saveFbSignUpDetails(String user_name, String email, String fb_id, String fb_access_token) {
            this.fb_id = fb_id;
            this.fb_access_token = fb_access_token;
            this.user_name = user_name;
            this.email = email;
        }

        public void saveSignUpDetails(String user_name, String email, String password) {
            this.user_name = user_name;
            this.password = password;
            this.email = email;
        }

        public void updateSignUpEmail(String email) {
            this.email = email;
        }

        public void saveLogInDetails(String email, String password) {
            this.password = password;
            this.email = email;
        }

        public void saveTokenDetails(String access_token, String token_type, String refresh_token, String scope) {
            this.access_token = access_token;
            this.refresh_token = refresh_token;
            this.token_type = token_type;
            this.scope = scope;
        }
    }

    public static void saveUserProfile(Context context, UserProfile profile) {
        SharedPreferences settings = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_NAME, profile.user_name);
        editor.putString(PASSWORD, profile.password);
        editor.putString(EMAIL, profile.email);
        editor.putString(ACCESS_TOKEN, profile.access_token);
        editor.putString(REFRESH_TOKEN, profile.refresh_token);
        editor.putString(TOKEN_TYPE, profile.token_type);
        editor.putString(SCOPE, profile.scope);
        editor.putString(FB_ID, profile.fb_id);
        editor.putString(FB_TOKEN, profile.fb_access_token);
        editor.putString(USER_ID, profile.user_Id);
        editor.putLong(EXP, profile.exp);
        editor.putLong(IAT, profile.iat);
        editor.putString(ISS, profile.iss);
        editor.putString(KID, profile.user_Id);
        editor.putString(UUID, profile.uuid);
        editor.putString(BIRTH, profile.birth);
        editor.putString(GENDER, profile.gender);
        editor.putString(EDUCATIONLEVEL, profile.education_level);
        editor.putString(COUNTRYOFRESIDENCE, profile.country_of_residence);
        editor.putString(UPDATED, profile.updated);
        editor.putString(NUMBERFOLLOWERS, profile.number_followers);
        editor.putString(NUMBERFOLLOWING, profile.number_following);
        editor.putString(POINTSCURRENT, profile.points_current);
        editor.putString(POINTSEVER, profile.points_ever);
        editor.putString(TOKENS, profile.tokens);
        editor.putString(ANSWERS, profile.answers);
        editor.putString(QUERIES, profile.queries);
        editor.putString(NAME, profile.name);
        editor.putString(DESCRIPTION, profile.description);
        editor.putString(IMAGEPATH, profile.imagePath);
        editor.putString(SMALLIMAGEPATH, profile.image_path);

        try {
            editor.putString(Constants.Follow.USERSNOTFOLLOWING, ObjectSerializer.serialize(profile.users_not_following));
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.apply();
    }

    public static UserProfile getUserProfile(Context context) {

        SharedPreferences settings = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        // setting is never null
        UserProfile profile = new UserProfile();

        profile.updateSignUpEmail(settings.getString(EMAIL, null));

        profile.updateUserDescription(settings.getString(DESCRIPTION, null));

        profile.updateUserName(settings.getString(NAME, null));

        profile.saveUserMetadata(settings.getString(NAME, null),
                settings.getString(DESCRIPTION, null),
                settings.getString(IMAGEPATH, null),
                settings.getString(SMALLIMAGEPATH, null)
        );

        profile.saveUserStats(settings.getString(USER_ID, null),
                settings.getString(NUMBERFOLLOWERS, null),
                settings.getString(NUMBERFOLLOWING, null),
                settings.getString(POINTSCURRENT, null),
                settings.getString(POINTSEVER, null),
                settings.getString(TOKENS, null),
                settings.getString(ANSWERS, null),
                settings.getString(QUERIES, null));

        profile.saveUserDemography(settings.getString(BIRTH, null),
                settings.getString(GENDER, null),
                settings.getString(EDUCATIONLEVEL, null),
                settings.getString(COUNTRYOFRESIDENCE, null),
                settings.getString(UPDATED, null));

        profile.saveFbSignUpDetails(settings.getString(USER_NAME, null),
                settings.getString(EMAIL, null),
                settings.getString(FB_ID, null),
                settings.getString(FB_TOKEN, null));

        profile.saveSignUpDetails(settings.getString(USER_NAME, null),
                settings.getString(EMAIL, null),
                settings.getString(PASSWORD, null));

        profile.saveTokenDetails(
                settings.getString(ACCESS_TOKEN, null),
                settings.getString(TOKEN_TYPE, null),
                settings.getString(REFRESH_TOKEN, null),
                settings.getString(SCOPE, null));

        profile.saveJwtTokenPayloadData(
                settings.getLong(EXP, 0),
                settings.getLong(IAT, 0),
                settings.getString(ISS, null),
                settings.getString(KID, null),
                settings.getString(UUID, null)
        );

        return profile;
    }

}
