package com.getqueried.getqueried_android.create_query.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.appyvet.rangebar.RangeBar;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.model.CreateQueryLocation;
import com.getqueried.getqueried_android.model.LocationOptions;
import com.getqueried.getqueried_android.model.TxtInpQuery;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ImageUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommunityFragment extends Fragment {

    private static final String TAG = "CommunityFragment";
    private static final Integer LOCATION_LEVEL = 1;
    private static final String TARGET_LOCATION = "location";

    private ArrayList<String> genderList = new ArrayList<>();
    private static boolean isMaleSelected = false;
    private static boolean isFemaleSelected = false;

    @Bind(R.id.rangebar)
    RangeBar rangeBar;

    @Bind(R.id.textView_minAge)
    TextView minAgeView;

    @Bind(R.id.textView_maxAge)
    TextView maxAgeView;

    @Bind(R.id.toggle_male)
    ToggleButton maleToggleView;

    @Bind(R.id.toggle_female)
    ToggleButton femaleToggleView;

    /*@Bind(R.id.textView_male)
    TextView maleTextView;

    @Bind(R.id.textView_female)
    TextView femaleTextView;*/

    private String selectedGender;

    @OnClick({R.id.toggle_male, R.id.toggle_female})
    public void toggleGender(View view) {
        int viewID = view.getId();
        if (viewID == R.id.toggle_male) {
            selectedGender = "male";
            femaleToggleView.setChecked(false);
            maleToggleView.setChecked(true);
        } else {
            // if not male then female
            selectedGender = "female";
            maleToggleView.setChecked(false);
            femaleToggleView.setChecked(true);
        }
    }

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment getInstance() {
        return new CommunityFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    List<Integer> ageValues = new ArrayList<>();
    ArrayList<List<Integer>> ageList = new ArrayList<List<Integer>>();
    ArrayList<String> eduList = new ArrayList<>();

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        eduList.add(SharedPrefUtils.getUserProfile(getActivity()).education_level);

        rangeBar.setOnRangeBarChangeListener((rangeBar1, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {
            Crashlytics.log(Log.DEBUG, TAG, "Left Range : " + leftPinValue + " Right Range : " + rightPinValue);
            minAgeView.setText(leftPinValue);
            maxAgeView.setText(rightPinValue);
            ageValues.add(Integer.valueOf(leftPinValue));
            ageValues.add(Integer.valueOf(rightPinValue));
            ageList.add(ageValues);
        });
    }

    private ArrayList<String> locationList;
    private SharedPreferences queryPref;

    @OnClick(R.id.layout_post_query)
    void postQuery() {

        queryPref = SharedPrefUtils.getQueryPref(getActivity());
        Gson gson = new Gson();

        String passedQuestionList = queryPref.getString(Constants.CreateQuery.QUESTION_LIST, null);
        Crashlytics.log(Log.DEBUG, TAG, "questionOption params : " + passedQuestionList);

        // Set location
        locationList = new ArrayList<>();
        locationList.add("denmark");
        Crashlytics.log(Log.DEBUG, TAG, "locationList : " + locationList);

        // Set selected gender
        genderList.add(selectedGender);

        try {
            String queryType = queryPref.getString(Constants.CreateQuery.TYPE, null);
            String queryTitle = queryPref.getString(Constants.CreateQuery.QUERY_TITLE, null);
            boolean isAnonymous = queryPref.getBoolean(Constants.CreateQuery.ANONYMOUS, false);

            LocationOptions locationOptions = new LocationOptions(String.valueOf(locationList),
                    LOCATION_LEVEL, genderList, ageList, eduList);

            Crashlytics.log(Log.DEBUG, TAG, "queryType : " + queryType);
            if (queryType != null) {
                switch (queryType) {
                    case Constants.CreateQuery.TXTINP:
                        TxtInpQuery txtInpQueryObject = gson.fromJson(passedQuestionList, TxtInpQuery.class);
                        JSONObject questionListObject = new JSONObject();
                        questionListObject.put(Constants.CreateQuery.TYPE, txtInpQueryObject.type);
                        questionListObject.put(Constants.CreateQuery.QUESTION_TEXT, txtInpQueryObject.questionText);
                        questionListObject.put(Constants.CreateQuery.QUESTION_IMAGE, txtInpQueryObject.questionImage);
                        Crashlytics.log(Log.DEBUG, TAG, "questionList : " + questionListObject);
                        JSONArray questionListArray = new JSONArray();
                        questionListArray.put(questionListObject);
                        Crashlytics.log(Log.DEBUG, TAG, "questionListArray : " + questionListArray.toString());

                        CreateQueryLocation createQueryLocation = new CreateQueryLocation(
                                queryTitle,
                                isAnonymous,
                                TARGET_LOCATION,
                                locationOptions,
                                questionListArray);
                        postCreatedQuery(createQueryLocation, queryType);
                        break;

                    case Constants.CreateQuery.TXTOPT:
                        CreateQueryLocation createQueryLocation1 = new CreateQueryLocation(
                                queryTitle,
                                isAnonymous,
                                TARGET_LOCATION,
                                locationOptions,
                                new JSONArray(passedQuestionList));
                        postCreatedQuery(createQueryLocation1, queryType);
                        break;

                    case Constants.CreateQuery.NUMIMP:
                        break;

                    case Constants.CreateQuery.IMGOPT:
                        CreateQueryLocation createQueryLocation2 = new CreateQueryLocation(
                                queryTitle,
                                isAnonymous,
                                TARGET_LOCATION,
                                locationOptions,
                                new JSONArray(passedQuestionList));
                        postCreatedQuery(createQueryLocation2, queryType);
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postCreatedQuery(CreateQueryLocation createQueryLocation, String queryType) {

        JSONObject params = new JSONObject();
        JSONObject jsonObjectOptions = new JSONObject();
        try {
            jsonObjectOptions.put(Constants.Options.LOCATION, new JSONArray(locationList));
            jsonObjectOptions.put(Constants.Options.GENDER, new JSONArray(genderList));
            jsonObjectOptions.put(Constants.Options.LOCATIONLEVEL, createQueryLocation.getOptions().getLocationLevel());
            Crashlytics.log(Log.DEBUG, TAG, "jsonObjectOptions : " + jsonObjectOptions.toString());

            params.put(Constants.CreateQuery.QUERY_TITLE, createQueryLocation.getQueryTitle());
            params.put(Constants.CreateQuery.ANONYMOUS, createQueryLocation.getAnonymous());
            params.put(Constants.CreateQuery.TARGET, createQueryLocation.getTarget());
            params.put(Constants.CreateQuery.OPTIONS, jsonObjectOptions);
            params.put(Constants.CreateQuery.QUESTION_LIST, createQueryLocation.getQuestionList());
            Crashlytics.log(Log.DEBUG, TAG, "Params : " + params.toString());

            if (!queryType.equals(Constants.CreateQuery.IMGOPT)) {
                String questionImagePath = queryPref.getString(Constants.CreateQuery.QUESTION_IMAGE_PATH, null);
                Crashlytics.log(Log.DEBUG, TAG, "Question Image path : " + questionImagePath);

                // Check if question Image is selected
                if (questionImagePath == null)
                    ((NetworkActivity) getActivity()).postJsonStringUrlEncodedWithToken(
                            Constants.URL.createQuery, params, successListener, null);
                else
                    new UploadQueryWithImage(params, questionImagePath, queryType).execute();

            } else {
                String questionImagePath = queryPref.getString(Constants.CreateQuery.QUESTION_IMAGE_PATH, null);
                Crashlytics.log(Log.DEBUG, TAG, "Question Image path : " + questionImagePath);

                // Check if question Image is selected
                new UploadQueryWithImage(params, questionImagePath, queryType).execute();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Response.Listener<String> successListener = responseObject -> {
        Crashlytics.log(Log.DEBUG, TAG, "Response : " + responseObject);
        routeOnSuccess();
    };

    public void routeOnSuccess() {
        Toast.makeText(getActivity(), "Query created successfully", Toast.LENGTH_LONG).show();
        SharedPrefUtils.clearSharedPreferences(SharedPrefUtils.getQueryPref(getActivity()));
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        intent.putExtra("view_pager_position", 2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (genderList.size() > 0)
            genderList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (genderList.size() > 0)
            genderList.clear();
    }

    private class UploadQueryWithImage extends AsyncTask<Void, Void, Boolean> {

        private String questionImagePath;
        private JSONObject jsonObject;
        private String queryType;

        public UploadQueryWithImage(JSONObject params, String questionImagePath, String queryType) {
            this.jsonObject = params;
            this.questionImagePath = questionImagePath;
            this.queryType = queryType;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (!queryType.equals(Constants.CreateQuery.IMGOPT)) {
                try {
                    return ImageUtils.uploadImageQuery(getActivity(), Constants.URL.createQuery, "POST",
                            jsonObject, BitmapFactory.decodeFile(questionImagePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                SharedPreferences queryPrefEditor = SharedPrefUtils.getQueryPref(getActivity());
                try {
                    Bitmap[] options = new Bitmap[12];
                    Bitmap questionImage = null;
                    try{
                        questionImage = BitmapFactory.decodeFile(questionImagePath);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        options[0] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_0, null))).resize(250,250).get();
                        options[1] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_1, null))).resize(250,250).get();
                        options[2] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_2, null))).resize(250,250).get();
                        options[3] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_3, null))).resize(250,250).get();
                        options[4] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_4, null))).resize(250,250).get();
                        options[5] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_5, null))).resize(250,250).get();
                        options[6] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_6, null))).resize(250,250).get();
                        options[7] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_7, null))).resize(250,250).get();
                        options[8] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_8, null))).resize(250,250).get();
                        options[9] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_9, null))).resize(250,250).get();
                        options[10] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_10, null))).resize(250,250).get();
                        options[11] = Picasso.with(getActivity()).load(new File(queryPrefEditor.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_11, null))).resize(250,250).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ImageUtils.uploadImpOptQueryWithImage(getActivity(), Constants.URL.createQuery,
                            "POST", jsonObject,
                            questionImage,
                            options[0], options[1], options[2], options[3],options[4],options[5],options[6],options[7],options[8]
                    ,options[9],options[10],options[11]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (isSuccess) {
                Crashlytics.log(Log.DEBUG, TAG, "Successfully updated query with image");
                Toast.makeText(getActivity(), "Query created successfully", Toast.LENGTH_LONG).show();
                GeneralUtils.routeAndClearBackStack( getActivity(), DashboardActivity.class);
            } else
                Crashlytics.log(Log.DEBUG, TAG, "Failed to update query with image");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPrefUtils.clearSharedPreferences(SharedPrefUtils.getQueryPref(getActivity()));
    }
}
