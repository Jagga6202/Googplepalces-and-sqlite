package com.getqueried.getqueried_android.registration;

import android.os.Bundle;
import android.widget.EditText;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginEmailActivity extends NetworkActivity {

    private static final String TAG = "LoginEmailActivity";
    @Bind(R.id.editText_email)
    EditText emailView;

    @Bind(R.id.editText_password)
    EditText passwordView;

    @OnClick(R.id.textView_register)
    void register() {
        GeneralUtils.routeWithFinish(this, RegisterEmailActivity.class);
    }

    @OnClick(R.id.btn_login)
    void emailLogin() {
        postTokenPassword(Constants.LOGIN, this, emailView.getText().toString(), passwordView.getText().toString());
    }

    @OnClick(R.id.textView_forgot_password)
    void forgotPassword() {
        GeneralUtils.route(this, ForgotPasswordActivity.class);
    }

    SharedPrefUtils.UserProfile userProfile;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ButterKnife.bind(this);

        userProfile = SharedPrefUtils.getUserProfile(this);
    }
}
