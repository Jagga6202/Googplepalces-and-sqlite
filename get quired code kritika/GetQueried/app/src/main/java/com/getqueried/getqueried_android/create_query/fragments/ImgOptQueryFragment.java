package com.getqueried.getqueried_android.create_query.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.cameraAPI.CaptureActivityCAPI1;
import com.getqueried.getqueried_android.create_query.AddImageDescription;
import com.getqueried.getqueried_android.create_query.AskQuestionActivity;
import com.getqueried.getqueried_android.create_query.QuerySendToActivity;
import com.getqueried.getqueried_android.model.AnswerOptionItem;
import com.getqueried.getqueried_android.model.ImgOptQuestionList;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_LIST;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.CHOICE_MAX;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.CHOICE_MIN;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.HAS_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.IMGOPT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.SLIDER_TYPE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TYPE;


public class ImgOptQueryFragment extends Fragment {

    private static final String TAG = "ImgOptQueryFragment";

    private static final int CAMERA_PERMISSION_REQUEST = 2;
    private static final int CHOOSE_PICTURES = 12;

    private static final int EDIT_DESCRIPTION = 1113;
    private static final Integer MIN_CHOICE = 1;

    private AnswerOptionItem[] optionInfos = new AnswerOptionItem[Constants.MAX_OPTIONS];

    private ImageView[] options = new ImageView[12];
    @Bind(R.id.imageView_option1)
    ImageView option1ImageView;

    @Bind(R.id.imageView_option2)
    ImageView option2ImageView;

    @Bind(R.id.imageView_option3)
    ImageView option3ImageView;

    @Bind(R.id.imageView_option4)
    ImageView option4ImageView;

   @Bind(R.id.imageView_option5)
    ImageView option5ImageView;

    @Bind(R.id.imageView_option6)
    ImageView option6ImageView;

    @Bind(R.id.imageView_option7)
    ImageView option7ImageView;

    @Bind(R.id.imageView_option8)
    ImageView option8ImageView;

    @Bind(R.id.imageView_option9)
    ImageView option9ImageView;

    @Bind(R.id.imageView_option10)
    ImageView option10ImageView;

    @Bind(R.id.imageView_option11)
    ImageView option11ImageView;

    @Bind(R.id.imageView_option12)
    ImageView option12ImageView;

    private TextView[] textViews = new TextView[Constants.MAX_OPTIONS];
    @Bind(R.id.textInfo1)
    public TextView textView1;
    @Bind(R.id.textInfo2)
    public TextView textView2;
    @Bind(R.id.textInfo3)
    public TextView textView3;
    @Bind(R.id.textInfo4)
    public TextView textView4;
    @Bind(R.id.textInfo5)
    public TextView textView5;
    @Bind(R.id.textInfo6)
    public TextView textView6;
    @Bind(R.id.textInfo7)
    public TextView textView7;
    @Bind(R.id.textInfo8)
    public TextView textView8;
    @Bind(R.id.textInfo9)
    public TextView textView9;
    @Bind(R.id.textInfo10)
    public TextView textView10;
    @Bind(R.id.textInfo11)
    public TextView textView11;
    @Bind(R.id.textInfo12)
    public TextView textView12;


    private int optionSelected;

    @OnClick({R.id.imageView_option1, R.id.imageView_option2, R.id.imageView_option3, R.id.imageView_option4,
            R.id.imageView_option5, R.id.imageView_option6, R.id.imageView_option7, R.id.imageView_option8,
            R.id.imageView_option9, R.id.imageView_option10, R.id.imageView_option11, R.id.imageView_option12})
    public void selectImage(View view) {
        optionSelected = Integer.valueOf(view.getTag().toString()) - 1;
        Log.e("tesdttftgftg",String.valueOf(optionSelected));
        if (optionInfos[optionSelected].answerOptionImage.isEmpty()) {
            // add image
            if (checkCameraPermission(getActivity())) {
                startActivityForResult(new Intent(getActivity(), CaptureActivityCAPI1.class), CHOOSE_PICTURES);
            }
        } else {
            // edit image
            Intent intent = new Intent(getActivity(), AddImageDescription.class);
            intent.putExtra(Constants.CreateQuery.IMAGE_PATH, optionInfos[optionSelected].answerOptionImage);
            intent.putExtra(Constants.CreateQuery.IMAGE_DESCRIPTION, optionInfos[optionSelected].answerOptionText);
            startActivityForResult(intent, EDIT_DESCRIPTION);
        }
    }

