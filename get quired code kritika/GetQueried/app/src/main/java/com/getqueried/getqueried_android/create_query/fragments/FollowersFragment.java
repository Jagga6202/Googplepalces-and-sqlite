package com.getqueried.getqueried_android.create_query.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.FollowersListAdapter;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.model.CreateQuerySpecificFollowers;
import com.getqueried.getqueried_android.model.Metadata;
import com.getqueried.getqueried_android.model.OptionsSpecificFollowers;
import com.getqueried.getqueried_android.model.TxtInpQuery;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ImageUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.RecyclerViewItemDecoration;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
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

import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANONYMOUS;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.HAS_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.IMGOPT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.OPTIONS;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUERY_TITLE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_IMAGE_PATH;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_LIST;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.SLIDER_TYPE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.SPECIFICUSERS;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TARGET;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTINP;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTOPT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TYPE;
import static com.getqueried.getqueried_android.utils.Constants.User.DESCRIPTION;
import static com.getqueried.getqueried_android.utils.Constants.User.IMAGEPATH;
import static com.getqueried.getqueried_android.utils.Constants.User.NAME;
import static com.getqueried.getqueried_android.utils.Constants.User.USER_ID;


public class FollowersFragment extends Fragment {

    private static final String TAG = "FollowersFragment";
    private static final String TARGET_LOCATION = "location";
    private SharedPrefUtils.UserProfile userProfile;
    private RecyclerView recyclerViewFollowers;
    private FollowersListAdapter followersListAdapter;
    private ProgressDialog progressDialog;

    @Bind(R.id.layout_post_query_followers)
    LinearLayout postLayout;


    private String userId;
    private ArrayList<Metadata> metadataList = new ArrayList<>();
    private TextView emptyListView;

