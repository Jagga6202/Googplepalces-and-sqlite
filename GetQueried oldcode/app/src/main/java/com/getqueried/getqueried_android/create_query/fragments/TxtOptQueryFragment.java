package com.getqueried.getqueried_android.create_query.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.create_query.QuerySendToActivity;
import com.getqueried.getqueried_android.model.AnswerOptionList;
import com.getqueried.getqueried_android.model.CreateQuery;
import com.getqueried.getqueried_android.model.TxtOptQuery;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANONYMOUS;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_LIST;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.ANSWER_OPTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.CHOICE_MAX;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.CHOICE_MIN;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.HAS_IMAGE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUERY_TITLE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_LIST;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.QUESTION_TEXT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.SLIDER_TYPE;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TARGET;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TXTOPT;
import static com.getqueried.getqueried_android.utils.Constants.CreateQuery.TYPE;

public class TxtOptQueryFragment extends Fragment {

    private static final Integer MIN_CHOICE = 1;
    private static final Integer MAX_CHOICE = 4;
    String questionText;
    CreateQuery createQuery;

    public TxtOptQueryFragment() {
    }

    private static final String TAG = "TxtOptQueryFragment";
    List<AnswerOptionList> answerOptionList = new ArrayList<AnswerOptionList>();

    @Bind(R.id.option1)
    EditText optionView1;

    @Bind(R.id.option2)
    EditText optionView2;

    @Bind(R.id.option3)
    EditText optionView3;

    @Bind(R.id.option4)
    EditText optionView4;

    @OnClick(R.id.btn_create_question)
    void askQuestion() {
        Crashlytics.log(Log.DEBUG, TAG, "askQuestion called !!!");

        questionText = questionView.getText().toString();
        Crashlytics.log(Log.DEBUG, TAG, "Question text : " + questionText);
        if (questionText.length() > 0) {
            answerOptionList.add(new AnswerOptionList(false, optionView1.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView2.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView3.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView4.getText().toString()));

            JSONArray jsonArrayAnswerOption = new JSONArray();
            int maxChoice = 1;
            for (int i = 0; i < answerOptionList.size() && !answerOptionList.get(i).answerOptionText.isEmpty(); i++) {
                Crashlytics.log(Log.DEBUG, TAG, "Answer option list position " + i + ": " + answerOptionList.get(i).answerOptionText);
                maxChoice = i + 1;
                try {
                    JSONObject jsonObjectAnswer = new JSONObject();
                    jsonObjectAnswer.put(ANSWER_OPTION_IMAGE, answerOptionList.get(i).answerOptionImage);
                    jsonObjectAnswer.put(ANSWER_OPTION_TEXT, answerOptionList.get(i).answerOptionText);
                    jsonArrayAnswerOption.put(jsonObjectAnswer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String imagePath = SharedPrefUtils.getQueryPref(getActivity()).getString(Constants.CreateQuery.QUESTION_IMAGE_PATH, "");
            TxtOptQuery txtOptQuery = new TxtOptQuery(TXTOPT, questionText,
                    imagePath.length() > 0, "", MIN_CHOICE, maxChoice, answerOptionList);

            JSONObject jsonObjectQuestion = new JSONObject();
            JSONArray jsonArrayQuestionList = new JSONArray();
            try {
                jsonObjectQuestion.put(TYPE, txtOptQuery.type);
                jsonObjectQuestion.put(QUESTION_TEXT, txtOptQuery.questionText);
                jsonObjectQuestion.put(HAS_IMAGE, txtOptQuery.questionImage);
                jsonObjectQuestion.put(SLIDER_TYPE, txtOptQuery.sliderType);
                jsonObjectQuestion.put(CHOICE_MIN, txtOptQuery.choiceMin);
                jsonObjectQuestion.put(CHOICE_MAX, txtOptQuery.choiceMax);
                jsonObjectQuestion.put(ANSWER_OPTION_LIST, jsonArrayAnswerOption);
                jsonArrayQuestionList.put(jsonObjectQuestion);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Crashlytics.log(Log.DEBUG, TAG, "Json object : " + jsonArrayQuestionList);
            ArrayList<TxtOptQuery> questionList = new ArrayList<>(1);
            questionList.add(txtOptQuery);
            createQuery = new CreateQuery("followers 1", false, "followers", questionList);

            JSONObject params = new JSONObject();
            try {
                params.put(QUERY_TITLE, createQuery.queryTitle);
                params.put(ANONYMOUS, createQuery.anonymous);
                params.put(TARGET, createQuery.target);
                params.put(QUESTION_LIST, jsonArrayQuestionList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Crashlytics.log(Log.DEBUG, TAG, "Payload : " + String.valueOf(params));

            SharedPrefUtils.putQueryPref(getActivity().getBaseContext())
                    .putString(QUERY_TITLE, "Txtopt query")
                    .putString(TYPE, TXTOPT)
                    .putString(ANSWER_OPTION_LIST, String.valueOf(params))
                    .putString(QUESTION_LIST, String.valueOf(jsonArrayQuestionList))
                    .putString(QUESTION_TEXT, questionText)
                    .apply();

            GeneralUtils.route((AppCompatActivity) getActivity(), QuerySendToActivity.class);
        } else
            Toast.makeText(getActivity(), "Please type your question.", Toast.LENGTH_SHORT).show();
    }


    @Bind(R.id.imageButton_add_answer_option)
    ImageButton addAnswerOptionView;

    @OnClick(R.id.imageButton_add_answer_option)
    void addAnswerOption() {
        if (answerOption2.getVisibility() == View.GONE)
            answerOption2.setVisibility(View.VISIBLE);
        else if (answerOption3.getVisibility() == View.GONE)
            answerOption3.setVisibility(View.VISIBLE);
        else {
            answerOption4.setVisibility(View.VISIBLE);
            addAnswerOptionView.setVisibility(View.GONE);
        }
    }

    @Bind(R.id.answer_option2)
    LinearLayout answerOption2;

    @Bind(R.id.answer_option3)
    LinearLayout answerOption3;

    @Bind(R.id.answer_option4)
    LinearLayout answerOption4;

    @OnClick(R.id.remove_option2)
    void removeAnswerOption2() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
        } else if (answerOption3.getVisibility() == View.VISIBLE) {
            answerOption3.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
        } else {
            answerOption2.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option3)
    void removeAnswerOption3() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView3.setText(optionView4.getText().toString());
        } else {
            answerOption3.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option4)
    void removeAnswerOption4() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        answerOption4.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_type, container, false);
    }

    private EditText questionView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        answerOptionList.clear();
        questionView = (EditText) getActivity().findViewById(R.id.editText_question);
        LinearLayout postTxtInpQueryLayout = (LinearLayout) getActivity().findViewById(R.id.layout_post_query_txtinp);
        postTxtInpQueryLayout.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        answerOptionList.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        answerOptionList.clear();
    }
}