    private boolean checkCameraPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), CaptureActivityCAPI1.class));
                } else {
                    Toast.makeText(getActivity(), "Permission is denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURES:
                    String[] optionsPaths = intent.getExtras().getStringArray("options");
                    if (optionsPaths == null) {
                        Crashlytics.log(Log.ERROR, TAG, "option paths list is null");
                        return;
                    }
                    for (int i = 0; i < optionsPaths.length; i++) {
                        appendImage(optionsPaths[i]);
                    }
                    break;
                case EDIT_DESCRIPTION:
                    Bundle bundle = intent.getExtras();
                    boolean isImageDeleted = bundle.getBoolean(Constants.CreateQuery.IS_IMAGE_DELETED, false);
                    if (isImageDeleted) {
                        deleteImage(optionSelected);
                    } else {
                        boolean isImageRotated = bundle.getBoolean(Constants.CreateQuery.IS_IMAGE_ROTATED, false);
                        if (isImageRotated) {
                            Picasso.with(getActivity()).load(new File(optionInfos[optionSelected].answerOptionImage)).resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).centerCrop().into(options[optionSelected]);
                        }
                        String description = bundle.getString(Constants.CreateQuery.IMAGE_DESCRIPTION, "");
                        Crashlytics.log(Log.DEBUG, TAG, "image rotated: " + isImageRotated + ", description : " + description);
                        optionInfos[optionSelected].answerOptionText = description;
                        textViews[optionSelected].setText(description);
                    }
                    break;

            }
        }
    }

    private void appendImage(String imagePath) {
        for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
            if (optionInfos[i].answerOptionImage.isEmpty()) {
                optionInfos[i].answerOptionImage = imagePath;
                Picasso.with(getActivity()).load(new File(optionInfos[i].answerOptionImage)).resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).centerCrop().into(options[i]);
                return;
            }
        }
    }

    private void deleteImage(int number) {
        if (optionInfos[number].answerOptionImage.isEmpty()) {
            Crashlytics.logException(new Throwable("image path to option is empty, nothing to delete"));
        }
        for (int i = number; i < Constants.MAX_OPTIONS - 1; i++) {
            optionInfos[i] = optionInfos[i + 1];
            Picasso.with(getActivity()).load(new File(optionInfos[i].answerOptionImage)).placeholder(getResources().getDrawable(R.drawable.images_query)).resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).centerCrop().into(options[i]);
            textViews[i].setText(optionInfos[i].answerOptionText);
        }
        // last option clears in any case
        optionInfos[Constants.MAX_OPTIONS - 1] = new AnswerOptionItem();
        options[Constants.MAX_OPTIONS - 1].setImageDrawable(getResources().getDrawable(R.drawable.images_query));
        textViews[Constants.MAX_OPTIONS - 1].setText("");
    }

    @OnClick(R.id.btn_create_question)
    void openSendTo() {
        if (questionView.getText().length() > 0) {
            JSONArray jsonArrayAnswerOption = new JSONArray();

            int choiceMax = 0;
            for (int i = 0; i < Constants.MAX_OPTIONS && !optionInfos[i].answerOptionImage.isEmpty(); i++) {
                try {
                    JSONObject jsonObjectAnswer = new JSONObject();
                    jsonObjectAnswer.put(ANSWER_OPTION_TEXT, optionInfos[i].answerOptionText);
                    jsonObjectAnswer.put(ANSWER_OPTION_IMAGE, true);
                    jsonArrayAnswerOption.put(jsonObjectAnswer);
                    choiceMax = i + 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ImgOptQuestionList query = new ImgOptQuestionList(
                    questionView.getText().toString(),
                    jsonArrayAnswerOption,
                    AskQuestionActivity.isQuestionImageSelected,
                    MIN_CHOICE, choiceMax, IMGOPT);

            JSONObject jsonObjectQuestion = new JSONObject();
            JSONArray jsonArrayQuestionList = new JSONArray();
            try {
                jsonObjectQuestion.put(TYPE, query.getType());
                jsonObjectQuestion.put(QUESTION_TEXT, query.getQuestionText());
                jsonObjectQuestion.put(HAS_IMAGE, query.getQuestionImage());
                jsonObjectQuestion.put(SLIDER_TYPE, "");
                jsonObjectQuestion.put(CHOICE_MIN, query.getChoiceMin());
                jsonObjectQuestion.put(CHOICE_MAX, query.getChoiceMax());
                jsonObjectQuestion.put(ANSWER_OPTION_LIST, jsonArrayAnswerOption);
                jsonArrayQuestionList.put(jsonObjectQuestion);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPrefUtils.putQueryPref(getActivity())
                    .putString(Constants.CreateQuery.QUERY_TITLE, "ImgOpt query title")
                    .putString(Constants.CreateQuery.TYPE, Constants.CreateQuery.IMGOPT)
                    .putString(Constants.CreateQuery.QUESTION_LIST, jsonArrayQuestionList.toString())
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_0, optionInfos[0].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_1, optionInfos[1].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_2, optionInfos[2].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_3, optionInfos[3].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_4, optionInfos[4].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_5, optionInfos[5].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_6, optionInfos[6].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_7, optionInfos[7].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_8, optionInfos[8].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_9, optionInfos[9].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_10, optionInfos[10].answerOptionImage)
                    .putString(Constants.CreateQuery.ANSWER_OPTION_IMAGE_11, optionInfos[11].answerOptionImage)
                    .apply();

            GeneralUtils.route((AppCompatActivity) getActivity(), QuerySendToActivity.class);
        } else
            Toast.makeText(getActivity(), "Please type your question.", Toast.LENGTH_SHORT).show();
    }

    public ImgOptQueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_query, container, false);
    }

    private EditText questionView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fillTheOptionsList();
        questionView = (EditText) getActivity().findViewById(R.id.editText_question);
        LinearLayout postTxtInpQueryLayout = (LinearLayout) getActivity().findViewById(R.id.layout_post_query_txtinp);
        postTxtInpQueryLayout.setVisibility(View.GONE);
        if (checkCameraPermission(getActivity())) {
            startActivityForResult(new Intent(getActivity(), CaptureActivityCAPI1.class), CHOOSE_PICTURES);
        }
    }

    private void fillTheOptionsList() {
        textViews[0] = textView1;
        textViews[1] = textView2;
        textViews[2] = textView3;
        textViews[3] = textView4;
        textViews[4] = textView5;
        textViews[5] = textView6;
        textViews[6] = textView7;
        textViews[7] = textView8;
        textViews[8] = textView9;
        textViews[9] = textView10;
        textViews[10] = textView11;
        textViews[11] = textView12;
        options[0] = option1ImageView;
        options[1] = option2ImageView;
        options[2] = option3ImageView;
        options[3] = option4ImageView;
        options[4] = option5ImageView;
        options[5] = option6ImageView;
        options[6] = option7ImageView;
        options[7] = option8ImageView;
        options[8] = option9ImageView;
        options[9] = option10ImageView;
        options[10] = option11ImageView;
        options[11] = option12ImageView;
        for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
            optionInfos[i] = new AnswerOptionItem();
        }
    }
}
