package com.getqueried.getqueried_android.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.getqueried.getqueried_android.model.User.email;
import static com.getqueried.getqueried_android.model.User.fb_id;
import static com.getqueried.getqueried_android.model.User.fb_token;
import static com.getqueried.getqueried_android.model.User.first_name;
import static com.getqueried.getqueried_android.model.User.last_name;

public class LoginActivity extends NetworkActivity {

    private static final String TAG = "LoginActivity";
    private static boolean isFacebookLogin = false;
    //private  userProfile;
    private View view;

    JSONObject payload = null;

    @OnClick(R.id.btn_facebook_login)
    void fbLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends", "email"));
    }

    @OnClick(R.id.btn_email_login)
    void emailLogin() {
        GeneralUtils.route(this, LoginEmailActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        view = findViewById(R.id.main_parent);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, callback);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean isEmailVerified = bundle.getBoolean(Constants.User.VERIFIED_EMAIL);
            if (isEmailVerified) {
                if (!isActivationDialogVisible)
                    displayActivationLinkDialog();
            }
        }
    }

    private boolean isActivationDialogVisible = false;
    private AlertDialog activateLinkDialog;

    public void displayActivationLinkDialog() {
        isActivationDialogVisible = true;
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog_activate_with_link, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setView(dialogView);

        final TextView resendMailView = (TextView) dialogView.findViewById(R.id.textView_resend_mail);
        resendMailView.setOnClickListener(v -> {
            registerUser();
            Snackbar.make(view, "We have resend your mail", Snackbar.LENGTH_LONG).show();
        });

        final TextView activateWithCodeView = (TextView) dialogView.findViewById(R.id.textView_activate_with_code);
        activateWithCodeView.setOnClickListener(v -> {
            activateLinkDialog.dismiss();
            isActivationDialogVisible = false;
            displayActivationCodeDialog();
        });

        final Button btnGetToken = (Button) dialogView.findViewById(R.id.btn_token);
        btnGetToken.setOnClickListener(v -> {
            SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
            postTokenPassword(Constants.LOGIN, LoginActivity.this, userProfile.email, userProfile.password);
        });

        // create alert dialog and show it
        activateLinkDialog = alertDialogBuilder.create();
        activateLinkDialog.show();
    }


    private AlertDialog activateCodeDialog;

    public void displayActivationCodeDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog_activate_with_code, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.editText_code);

        final TextView resendMailView = (TextView) dialogView.findViewById(R.id.textView_resend_mail);
        resendMailView.setOnClickListener(v -> {
            registerUser();
            Snackbar.make(view, "We have resend your mail", Snackbar.LENGTH_LONG).show();
        });

        final TextView activateWithLinkView = (TextView) dialogView.findViewById(R.id.textView_activate_with_link);
        activateWithLinkView.setOnClickListener(v -> {
            activateCodeDialog.dismiss();
            displayActivationLinkDialog();
        });

        final Button submitCodeBtn = (Button) dialogView.findViewById(R.id.btn_verify);
        submitCodeBtn.setOnClickListener(v -> {
            SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getBaseContext());
            GeneralUtils.hideSoftKeyboard(LoginActivity.this, view);
            JSONObject activationPayload = new JSONObject();
            try {
                activationPayload.put(Constants.User.EMAIL, userProfile.email);
                activationPayload.put(Constants.User.PASSWORD, userProfile.password);
                activationPayload.put(Constants.User.USER_TYPE, "user");
                activationPayload.put(Constants.User.VERIFICATION_CODE, userInput.getText().toString());
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + activationPayload.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // POST activation API
            postJson(Constants.URL.activation, activationPayload, response -> {
                Crashlytics.log(Log.DEBUG, TAG, "Response : " + response);
                postTokenPassword(Constants.LOGIN, LoginActivity.this, userProfile.email, userProfile.password);
            }, failureHandler);
        });

        // create alert dialog and show it
        activateCodeDialog = alertDialogBuilder.create();
        activateCodeDialog.show();

        Handler mHandler= new Handler();
        mHandler.postDelayed(
                () -> {
                    InputMethodManager inputMethodManager =  (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(userInput.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    userInput.requestFocus();
                }, 500);
    }

    //FB registration + login

    private CallbackManager callbackManager;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Crashlytics.log(Log.DEBUG, TAG, "Access Token : " + accessToken + "Login QueryJsonBody : " + loginResult.toString());

            isFacebookLogin = true;

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    (jsonObject, response) -> {
                        Crashlytics.log(Log.DEBUG, TAG, jsonObject.toString());
                        try {
                            String[] name = jsonObject.getString(Constants.User.USER_NAME).split(" ");
                            if (name.length != 0) {
                                first_name = name[0];
                                last_name = name[1];
                                fb_id = accessToken.getUserId();
                                fb_token = accessToken.getToken();
                                email = jsonObject.getString(Constants.User.EMAIL);

                                SharedPrefUtils.putRegisterPref(LoginActivity.this)
                                        .putString(Constants.User.USER_NAME, first_name + " " + last_name)
                                        .putString(Constants.User.EMAIL, email)
                                        .putString(Constants.User.PASSWORD, fb_token)
                                        .apply();

                                postTokenFb();
                            } else
                                Crashlytics.log(Log.DEBUG, TAG, "Name field is empty");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,age_range");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Crashlytics.log(Log.DEBUG, TAG, " FB login cancelled");
        }

        @Override
        public void onError(FacebookException error) {
            Crashlytics.log(Log.ERROR, TAG, " FB login exception : " + error.getMessage());
        }
    };

    public Response.ErrorListener failureHandler = error -> {
        Crashlytics.log(Log.ERROR, TAG, "Error : " + error);
        if (error != null) {
            Crashlytics.log(Log.ERROR, TAG, "Error message : " + error.getMessage());
            try {
                JSONObject errorJson = new JSONObject(error.getMessage());
                String errorDescription = errorJson.getString(Constants.Error.ERROR_DESCRIPTION);
                if (errorDescription.equals("An account with that e-mail already exists.")) {
                    Snackbar.make(view, "User already exists.", Snackbar.LENGTH_LONG).show();
                    displayActivationCodeDialog();
                } else if (errorDescription.equals("The authorization server encountered an unexpected condition " +
                        "that prevented it from fulfilling the request.")) {
                    Toast.makeText(this, "Something went wrong Please try again.", Toast.LENGTH_LONG).show();
                } else
                    Snackbar.make(view, "Server response error. Please check logs.", Snackbar.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activateCodeDialog != null)
            activateCodeDialog.dismiss();

        if (activateLinkDialog != null)
            activateLinkDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
