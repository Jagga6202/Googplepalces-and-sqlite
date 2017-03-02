package com.getqueried.getqueried_android.answer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.fragments.AnswerProvided;
import com.getqueried.getqueried_android.answer.fragments.DisplayImgOptFragment;
import com.getqueried.getqueried_android.answer.fragments.DisplayTxtInpFragment;
import com.getqueried.getqueried_android.answer.fragments.DisplayTxtOptFragment;
import com.getqueried.getqueried_android.answer.fragments.TextInpFragment;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.model.AnswerOptionItem;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.getqueried.getqueried_android.utils.Constants.Answer.ANONYMOUS;
import static com.getqueried.getqueried_android.utils.Constants.Answer.ANSWER_LIST;
import static com.getqueried.getqueried_android.utils.Constants.Answer.QUERYID;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.IMGOPT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTINP;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTOPT;
import static com.getqueried.getqueried_android.utils.Constants.User.NAME;
import static com.getqueried.getqueried_android.utils.Constants.User.SMALLIMAGEPATH;

public class AnswerQueriesActivity extends NetworkActivity {

    private static final String HEADER_TITLE = "Answer Query";
    public static final String TAG = "AnswerQueriesActivity";

    @Bind(R.id.textView_questionText)
    TextView questionTextView;

    @Bind(R.id.imageView_anonymous)
    ImageView anonymousImageView;

    @Bind(R.id.header_layout)
    LinearLayout headerLayout;

    @Bind(R.id.profile_image)
    CircleImageView profileImageView;

    @Bind(R.id.textView_user_name)
    TextView userNameView;

    @Bind(R.id.textView_footer_message)
    TextView footerTextView;
    @Bind(R.id.layout_post_answer)
    LinearLayout footerLayout;

    // if answers is presented
    @Bind(R.id.layout_post_query)
    LinearLayout layoutPostQuery;
    @Bind(R.id.textView_answers_count)
    TextView answersCount;
    @Bind(R.id.textView_like_count)
    TextView likeCount;
    @Bind(R.id.textView_share_count)
    TextView shareCount;

    // for sharing on the facebook
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    // JSON with all question information
    Bundle bundle;
    private JSONArray queriesJSON;
    private JSONObject queryJSON;
    private JSONObject questionListObject;
    private int position;

    private String questionText;
    private String queryType;
    private String queryId;

    private ArrayList answers;

    Fragment fragmentToPresent = null;
    String fragmentTag = null;

    private boolean isForShowingResult;
    private boolean isAnonymous = false;

