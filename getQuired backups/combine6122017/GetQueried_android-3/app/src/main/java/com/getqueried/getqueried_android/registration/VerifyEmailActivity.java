package com.getqueried.getqueried_android.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyEmailActivity extends NetworkActivity {

    private static final String HEADER_TITLE = "VERIFY YOUR EMAIL";
    private static final String TAG = "VerifyEmailActivity";
    public static View view;
    private SharedPrefUtils.UserProfile userProfile;

    @Bind(R.id.editText_email)
    EditText emailView;

    @OnClick(R.id.btn_verify_email)
    void verifiedEmail() {
        if (!emailId.equals(emailView.getText().toString())) {
            SharedPrefUtils.putRegisterPref(this).putString(Constants.User.EMAIL, emailView.getText().toString())
                    .apply();
        }
        postRegisterUser();
    }

    private SharedPreferences registerPref;
    private String emailId;
    private boolean isFbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        ButterKnife.bind(this);
        view = findViewById(R.id.main_parent);
        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
        GeneralUtils.setToolbarBackButton(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFbLogin = bundle.getBoolean(Constants.User.VERIFIED_FB_EMAIL);
            Crashlytics.log(Log.DEBUG, TAG, "isFbLogin : " + isFbLogin);

            userProfile = SharedPrefUtils.getUserProfile(this);
            registerPref = SharedPrefUtils.getRegisterPref(this);
            emailId = registerPref.getString(Constants.User.EMAIL, null);
            emailView.setText(emailId);

            Crashlytics.log(Log.DEBUG, TAG, "Saved user name : " + registerPref.getString(Constants.User.NAME, null));
        }
    }

    public void postRegisterUser() {
        JSONObject registerPayload = new JSONObject();
        try {
            Crashlytics.log(Log.DEBUG, TAG, "User profile name : " + registerPref.getString(Constants.User.NAME, null));

            registerPayload.put(Constants.User.EMAIL, registerPref.getString(Constants.User.EMAIL, null));
            registerPayload.put(Constants.User.USER_NAME, registerPref.getString(Constants.User.NAME, null));
            registerPayload.put(Constants.User.EULA, true);

            if (!isFbLogin) {
                registerPayload.put(Constants.User.PASSWORD, registerPref.getString(Constants.User.PASSWORD, null));
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + registerPayload.toString());
                // POST registerPassword API
                postJson(Constants.URL.registerPassword, registerPayload, response -> saveUserRegistrationDetails(),
                        null);
            } else {
                registerPayload.put(Constants.User.FB_ACCESS_TOKEN, registerPref.getString(Constants.User.PASSWORD, null));
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + registerPayload.toString());
                // POST registerFacebook API
                postJson(Constants.URL.registerFb, registerPayload, response -> saveUserRegistrationDetails(), null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveUserRegistrationDetails() {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);

        userProfile.saveSignUpDetails(registerPref.getString(Constants.User.NAME, null),
                registerPref.getString(Constants.User.EMAIL, null),
                registerPref.getString(Constants.User.PASSWORD, null));
        SharedPrefUtils.saveUserProfile(this, userProfile);

        if (!isFbLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(Constants.User.VERIFIED_EMAIL, true);
            startActivity(intent);
        } else {
            postTokenFb();
        }
    }
}
