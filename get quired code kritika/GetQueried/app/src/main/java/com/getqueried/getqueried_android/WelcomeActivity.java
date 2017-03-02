package com.getqueried.getqueried_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.getqueried.getqueried_android.registration.LoginActivity;
import com.getqueried.getqueried_android.registration.LoginEmailActivity;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;

import static com.getqueried.getqueried_android.model.User.email;
import static com.getqueried.getqueried_android.model.User.fb_id;
import static com.getqueried.getqueried_android.model.User.fb_token;
import static com.getqueried.getqueried_android.model.User.first_name;
import static com.getqueried.getqueried_android.model.User.last_name;

public class WelcomeActivity extends NetworkActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnemaillogin, btnfb;
    private PrefManager prefManager;
    private static final String TAG = "WelcomeActivity";
    private static boolean isFacebookLogin = false;
    //private  userProfile;
    private View view;

    JSONObject payload = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
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
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnemaillogin = (Button) findViewById(R.id.btn_emaillogin);
        btnfb = (Button) findViewById(R.id.btn_facebooklogin);
        Paint p = new Paint();
        p.setColor(Color.BLUE);

        TextView t = (TextView) findViewById(R.id.termstext);
        t.setPaintFlags(p.getColor());
        t.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        t.setText("Terms");
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.getqueried.com/tos")));
            }
        });
        String fontPath = "fonts/open-sans.semibold.ttf";
        String regularfontPath="fonts/open-sans.regular.ttf";
        // text view label
        Button txtGhost = (Button) findViewById(R.id.btn_emaillogin);
        Button txtGhos = (Button) findViewById(R.id.btn_facebooklogin);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tff = Typeface.createFromAsset(getAssets(), regularfontPath);
        // Applying font
        txtGhost.setTypeface(tf);
        txtGhos.setTypeface(tff);
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnemaillogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });

        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this,
                        Arrays.asList("public_profile", "user_friends", "email"));
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
           //     btnNext.setText(getString(R.string.start));
       //         btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
//                btnNext.setText(getString(R.string.next));
  //              btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
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
            postTokenPassword(Constants.LOGIN, WelcomeActivity.this, userProfile.email, userProfile.password);
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
            GeneralUtils.hideSoftKeyboard(WelcomeActivity.this, view);
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
                postTokenPassword(Constants.LOGIN, WelcomeActivity.this, userProfile.email, userProfile.password);
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

                                SharedPrefUtils.putRegisterPref(WelcomeActivity.this)
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
            GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                    loginResult.getAccessToken(),
                    //AccessToken.getCurrentAccessToken(),
                    "/me/invitable_friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Intent intent = new Intent(WelcomeActivity.this,FriendsAddActivity.class);
                            try {
                                JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                intent.putExtra("jsondata", rawName.toString());
                                Log.i("SOME DATA",rawName.toString());
                                SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                data.edit().putString("SharedPreferences", rawName.toString()).commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).executeAsync();

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
    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}