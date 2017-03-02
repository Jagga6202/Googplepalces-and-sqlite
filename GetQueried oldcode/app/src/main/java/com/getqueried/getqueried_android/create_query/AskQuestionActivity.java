package com.getqueried.getqueried_android.create_query;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.fragments.AnswerProvided;
import com.getqueried.getqueried_android.create_query.fragments.SelectImageDialog;
import com.getqueried.getqueried_android.create_query.fragments.SelectQueryTypeFragment;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.model.TxtInpQuery;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ImageUtils;
import com.getqueried.getqueried_android.utils.MPermissionChecker;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANONYMOUS;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUERY_TITLE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_LIST;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTINP;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TYPE;

public class AskQuestionActivity extends NetworkActivity {

    private static final String TAG = "AskQuestionActivity";

    public static boolean isAnonymous = false;
    public static boolean isQuestionImageSelected = false;

    private String selectedPhotoPath;

    private SharedPrefUtils.UserProfile userProfile;

    // DEFAULT, can be changed in future to other type
    private String currentType = Constants.CreateQuery.TXTINP;
    AnswerProvided callback = (isAnswerChosen, optionNumber, answersText) -> {
        currentType = answersText;
    };


    @Bind(R.id.imageView_add_query_image)
    ImageView addQueryImageView;

    @Bind(R.id.layoutBackground)
    ImageView layoutBackground;

    @Bind(R.id.editText_question)
    EditText questionView;

    @Bind(R.id.textView_query_word_count)
    TextView wordCountView;

    @Bind(R.id.imageView_anonymous)
    ImageView anonymousImageView;

    @Bind(R.id.textView_anonymous)
    TextView creatorNameView;

    @Bind(R.id.layout_post_query_txtinp)
    LinearLayout postTxtInpType;

