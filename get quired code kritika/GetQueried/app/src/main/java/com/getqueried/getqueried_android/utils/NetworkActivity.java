package com.getqueried.getqueried_android.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.registration.CompleteProfileActivity;
import com.getqueried.getqueried_android.registration.LoginActivity;
import com.getqueried.getqueried_android.registration.VerifyEmailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

import static com.getqueried.getqueried_android.utils.Constants.User.BIRTH;
import static com.getqueried.getqueried_android.utils.Constants.User.COUNTRYOFRESIDENCE;
import static com.getqueried.getqueried_android.utils.Constants.User.EDUCATIONLEVEL;
import static com.getqueried.getqueried_android.utils.Constants.User.GENDER;
import static com.getqueried.getqueried_android.utils.Constants.User.PASSWORD;
import static com.getqueried.getqueried_android.utils.Constants.User.UPDATED;

public class NetworkActivity extends AppCompatActivity {

    private static final String TAG = "NetworkActivity";
    protected static final Object VOLLEY_REQUEST_TAG = "TAG";
    protected RequestQueue mRequestQueue = null;
    private Context context;
    private static boolean isFbLogin = false;

    private SharedPrefUtils.UserProfile userProfile;
    private SharedPreferences registerPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Crashlytics.log(Log.DEBUG, TAG, "NetworkAct onCreate called");
        registerPref = SharedPrefUtils.getRegisterPref(this);
        mRequestQueue = Volley.newRequestQueue(getBaseContext());
    }

    public boolean handleVolleyError(VolleyError error) {

        if (error instanceof TimeoutError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley TimeoutError : " + error.getMessage());
            mRequestQueue.cancelAll(VOLLEY_REQUEST_TAG);
            return true;
        } else if (error instanceof NoConnectionError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley NoConnectionError : " + error.getMessage());
            mRequestQueue.cancelAll(VOLLEY_REQUEST_TAG);
            return true;
        } else if (error instanceof AuthFailureError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley AuthFailureError : " + error.getMessage());
            return true;
        } else if (error instanceof ServerError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley ServerError : " + error.getMessage());
            return true;
        } else if (error instanceof NetworkError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley NetworkError : " + error.getMessage());
            mRequestQueue.cancelAll(VOLLEY_REQUEST_TAG);
            return true;
        } else if (error instanceof ParseError) {
            //Crashlytics.log(Log.ERROR, TAG, "Volley ParseError : " + error.getMessage());
            return true;
        }

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            Crashlytics.log(Log.ERROR, TAG, "Response code : " + response.statusCode + "\n Data : " + error.getMessage());
            //DisplayMessage.error("Something went wrong. Please try again later.", this);
            return true;
        }
        return false;
    }

    private static String emailId, passwordLogin;

    public void postTokenPassword(String type, AppCompatActivity activity, String email, String password) {
        String tokenUrl = Constants.URL.emaillogin + "&id_type=email&username=" + email
                + "&password=" + password;
        Crashlytics.log(Log.DEBUG, TAG, "Token Url : " + tokenUrl);

        emailId = email;
        passwordLogin = password;
        // POST tokenUrl API
        postStringUrlEncoded(tokenUrl, new HashMap<>(), response -> {
            Crashlytics.log(Log.DEBUG, TAG, "Token Auth Response : " + response);
            try {
                SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
                JSONObject responseJson = new JSONObject(response);
                userProfile.saveLogInDetails(email, password);
                userProfile.saveTokenDetails(
                        responseJson.getString(Constants.User.ACCESS_TOKEN),
                        responseJson.getString(Constants.User.TOKEN_TYPE),
                        responseJson.getString(Constants.User.REFRESH_TOKEN),
                        responseJson.getString(Constants.User.SCOPE)
                );
                SharedPrefUtils.saveUserProfile(activity.getBaseContext(), userProfile);

                Crashlytics.log(Log.DEBUG, TAG, "Refresh token and Scope : " + userProfile.refresh_token + " " + userProfile.scope);
                if (type.equals(Constants.LOGIN))
                    getUserDemography();
                else
                    Crashlytics.log(Log.DEBUG, TAG, "Token expired !! fetching new token.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    public void postTokenFb() {
        isFbLogin = true;
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
        Crashlytics.log(Log.DEBUG, TAG, "FB Access token : " + SharedPrefUtils.getRegisterPref(this).getString(PASSWORD, null));
        String tokenUrl = Constants.URL.fbLogin + "&access=" +
                SharedPrefUtils.getRegisterPref(this).getString(PASSWORD, null);
        Crashlytics.log(Log.DEBUG, TAG, "Token Url : " + tokenUrl);
        // POST tokenUrl API
        postStringUrlEncoded(tokenUrl, new HashMap<String, String>(), response -> {
            Crashlytics.log(Log.DEBUG, TAG, "Token Auth Response : " + response);
            try {
                JSONObject responseJson = new JSONObject(response);
                userProfile.saveTokenDetails(
                        responseJson.getString(Constants.User.ACCESS_TOKEN),
                        responseJson.getString(Constants.User.TOKEN_TYPE),
                        responseJson.getString(Constants.User.REFRESH_TOKEN),
                        responseJson.getString(Constants.User.SCOPE)
                );
                SharedPrefUtils.saveUserProfile(getBaseContext(), userProfile);
                getUserDemography();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    private void refreshToken() {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
        String refreshTokenUrl = Constants.URL.refreshToken + userProfile.scope +
                "&refresh_token=" + userProfile.refresh_token;
        Crashlytics.log(Log.DEBUG, TAG, "Refresh Token Url : " + refreshTokenUrl);

        postStringUrlEncoded(refreshTokenUrl, new HashMap<>(), response -> {
            Crashlytics.log(Log.DEBUG, TAG, "Token Auth Response : " + response);
            try {
                JSONObject responseJson = new JSONObject(response);
                userProfile.saveTokenDetails(
                        responseJson.getString(Constants.User.ACCESS_TOKEN),
                        responseJson.getString(Constants.User.TOKEN_TYPE),
                        responseJson.getString(Constants.User.REFRESH_TOKEN),
                        responseJson.getString(Constants.User.SCOPE)
                );
                SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);

                getUserDemography();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    public void getString(String url, Response.Listener<String> success, Response.ErrorListener failure) {
        if (failure == null) {
            failure = defaultErrorListener;
        }

        StringRequest getStringRequest = new StringRequest
                (Request.Method.GET, url, success, failure) {

            // Set Request header definition
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPrefUtils.UserProfile profile = SharedPrefUtils.getUserProfile(context);
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + profile.access_token);
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                return volleyError;
            }
        };

        getStringRequest.setShouldCache(false);
        mRequestQueue.add(getStringRequest);
    }


    public void getJson(String url, Response.Listener<JSONObject> success, Response.ErrorListener failure) {
        if (failure == null) {
            failure = defaultErrorListener;
        }

        JsonObjectRequest getJsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, success, failure) {

            // Set Request header definition
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPrefUtils.UserProfile profile = SharedPrefUtils.getUserProfile(context);
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + profile.access_token);
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                return volleyError;
            }
        };

        getJsonRequest.setShouldCache(false);
        mRequestQueue.add(getJsonRequest);
    }


    public void postStringUrlEncoded(String url, String stringParam,
                                     Response.Listener<String> success, Response.ErrorListener failure) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, success, failure) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                Crashlytics.log(Log.DEBUG, TAG, "Access emaillogin : " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                headers.put("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return stringParam.getBytes();
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                return volleyError;
            }
        };

        stringRequest.setShouldCache(false);
        mRequestQueue.add(stringRequest);
    }

    public void postJsonStringUrlEncodedWithToken(String url, JSONObject params,
                                                  Response.Listener<String> success, Response.ErrorListener failure) {

        if (failure == null)
            failure = defaultErrorListener;

        StringRequest postStringRequest = null;

        if (url.equals(Constants.URL.createQuery)) {
            postStringRequest = new StringRequest(
                    Request.Method.POST,
                    url, success, failure) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    Crashlytics.log(Log.DEBUG, TAG, "Access emaillogin : " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return ("json=" + params).getBytes();
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                        volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                    return volleyError;
                }
            };
        } else {
            postStringRequest = new StringRequest(
                    Request.Method.POST,
                    url, success, failure) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    Crashlytics.log(Log.DEBUG, TAG, "Access emaillogin : " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    if (url.equals(Constants.URL.userFeed) || url.equals(Constants.URL.shareQuery)) {
                        headers.put("Content-Type", "application/json");
                    } else {
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                    }
                    headers.put("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    Crashlytics.log(Log.DEBUG, TAG, "Param body : " + params.toString());
                    return ("" + params).getBytes();
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                        volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                    return volleyError;
                }
            };
        }

        postStringRequest.setShouldCache(false);
        mRequestQueue.add(postStringRequest);
    }

    public void postStringUrlEncoded(String url, Map<String, String> params,
                                     Response.Listener<String> success, Response.ErrorListener failure) {

        if (failure == null)
            failure = defaultErrorListener;

        StringRequest postJsonRequest = new StringRequest(
                Request.Method.POST,
                url, success, failure) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        postJsonRequest.setShouldCache(false);
        mRequestQueue.add(postJsonRequest);
    }

    public void postJson(String url, JSONObject params,
                         Response.Listener<JSONObject> success, Response.ErrorListener failure) {
        if (failure == null)
            failure = defaultErrorListener;

        JsonObjectRequest postJsonRequest;

        if (url.contains("register")) {
            postJsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, params, success, failure) {
                // Set Request header definition

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                //In your extended request class
                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                        volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                    }
                    return volleyError;
                }
            };
        } else {
            postJsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, params, success, failure) {
                // Set Request header definition

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    Crashlytics.log(Log.DEBUG, TAG, "Access token : " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    headers.put("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(getApplicationContext()).access_token);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                //In your extended request class
                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                        volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                    return volleyError;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                        JSONObject result = null;

                        if (jsonString != null && jsonString.length() > 0)
                            result = new JSONObject(jsonString);

                        return Response.success(result,
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (JSONException je) {
                        return Response.error(new ParseError(je));
                    }
                }
            };
        }

        postJsonRequest.setTag(VOLLEY_REQUEST_TAG);
        postJsonRequest.setShouldCache(false);
        mRequestQueue.add(postJsonRequest);
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
            Toast.makeText(this, "We have resend your mail", Toast.LENGTH_LONG).show();
        });

        final TextView activateWithLinkView = (TextView) dialogView.findViewById(R.id.textView_activate_with_link);
        activateWithLinkView.setOnClickListener(v -> {
            activateCodeDialog.dismiss();
            displayActivationLinkDialog();
        });

        final Button submitCodeBtn = (Button) dialogView.findViewById(R.id.btn_verify);
        submitCodeBtn.setOnClickListener(v -> {
            SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
            //GeneralUtils.hideSoftKeyboard(NetworkActivity.this, view);
            JSONObject activationPayload = new JSONObject();
            try {
                activationPayload.put(Constants.User.EMAIL, emailId);
                activationPayload.put(Constants.User.PASSWORD, passwordLogin);
                activationPayload.put(Constants.User.USER_TYPE, "user");
                activationPayload.put(Constants.User.VERIFICATION_CODE, userInput.getText().toString());
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + activationPayload.toString());

                // POST activation API
                postJson(Constants.URL.activation, activationPayload, response -> {
                    Crashlytics.log(Log.DEBUG, TAG, "Response : " + response);
                    postTokenPassword(Constants.LOGIN, this, userProfile.email, userProfile.password);
                }, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        // create alert dialog and show it
        activateCodeDialog = alertDialogBuilder.create();
        activateCodeDialog.show();
    }

    private AlertDialog activateLinkDialog;

    private boolean isActivationDialogVisible = false;

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
            //Snackbar.make(view, "We have resend your mail", Snackbar.LENGTH_LONG).show();
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
            postTokenPassword(Constants.LOGIN, NetworkActivity.this, userProfile.email, userProfile.password);
        });

        // create alert dialog and show it
        activateLinkDialog = alertDialogBuilder.create();
        activateLinkDialog.show();
    }

    public void registerUser() {
        JSONObject registerPayload = new JSONObject();
        try {
            SharedPreferences registerPref = SharedPrefUtils.getRegisterPref(this);

            Crashlytics.log(Log.DEBUG, TAG, "User profile values : " + registerPref.getString(Constants.User.NAME, null));

            registerPayload.put(Constants.User.EMAIL, registerPref.getString(Constants.User.EMAIL, null));
            registerPayload.put(Constants.User.PASSWORD, registerPref.getString(Constants.User.PASSWORD, null));
            registerPayload.put(Constants.User.USER_NAME, registerPref.getString(Constants.User.NAME, null));
            registerPayload.put(Constants.User.EULA, true);
            Crashlytics.log(Log.DEBUG, TAG, "Payload : " + registerPayload.toString());

            // POST registerPassword API
            postJson(Constants.URL.registerPassword, registerPayload, response -> {
                        Crashlytics.log(Log.DEBUG, TAG, "Successfully sent mail to user.");
                    },
                    null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserDemography() {
        // API get user demography
        Crashlytics.log(Log.DEBUG, TAG, "getUserProfileDemography called !!! ");
        getJson(Constants.URL.getUserProfileDemography, response -> {
            SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);
            Crashlytics.log(Log.DEBUG, TAG, "getUserProfileDemography response : " + response);
            try {
                userProfile.saveUserDemography(
                        response.getString(BIRTH),
                        response.getString(GENDER),
                        response.getString(EDUCATIONLEVEL),
                        response.getString(COUNTRYOFRESIDENCE),
                        response.getString(UPDATED)
                );
            } catch (JSONException e1) {
                Crashlytics.log(Log.ERROR, TAG, "getUserProfileDemography Exception message : " + e1.getMessage());
            }
            SharedPrefUtils.saveUserProfile(getApplicationContext(), userProfile);

            Crashlytics.log(Log.DEBUG, TAG, "Country of residence : " + userProfile.country_of_residence);
            if (userProfile.country_of_residence == null) {
                GeneralUtils.routeAndClearBackStack(this, CompleteProfileActivity.class);
            } else {
                GeneralUtils.routeAndClearBackStack(this, DashboardActivity.class);
            }
        }, null);
    }

    public Response.ErrorListener defaultErrorListener = error -> {
        Crashlytics.log(Log.ERROR, TAG, "Error : " + error);
        if (error != null) {
          //  Toast.makeText(getApplicationContext(),"Error!!!",Toast.LENGTH_SHORT).show();
            // TODO: 14.11.16 remake it
            try {

                JSONObject errorObject = new JSONObject(error.getMessage());
                if (errorObject.getString("error").equals("Age below 13 not allowed according to ToS."))
                    Toast.makeText(this, "Age below 13 not allowed according to ToS.", Toast.LENGTH_LONG).show();
                else {
                    String errorDescription = errorObject.getString(Constants.Error.ERROR_DESCRIPTION);
                    Crashlytics.log(Log.ERROR, TAG, "Error Object : " + errorObject);
                    if (errorObject.getString("error_description").equals("Access token invalid or malformed")) {
                        refreshToken();
                    } else if (errorObject.getString("error_description").equals("This user hasn't been activated yet")) {
                        Toast.makeText(this, "User not yet activated. Please activate using link or code.", Toast.LENGTH_LONG)
                                .show();
                        displayActivationLinkDialog();

                    } else if (errorDescription.equals("An account with that e-mail already exists.")) {
                        Toast.makeText(this, "E-mail already exists. Register using different email.", Toast.LENGTH_LONG).show();

                    } else if (errorDescription.equals("The authorization server encountered an unexpected condition " +
                            "that prevented it from fulfilling the request.")) {
                        Toast.makeText(this, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();

                    } else if (errorDescription.equals("The resource Owner Credentials provided are invalid.")) {
                        Crashlytics.log(Log.DEBUG, TAG, "isFblogin : " + isFbLogin);
                        if (!isFbLogin)
                            Toast.makeText(this, "Invalid login credentials.", Toast.LENGTH_LONG).show();
                        else {
                            postRegisterUser();
                          // Intent intent = new Intent(NetworkActivity.this, VerifyEmailActivity.class);
                              Intent intent = new Intent(NetworkActivity.this, CompleteProfileActivity.class);
                            intent.putExtra(Constants.User.VERIFIED_FB_EMAIL, true);
                            startActivity(intent);
                        }
                    } else if (errorDescription.equals("An account with that facebook ID already exists.")) {
                        postTokenFb();

                    } else if (errorDescription.equals("The resource Owner Credentials provided are invalid.")) {


                    } else {
                        Crashlytics.log(Log.ERROR, TAG, "Response Error : " + error.getMessage());
                        Toast.makeText(this, "Failed to submit data. Please check logs.", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Crashlytics.log(Log.ERROR, TAG, "No response error NULL");
        }
    };
//Jagdeep Changes 16 Jan 2016
    public void postRegisterUser() {
        JSONObject registerPayload = new JSONObject();
        try {
            Crashlytics.log(Log.DEBUG, TAG, "User profile name : " + registerPref.getString(Constants.User.NAME, null));

            registerPayload.put(Constants.User.EMAIL, registerPref.getString(Constants.User.EMAIL, null));
            registerPayload.put(Constants.User.USER_NAME, registerPref.getString(Constants.User.NAME, null));
            registerPayload.put(Constants.User.EULA, true);

           /* if (!isFbLogin) {
                registerPayload.put(Constants.User.PASSWORD, registerPref.getString(Constants.User.PASSWORD, null));
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + registerPayload.toString());
                // POST registerPassword API
                postJson(Constants.URL.registerPassword, registerPayload, response -> saveUserRegistrationDetails(),
                        null);
            } else {*/
                registerPayload.put(Constants.User.FB_ACCESS_TOKEN, registerPref.getString(Constants.User.PASSWORD, null));
                Crashlytics.log(Log.DEBUG, TAG, "Payload : " + registerPayload.toString());
                // POST registerFacebook API
                postJson(Constants.URL.registerFb, registerPayload, response -> saveUserRegistrationDetails(), null);
           /* }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Jagdeep Changes 16 Jan 2016
    private void saveUserRegistrationDetails() {
        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(this);

        userProfile.saveSignUpDetails(registerPref.getString(Constants.User.NAME, null),
                registerPref.getString(Constants.User.EMAIL, null),
                registerPref.getString(Constants.User.PASSWORD, null));
        SharedPrefUtils.saveUserProfile(this, userProfile);

      /*  if (!isFbLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(Constants.User.VERIFIED_EMAIL, true);
            startActivity(intent);
        } else {*/
            postTokenFb();
        /*}*/
    }
}