    private boolean isAnswerProvided = false;
    private int selectedOptionNumber = -1;
    private String answeredText = null;
    private boolean isQueryLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_queries);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        isForShowingResult = bundle.getBoolean(Constants.Data.IS_FOR_SHOWING_RESULT);
        position = bundle.getInt(Constants.Data.QUERY_POSITION);

        setView();
        fetchQuestionInfo();
        setQuestion();

        if (!isForShowingResult) {
            // just show the question
            addFragment();
        } else {
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, facebookCallback);
            // Get query stats API
            postStringUrlEncoded(Constants.URL.getQueryStats, "[\"" + queryId + "\"]", successHandlerQueryStats, null);
            // get answers
            postStringUrlEncoded(Constants.URL.getQueryResults,
                    "[\"" + queryId + "\"]", response -> {
                        JSONArray jsonArrayQuery = null;
                        try {
                            jsonArrayQuery = new JSONArray(response);
                            if (queryType.equals(TXTINP)) {
                                JSONArray answersJSON = jsonArrayQuery.getJSONObject(0).getJSONObject(Constants.Answer.ANSWER_LIST).getJSONArray("1");
                                ArrayList<TxtInpAnswer> answer = new ArrayList<>(answersJSON.length());
                                JSONObject answerItem;
                                for (int i = 0; i < answersJSON.length(); i++) {
                                    answerItem = answersJSON.getJSONObject(i);
                                    answer.add(new TxtInpAnswer(
                                            answerItem.getString(Constants.Answer.ANSWER_TEXT),
                                            answerItem.getString(Constants.Answer.ANSWER_TIME),
                                            answerItem.getString(Constants.Answer.USER_ID)));
                                }
                                answers = answer;
                            } else {
                                JSONObject answersJSON = jsonArrayQuery.getJSONObject(0).getJSONObject(Constants.Answer.ANSWER_LIST).getJSONObject("1");
                                ArrayList<Integer> answersCount = new ArrayList<>(4);
                                String iS;
                                for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
                                    iS = Integer.toString(i);
                                    if (answersJSON.has(iS)) {
                                        answersCount.add(answersJSON.getInt(iS));
                                    } else {
                                        answersCount.add(0);
                                    }
                                }
                                answers = answersCount;
                            }
//                                addFragment();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            answers = new ArrayList();
                        }
                        if (jsonArrayQuery != null) {
                            Crashlytics.log(Log.DEBUG, TAG, "Length Query structure  array : " + jsonArrayQuery.length());
                        }
                        addFragment();
                    }, error -> {
                        Crashlytics.log(Log.ERROR, TAG, "Error " + error.getMessage());
                    });
        }
    }

    private void addFragment() {
        // get options if needed
        ArrayList<AnswerOptionItem> options = null;
        try {
            if (queryType.equals(TXTOPT) || queryType.equals(IMGOPT)) {
                options = fetchTheAnswerOptionsFromJson(questionListObject.getJSONArray(Constants.CreateQuery.ANSWER_OPTION_LIST));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle dataToFragment = new Bundle();
        dataToFragment.putBoolean(Constants.Data.ANSWERS_PROVIDED, isForShowingResult);
        dataToFragment.putSerializable(Constants.CreateQuery.OPTIONS, options);
        if (isForShowingResult) {
            dataToFragment.putSerializable(Constants.Answer.ANSWER_LIST, answers);
        }
        switch (queryType) {
            case TXTINP:
                if (isForShowingResult) {
                    fragmentToPresent = DisplayTxtInpFragment.getInstance();
                    fragmentTag = DisplayImgOptFragment.TAG;
                } else {
                    fragmentToPresent = TextInpFragment.getInstance(callback);
                    fragmentTag = TextInpFragment.TAG;
                }
                break;
            case TXTOPT:
                fragmentToPresent = DisplayTxtOptFragment.getInstance(callback);
                fragmentTag = DisplayTxtOptFragment.TAG;
                break;
            case IMGOPT:
                fragmentToPresent = DisplayImgOptFragment.getInstance(callback);
                fragmentTag = DisplayImgOptFragment.TAG;
                break;
        }
        fragmentToPresent.setArguments(dataToFragment);
        presentFragment(fragmentToPresent, fragmentTag);
    }

    private void setView() {
        // set right footer
        footerLayout.setVisibility(isForShowingResult ? View.GONE : View.VISIBLE);
        layoutPostQuery.setVisibility(isForShowingResult ? View.VISIBLE : View.GONE);
        headerLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
    }

    private void fetchQuestionInfo() {
        try {
            queriesJSON = new JSONArray(bundle.getString(Constants.Data.ARRAY_OF_QUERIES));
            queryJSON = queriesJSON.getJSONObject(position);

            // Get user info from query Id
            queryId = queryJSON.getString(Constants.Answer.QUERYID);
            Crashlytics.log(Log.DEBUG, TAG, "Query Id : " + queryId);

            // fetch query creator data
            getQueryMetadata(queryId);

            JSONArray jsonArrayQuestionList = new JSONArray(queryJSON.getString(Constants.CreateQuery.QUESTION_LIST));
            Crashlytics.log(Log.DEBUG, TAG, "jsonArrayQuestionList Object : " + jsonArrayQuestionList.toString());
            questionListObject = jsonArrayQuestionList.getJSONObject(0);
            queryType = questionListObject.getString(Constants.CreateQuery.TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setQuestion() {
        try {
            // Parse Question text and display

            // Set question text
            questionText = questionListObject.getString(Constants.Query.QUESTION_TEXT);
            questionTextView.setText(questionText);

            // Set question Image
            String queryImagePath = questionListObject.getString(Constants.CreateQuery.QUESTION_IMAGE).replace("\\", "");
            Crashlytics.log(Log.DEBUG, TAG, "queryImagePath : " + queryImagePath);
            if (queryImagePath.length() > 0) {
                String imageUrl = Constants.URL.getQueryImage + queryImagePath;
                Picasso.with(this).load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        headerLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Crashlytics.log(Log.ERROR, TAG, "Failed to download query image");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imageView_close_activity)
    void closeActivity() {
        finish();
    }


    // footer logic when answering
    @OnClick(R.id.textView_footer_message)
    void postOrSkip(View view) {
        if (isAnswerProvided) {
            postAnswer();
        } else {
            Intent intent;
            if (position == queriesJSON.length() - 1) {
                // go to the dashboard
                GeneralUtils.routeAndClearBackStack(this, DashboardActivity.class);
            } else {
                // show next question
                position++;
                setView();
                fetchQuestionInfo();
                setQuestion();
            }
        }
    }

    @OnClick(R.id.imageView_anonymous)
    void changeAnonymity() {
        if (!isAnonymous) {
            isAnonymous = true;
            anonymousImageView.setImageResource(R.drawable.anonymous_on);
        } else {
            isAnonymous = false;
            anonymousImageView.setImageResource(R.drawable.anonymous_off);
        }
        updateFooter();
    }

    // if isForAnswering and so !isForResult
    AnswerProvided callback = (isAnswerChosen, optionNumber, answersText) -> {
        Crashlytics.log(Log.DEBUG, TAG, "Answer provided");
        isAnswerProvided = isAnswerChosen;
        if (isAnswerChosen) {
            selectedOptionNumber = optionNumber;
            answeredText = answersText;
        } else {
            selectedOptionNumber = -1;
            answersText = null;
        }
        updateFooter();
    };

    private void updateFooter() {
        if (isAnswerProvided) {
            footerLayout.setBackgroundColor(Color.parseColor("#F65C19"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                footerTextView.setTextColor(getResources().getColor(R.color.white, null));
            } else {
                footerTextView.setTextColor(getResources().getColor(R.color.white));
            }
            // TODO: 06.11.16 should be called from the R strings
            footerTextView.setText((isAnonymous) ? "ANONYMOUS ANSWER" : "PUBLIC_ANSWER");
        } else {
            footerLayout.setBackgroundColor(getResources().getColor(R.color.whiteAlmost));
            footerTextView.setTextColor(getResources().getColor(R.color.mdtp_dark_gray));
            footerTextView.setText("SKIP ANSWER");
        }
    }

    // footer logic if answers is presented
    @OnClick(R.id.imageView_like)
    public void likeIt(ImageView view) {
        if (!isQueryLiked) {
            postStringUrlEncoded(Constants.URL.likeQuery, "[\"" + queryId + "\"]", response -> {
                Crashlytics.log(Log.DEBUG, TAG, "Like query successfully !!");
                isQueryLiked = true;
                view.setImageResource(R.drawable.liked_blue);
                // Get query stats API
                postStringUrlEncoded(Constants.URL.getQueryStats, "[\"" + queryId + "\"]", successHandlerQueryStats, null);
            }, null);
        } else {
            postStringUrlEncoded(Constants.URL.rejectQuery, "[\"" + queryId + "\"]", response -> {
                Crashlytics.log(Log.DEBUG, TAG, "Reject query successfully !!");
                isQueryLiked = false;
                // Get query stats API
                postStringUrlEncoded(Constants.URL.getQueryStats, "[\"" + queryId + "\"]", successHandlerQueryStats, null);
            }, null);
        }
    }

    private Response.Listener<String> successHandlerQueryStats = response -> {
        Crashlytics.log(Log.DEBUG, TAG, "Response Query stats : " + response);
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.get(i)));
                answersCount.setText(jsonObject.getString("answersGiven"));
                likeCount.setText(jsonObject.getString("likes"));
                shareCount.setText(jsonObject.getString("shares"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    @OnClick(R.id.imageView_post)
    public void postIt(ImageView view) {
        // TODO: 15.11.16 place more direct link
        if (ShareDialog.canShow(ShareLinkContent.class)) {
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.getqueried.com"))
                .setContentTitle("Get Queried")
                .setContentDescription("Find out the answers.")
                .build();
            shareDialog.show(shareLinkContent);
        }
    }

    FacebookCallback<Sharer.Result> facebookCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Crashlytics.log(Log.DEBUG, TAG, result.toString());
            JSONObject params = new JSONObject();
            try {
                params.put(Constants.Query.ACTION, "facebook");
                params.put(Constants.Query.ID, queryId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AnswerQueriesActivity.this.postJsonStringUrlEncodedWithToken(Constants.URL.shareQuery, params, response -> {
                Crashlytics.log(Log.DEBUG, TAG, response);
                postStringUrlEncoded(Constants.URL.getQueryStats, "[\"" + queryId + "\"]", successHandlerQueryStats, null);
            }, null);
        }

        @Override
        public void onCancel() {
            Crashlytics.log(Log.DEBUG, TAG, "Facebook share canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Crashlytics.log(Log.ERROR, TAG, error.toString());
        }
    };

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void postAnswer() {
        if (!isForShowingResult && isAnswerProvided) {
            JSONObject params = new JSONObject();
            JSONObject answerListObject = new JSONObject();
            try {
                if (queryType.equals(TXTINP)) {
                    answerListObject.put("1", answeredText);
                } else {
                    answerListObject.put("1", selectedOptionNumber);
                }
                Crashlytics.log(Log.DEBUG, TAG, "AnswerListObject : " + answerListObject);

                params.put(QUERYID, queryId);
                params.put(ANONYMOUS, isAnonymous);
                params.put(ANSWER_LIST, answerListObject);
                Crashlytics.log(Log.DEBUG, TAG, "Answer Query params : " + params);

                postJson(Constants.URL.answerQuery, params, successListener, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Response.Listener<JSONObject> successListener = responseObject -> {
        Crashlytics.log(Log.DEBUG, TAG, "Response : " + responseObject);
        Toast.makeText(this, "Query answered successfully", Toast.LENGTH_LONG).show();
        GeneralUtils.routeAndClearBackStack(this, DashboardActivity.class);
    };

    private void getQueryMetadata(String queryId) {
        Crashlytics.log(Log.DEBUG, TAG, "getQueryMetadata called !!");
        // Get query metadata
        postStringUrlEncoded(Constants.URL.getQueryMetadata, "[\"" + queryId + "\"]", response -> {
            Crashlytics.log(Log.DEBUG, TAG, "Response Query MetadataList : " + response);
            try {
                getUserMetadata(new JSONArray(response).getJSONObject(0).getString(Constants.User.USER_ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    private void getUserMetadata(String userId) {
        postStringUrlEncoded(Constants.URL.userMetadata, "[\"" + userId + "\"]", response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User Metadata Response : " + response);
            try {
                JSONObject jsonObject = new JSONArray(response).getJSONObject(0);
                userNameView.setText(jsonObject.getString(NAME));
                String imagePath = jsonObject.getString(SMALLIMAGEPATH);
                if (imagePath.length() > 0) {
                    String imageUrl = Constants.URL.getProfileImage + imagePath;
                    Picasso.with(this).load(imageUrl).into(profileImageView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null);
    }

    public ArrayList<AnswerOptionItem> fetchTheAnswerOptionsFromJson(JSONArray answersList) {
        ArrayList<AnswerOptionItem> res = new ArrayList<>();
        JSONObject answerItem;
        for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
            try {
                answerItem = answersList.getJSONObject(i);
                String answerOptionImage = answerItem.getString(Constants.CreateQuery.ANSWER_OPTION_IMAGE);
                String answerOptionText = answerItem.getString(Constants.CreateQuery.ANSWER_OPTION_TEXT);
                if (answerOptionText.isEmpty() && answerOptionImage.isEmpty()) {
                    break;
                } else {
                    res.add(new AnswerOptionItem(answerOptionImage, answerOptionText));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private boolean isFragmentPresent(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        return frag instanceof TextInpFragment || frag instanceof DisplayTxtInpFragment || frag instanceof DisplayImgOptFragment || frag instanceof DisplayTxtOptFragment;
    }

    private void presentFragment(Fragment fragment, String fragmentTAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isFragmentPresent("AnswerTextOptionPanel")) {
            transaction.add(R.id.answer_type_layout, fragment, fragmentTAG);
            transaction.commit();
        } else {
            transaction.replace(R.id.answer_type_layout, fragment, fragmentTAG);
            transaction.commit();
        }
    }

}