    SharedPreferences.Editor putQueryPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_query);
        ButterKnife.bind(this);

        GeneralUtils.setToolbarTitle(this, "Ask a question");
        GeneralUtils.closeActivityButton(this);

        putQueryPref = SharedPrefUtils.putQueryPref(this);

        userProfile = SharedPrefUtils.getUserProfile(this);
        creatorNameView.setText(userProfile.name);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.query_type_layout, SelectQueryTypeFragment.createInstance(callback), "DisplaySelectQueryPanel");
        transaction.commit();

        int wordCount = 125;
        questionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                wordCountView.setText(String.valueOf(wordCount));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Crashlytics.log(Log.DEBUG, TAG, "Text start : " + start + " before : " + before + " Count : " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                wordCountView.setText(String.valueOf(wordCount - s.length()));

            }
        });
    }


    @OnClick(R.id.layout_anonymous)
    void changeAnonymity() {
        if (!isAnonymous) {
            isAnonymous = true;
            creatorNameView.setText("Anonymous");
            anonymousImageView.setImageResource(R.drawable.anonymous_on);
            putQueryPref.putBoolean(Constants.Query.ANONYMOUS, isAnonymous);
        } else {
            isAnonymous = false;
            creatorNameView.setText(userProfile.name);
            anonymousImageView.setImageResource(R.drawable.profile_placeholder_icon);
            putQueryPref.putBoolean(Constants.Query.ANONYMOUS, isAnonymous);
        }
    }

    @OnClick(R.id.imageView_add_query_image)
    void addImage() {
        if (!isQuestionImageSelected) {
            FragmentManager fm = getFragmentManager();
            SelectImageDialog dialogFragment = new SelectImageDialog(listener);
            dialogFragment.show(fm, "SelectImageDialogFragment");
        } else {
            layoutBackground.setImageDrawable(null);
            questionView.setHintTextColor(Color.parseColor(getString(R.string.default_text_color)));
            questionView.setTextColor(Color.parseColor(getString(R.string.default_text_color)));
            creatorNameView.setTextColor(Color.parseColor(getString(R.string.default_text_color)));
            addQueryImageView.setImageResource(R.drawable.ic_action_camera);
            putQueryPref
                    .putString(Constants.CreateQuery.QUESTION_IMAGE_PATH, "")
                    .apply();
            isQuestionImageSelected = false;
        }
    }

    SelectImageDialog.SelectedItemListener listener = openCamera -> {
        if (openCamera) {
            if (MPermissionChecker.grantCameraAccess(this, ImageUtils.REQUEST_CAMERA))
                selectedPhotoPath = ImageUtils.intentCamera(this, ImageUtils.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
        } else {
            if (MPermissionChecker.grantGalleryAccess(this, ImageUtils.REQUEST_GALLERY))
                ImageUtils.intentGallery(this, ImageUtils.SELECT_IMAGE_FROM_GALLERY);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ImageUtils.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE || requestCode == ImageUtils.SELECT_IMAGE_FROM_GALLERY) {
                String imagePath = null;
                Uri imageUri = null;
                // get image path
                switch (requestCode) {
                    case ImageUtils.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE:
                        imagePath = selectedPhotoPath;
                        break;
                    case ImageUtils.SELECT_IMAGE_FROM_GALLERY:
                        try {
                            imageUri = intent.getData();
                            imagePath = GeneralUtils.getRealPathFromURI(this, imageUri);
                            Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);

                        } catch (Exception e) {
                            Crashlytics.log(Log.ERROR, TAG, "Caught Exception message : " + e.getMessage());
                            imagePath = GeneralUtils.getRealPathFromURI_API19(this, imageUri);
                            Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);
                        }
                        break;
                }
                if (imagePath == null) {
                    return;
                }
                GeneralUtils.fixImageOrientation(imagePath);
                Picasso.with(this).load(new File(imagePath)).resize(Constants.IMG_WIDTH*2, Constants.IMG_HEIGHT).centerCrop().into(layoutBackground);

                questionView.setHintTextColor(Color.parseColor("#FFFFFF"));
                questionView.setTextColor(Color.parseColor("#FFFFFF"));
                creatorNameView.setTextColor(Color.parseColor("#FFFFFF"));
                addQueryImageView.setImageResource(R.drawable.delete_image);
                isQuestionImageSelected = true;

                putQueryPref
                        .putString(Constants.CreateQuery.QUESTION_IMAGE_PATH, imagePath)
                        .apply();
            }
        }
    }

    @OnClick(R.id.layout_post_query_txtinp)
    void openSendTo() {
        if (questionView.getText().length() > 0) {
            String questionListString;
            putQueryPref
                    .putString(QUESTION_TEXT, questionView.getText().toString())
                    .putBoolean(ANONYMOUS, isAnonymous)
                    .putBoolean(QUESTION_IMAGE, isQuestionImageSelected)
                    .apply();

            switch (currentType) {
                case Constants.CreateQuery.TXTINP:
                    TxtInpQuery txtInpQuery = new TxtInpQuery(Constants.CreateQuery.TXTINP,
                            questionView.getText().toString(),
                            isQuestionImageSelected);

                    Gson gson = new Gson();
                    questionListString = gson.toJson(txtInpQuery);

                    putQueryPref
                            .putString(QUERY_TITLE, "TxtInpQuery")
                            .putString(TYPE, TXTINP)
                            .putString(QUESTION_LIST, questionListString)
                            .apply();
                    break;
                case Constants.CreateQuery.IMGOPT:
                    // TODO: 17.11.16 finish implementation here
//                    putQueryPref
//                        .putString(Constants.CreateQuery.QUERY_TITLE, "ImgOpt query title")
//                        .putString(Constants.CreateQuery.TYPE, Constants.CreateQuery.IMGOPT)
//                            .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_0, optionInfos[0].answerOptionImage)
//                            .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_1, optionInfos[1].answerOptionImage)
//                            .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_2, optionInfos[2].answerOptionImage)
//                            .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_3, optionInfos[3].answerOptionImage)

                    break;
                case Constants.CreateQuery.TXTOPT:
                    // TODO: 17.11.16 implement post here
                    break;
                default:
                    Crashlytics.logException(new Throwable("Should be never happened"));
            }
            GeneralUtils.route(this, QuerySendToActivity.class);
        } else {
            Toast.makeText(this, "Please type your question.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GeneralUtils.routeWithFinish(this, DashboardActivity.class);
    }
}