    public static FollowersFragment getInstance() {
        return new FollowersFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_followers, container, false);
        ButterKnife.bind(this, layout);
        recyclerViewFollowers = (RecyclerView) layout.findViewById(R.id.recyclerView_query_followers);
        emptyListView = (TextView) layout.findViewById(R.id.textView_empty_followers_list);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*ItemClickSupport.addTo(recyclerViewFollowers)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        recyclerView.getChildViewHolder()
                    }
                })*/

        userProfile = SharedPrefUtils.getUserProfile(getActivity());
        userId = "\"" + userProfile.uuid + "\"";
        getUsersFollowing(userId);

    }

    private void getUsersFollowing(String userId) {
        ((NetworkActivity) getActivity()).postStringUrlEncoded(Constants.URL.userFollowing, userId, response -> {
            Crashlytics.log(Log.DEBUG, TAG, "userFollowing response : " + response);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("following");
                String[] userFollowing = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    userFollowing[i] = jsonArray.getString(i).replace("\\[|\\]", "");
                    Crashlytics.log(Log.DEBUG, TAG, userFollowing[i]);
                    getUserMetadata((NetworkActivity) getActivity(),
                            "\"" + userFollowing[i] + "\"");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    public Metadata getUserMetadata(NetworkActivity networkActivity,
                                    String userId) {
        Crashlytics.log(Log.DEBUG, TAG, "getUserMetadata called !!! ");
        networkActivity.postStringUrlEncoded(Constants.URL.userMetadata, "[" + userId + "]", response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User Metadata Response : " + response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                String uuid = jsonObject.getString(USER_ID);
                String name = jsonObject.getString(NAME);
                String description = jsonObject.getString(DESCRIPTION);
                String imagePath = jsonObject.getString(IMAGEPATH);
                Metadata metadata = new Metadata(uuid, name, description, imagePath);
                metadataList.add(metadata);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setUpView();
        }, null);
        return null;
    }

    private void setUpView() {
        if (!(metadataList.size() == 0)) {
            followersListAdapter = new FollowersListAdapter(getActivity(), metadataList);
            recyclerViewFollowers.setAdapter(followersListAdapter);
            recyclerViewFollowers.addItemDecoration(new RecyclerViewItemDecoration(getActivity()));
            recyclerViewFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));

            postLayout.setEnabled(true);
            //recyclerViewFollowers.getChildViewHolder(getView().findViewById(R.id.imageButton_add_user));
        } else {
            Crashlytics.log(Log.DEBUG, TAG, "List is empty. Setting empty view.");
            recyclerViewFollowers.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }
    }

    List<OptionsSpecificFollowers> optionsFollowersList = new ArrayList<OptionsSpecificFollowers>();
    List<TxtInpQuery> questionList = new ArrayList<TxtInpQuery>();
    CreateQuerySpecificFollowers createQuerySpecificFollowers;
    private ProgressDialog dialog;
    private static boolean isQueryImageSelected;
    private String queryType;

    String questionImagePath = null;

    @OnClick(R.id.layout_post_query_followers)
    void postToFollowers() {
        ArrayList<String> followersSelectedIdList = followersListAdapter.getSelectedFollowers();
        if (followersSelectedIdList.size() > 0) {
            queryType = SharedPrefUtils.getQueryPref(getActivity()).getString(TYPE, null);
            boolean isAnonymous = SharedPrefUtils.getQueryPref(getActivity()).getBoolean(ANONYMOUS, false);
            isQueryImageSelected = SharedPrefUtils.getQueryPref(getActivity()).getBoolean(QUESTION_IMAGE, false);
            questionImagePath = SharedPrefUtils.getQueryPref(getActivity()).getString(QUESTION_IMAGE_PATH, null);

            Crashlytics.log(Log.DEBUG, TAG, "isQueryImageSelected : " + isQueryImageSelected + " questionImagePath : " + questionImagePath);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObjectOption = new JSONObject();

            for (int i = 0; i < followersSelectedIdList.size(); i++) {
                Crashlytics.log(Log.DEBUG, TAG, "Followers ids : " + followersSelectedIdList.get(i));
                jsonArray.put(followersSelectedIdList.get(i));
                optionsFollowersList.add(new OptionsSpecificFollowers(followersSelectedIdList));
            }

            try {
                jsonObjectOption.put(SPECIFICUSERS, jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Crashlytics.log(Log.DEBUG, TAG, "Followers List Object : " + jsonObjectOption);

            JSONArray jsonArrayQuestionList = null;

            if (queryType != null) {
                Crashlytics.log(Log.DEBUG, TAG, "Query type passed : " + queryType);
                switch (queryType) {
                    case TXTINP:
                        String questionText = SharedPrefUtils.getQueryPref(getActivity()).getString(QUESTION_TEXT, null);
                        questionList.add(new TxtInpQuery(TXTINP, questionText, isQueryImageSelected));
                        createQuerySpecificFollowers = new CreateQuerySpecificFollowers("My Query", isAnonymous,
                                "followers", optionsFollowersList, questionList);
                        jsonArrayQuestionList = typeTxtInpJsonParam();
                        postQuery(jsonObjectOption, jsonArrayQuestionList, questionImagePath);
                        break;

                    case TXTOPT:
                        String questionListString = SharedPrefUtils.getQueryPref(getActivity()).getString(QUESTION_LIST, null);
                        try {
                            jsonArrayQuestionList = new JSONArray(questionListString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        createQuerySpecificFollowers = new CreateQuerySpecificFollowers("My Query", isAnonymous,
                                "followers", optionsFollowersList, questionList);
                        postQuery(jsonObjectOption, jsonArrayQuestionList, questionImagePath);
                        break;

                    case IMGOPT:
                        questionListString = SharedPrefUtils.getQueryPref(getActivity()).getString(QUESTION_LIST, null);
                        try {
                            jsonArrayQuestionList = new JSONArray(questionListString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        createQuerySpecificFollowers = new CreateQuerySpecificFollowers("My Query", isAnonymous,
                                "followers", optionsFollowersList, questionList);
                        postQuery(jsonObjectOption, jsonArrayQuestionList, questionImagePath);
                        break;
                }
            }
        } else {
            Crashlytics.log(Log.DEBUG, TAG, "Followers list is empty");
            Toast.makeText(getActivity(), "Please select at least 1 follower", Toast.LENGTH_LONG).show();
        }
    }

    public void postQuery(JSONObject jsonObjectOption, JSONArray jsonArrayQuestionList, String questionImagePath) {
        JSONObject postParams = new JSONObject();
        try {
            postParams.put(QUERY_TITLE, createQuerySpecificFollowers.queryTitle);
            postParams.put(ANONYMOUS, createQuerySpecificFollowers.anonymous);
            postParams.put(TARGET, createQuerySpecificFollowers.target);
            postParams.put(OPTIONS, jsonObjectOption);
            postParams.put(QUESTION_LIST, jsonArrayQuestionList);
            Crashlytics.log(Log.DEBUG, TAG, "JsonObject : " + postParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (questionImagePath == null)
//            ((NetworkActivity) getActivity()).postJsonStringUrlEncodedWithToken(
//                    Constants.URL.createQuery, postParams, successListener, errorListener);
//        else {
            new UploadImageQuery(postParams, questionImagePath, queryType).execute();
//        }
    }


    public JSONArray typeTxtInpJsonParam() {
        JSONArray jsonArrayQuestionList = new JSONArray();
        for (int i = 0; i < questionList.size(); i++) {
            JSONObject jsonObjectQuestion = new JSONObject();
            try {
                jsonObjectQuestion.put(TYPE, questionList.get(i).type);
                jsonObjectQuestion.put(QUESTION_TEXT, questionList.get(i).questionText);
                jsonObjectQuestion.put(HAS_IMAGE, questionList.get(i).questionImage);
                jsonObjectQuestion.put(SLIDER_TYPE, "");
                jsonArrayQuestionList.put(jsonObjectQuestion);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArrayQuestionList;
    }

    Response.ErrorListener errorListener = response -> {
      Crashlytics.log(Log.ERROR, TAG, response.getMessage());
    };

    Response.Listener<String> successListener = responseObject -> {
        Crashlytics.log(Log.DEBUG, TAG, "Response : " + responseObject);
        SharedPrefUtils.clearSharedPreferences(SharedPrefUtils.getQueryPref(getActivity()));
        Toast.makeText(getActivity(), "Query created successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        intent.putExtra("view_pager_position", 2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    };

    private class UploadImageQuery extends AsyncTask<Void, Void, Boolean> {

        private String imagePath;
        private JSONObject jsonObject;
        private String queryType;

        public UploadImageQuery(JSONObject params, String imagePath, String queryType) {
            this.jsonObject = params;
            this.imagePath = imagePath;
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
                GeneralUtils.routeAndClearBackStack((AppCompatActivity) getActivity(), DashboardActivity.class);
            } else
                Crashlytics.log(Log.DEBUG, TAG, "Failed to update query with image");
        }
    }
}
