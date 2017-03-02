package com.getqueried.getqueried_android.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterEmailActivity extends NetworkActivity {

    private static final String TAG = "RegisterEmailActivity";
    private static View view;

    @Bind(R.id.editText_email)
    EditText emailView;

    @Bind(R.id.editText_password)
    EditText passwordView;

    @Bind(R.id.editText_repeatPassword)
    EditText repeatpasswordView;

    @Bind(R.id.editText_name)
    EditText userNameView;

    @OnClick(R.id.btn_register)
    void saveUserCredentials() {
        Crashlytics.log(Log.DEBUG, TAG, "Input data : " + emailView.getText().toString() + " " + passwordView.getText().toString()
                + " " + userNameView.getText().toString());

        if (!passwordView.getText().toString().equals(repeatpasswordView.getText().toString()))
            Snackbar.make(view, "Password doesn't match.", Snackbar.LENGTH_LONG).show();
        else {

            SharedPrefUtils.putRegisterPref(this)
                    .putString(Constants.User.USER_NAME, userNameView.getText().toString())
                    .putString(Constants.User.EMAIL, emailView.getText().toString())
                    .putString(Constants.User.PASSWORD, passwordView.getText().toString())
                    .apply();

            Intent intent = new Intent(this, VerifyEmailActivity.class);
            intent.putExtra(Constants.User.VERIFIED_FB_EMAIL, false);
            startActivity(intent);
        }
    }

    @OnClick(R.id.textView_memberLogin)
    void memberLogin() {
        GeneralUtils.routeWithFinish(this, LoginEmailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        ButterKnife.bind(this);

        view = findViewById(R.id.main_parent);
    }
}
