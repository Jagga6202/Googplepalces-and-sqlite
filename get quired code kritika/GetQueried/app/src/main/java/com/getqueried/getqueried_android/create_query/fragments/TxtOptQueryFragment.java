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

    @Bind(R.id.option5)
    EditText optionView5;

    @Bind(R.id.option6)
    EditText optionView6;

    @Bind(R.id.option7)
    EditText optionView7;

    @Bind(R.id.option8)
    EditText optionView8;

    @Bind(R.id.option9)
    EditText optionView9;

    @Bind(R.id.option10)
    EditText optionView10;

    @Bind(R.id.option11)
    EditText optionView11;

    @Bind(R.id.option12)
    EditText optionView12;

    @Bind(R.id.option13)
    EditText optionView13;

    @Bind(R.id.option14)
    EditText optionView14;

    @Bind(R.id.option15)
    EditText optionView15;

    @Bind(R.id.option16)
    EditText optionView16;

    @Bind(R.id.option17)
    EditText optionView17;

    @Bind(R.id.option18)
    EditText optionView18;

    @Bind(R.id.option19)
    EditText optionView19;

    @Bind(R.id.option20)
    EditText optionView20;


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
            answerOptionList.add(new AnswerOptionList(false, optionView5.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView6.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView7.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView8.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView9.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView10.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView11.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView12.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView13.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView14.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView15.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView16.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView17.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView18.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView19.getText().toString()));
            answerOptionList.add(new AnswerOptionList(false, optionView20.getText().toString()));

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
        else if (answerOption4.getVisibility() == View.GONE)
            answerOption4.setVisibility(View.VISIBLE);
        else if (answerOption5.getVisibility() == View.GONE)
            answerOption5.setVisibility(View.VISIBLE);
        else if (answerOption6.getVisibility() == View.GONE)
            answerOption6.setVisibility(View.VISIBLE);
        else if (answerOption7.getVisibility() == View.GONE)
            answerOption7.setVisibility(View.VISIBLE);
        else if (answerOption8.getVisibility() == View.GONE)
            answerOption8.setVisibility(View.VISIBLE);
        else if (answerOption9.getVisibility() == View.GONE)
            answerOption9.setVisibility(View.VISIBLE);
        else if (answerOption10.getVisibility() == View.GONE)
            answerOption10.setVisibility(View.VISIBLE);
        else if (answerOption11.getVisibility() == View.GONE)
            answerOption11.setVisibility(View.VISIBLE);
        else if (answerOption12.getVisibility() == View.GONE)
            answerOption12.setVisibility(View.VISIBLE);
        else if (answerOption13.getVisibility() == View.GONE)
            answerOption13.setVisibility(View.VISIBLE);
        else if (answerOption14.getVisibility() == View.GONE)
            answerOption14.setVisibility(View.VISIBLE);
        else if (answerOption15.getVisibility() == View.GONE)
            answerOption15.setVisibility(View.VISIBLE);
        else if (answerOption16.getVisibility() == View.GONE)
            answerOption16.setVisibility(View.VISIBLE);
        else if (answerOption17.getVisibility() == View.GONE)
            answerOption17.setVisibility(View.VISIBLE);
        else if (answerOption18.getVisibility() == View.GONE)
            answerOption18.setVisibility(View.VISIBLE);
        else if (answerOption19.getVisibility() == View.GONE)
            answerOption19.setVisibility(View.VISIBLE);
        else  {
            answerOption20.setVisibility(View.VISIBLE);
            addAnswerOptionView.setVisibility(View.GONE);
        }
        //Jagdeep changes 14
    }

    @Bind(R.id.answer_option2)
    LinearLayout answerOption2;

    @Bind(R.id.answer_option3)
    LinearLayout answerOption3;

    @Bind(R.id.answer_option4)
    LinearLayout answerOption4;

    @Bind(R.id.answer_option5)
    LinearLayout answerOption5;

    @Bind(R.id.answer_option6)
    LinearLayout answerOption6;

    @Bind(R.id.answer_option7)
    LinearLayout answerOption7;

    @Bind(R.id.answer_option8)
    LinearLayout answerOption8;

    @Bind(R.id.answer_option9)
    LinearLayout answerOption9;

    @Bind(R.id.answer_option10)
    LinearLayout answerOption10;

    @Bind(R.id.answer_option11)
    LinearLayout answerOption11;

    @Bind(R.id.answer_option12)
    LinearLayout answerOption12;

    @Bind(R.id.answer_option13)
    LinearLayout answerOption13;

    @Bind(R.id.answer_option14)
    LinearLayout answerOption14;

    @Bind(R.id.answer_option15)
    LinearLayout answerOption15;

    @Bind(R.id.answer_option16)
    LinearLayout answerOption16;

    @Bind(R.id.answer_option17)
    LinearLayout answerOption17;

    @Bind(R.id.answer_option18)
    LinearLayout answerOption18;

    @Bind(R.id.answer_option19)
    LinearLayout answerOption19;

    @Bind(R.id.answer_option20)
    LinearLayout answerOption20;


    @OnClick(R.id.remove_option2)
    void removeAnswerOption2() {
       /* addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
        } else if (answerOption3.getVisibility() == View.VISIBLE) {
            answerOption3.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
        } else {
            answerOption2.setVisibility(View.GONE);
        }*/

        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
       else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
       else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
       else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
       else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
       else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
       else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }
        else if (answerOption7.getVisibility() == View.VISIBLE) {
            answerOption7.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
        }
        else if (answerOption6.getVisibility() == View.VISIBLE) {
            answerOption6.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
        }
        else if (answerOption5.getVisibility() == View.VISIBLE) {
            answerOption5.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
        }
        else if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
        }
        else if (answerOption3.getVisibility() == View.VISIBLE) {
            answerOption3.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
        }
        else {
            answerOption2.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option3)
    void removeAnswerOption3() {
      /*  addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView3.setText(optionView4.getText().toString());
        } else {
            answerOption3.setVisibility(View.GONE);
        }*/
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }
        else if (answerOption7.getVisibility() == View.VISIBLE) {
            answerOption7.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
        }
        else if (answerOption6.getVisibility() == View.VISIBLE) {
            answerOption6.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
        }
        else if (answerOption5.getVisibility() == View.VISIBLE) {
            answerOption5.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
        }
        else if (answerOption4.getVisibility() == View.VISIBLE) {
            answerOption4.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
        }

        else {
            answerOption3.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option4)
    void removeAnswerOption4() {
        /*addAnswerOptionView.setVisibility(View.VISIBLE);
        answerOption4.setVisibility(View.GONE);*/
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }
        else if (answerOption7.getVisibility() == View.VISIBLE) {
            answerOption7.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
        }
        else if (answerOption6.getVisibility() == View.VISIBLE) {
            answerOption6.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
        }
        else if (answerOption5.getVisibility() == View.VISIBLE) {
            answerOption5.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
        }

        else {
            answerOption4.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option5)
    void removeAnswerOption5() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }
        else if (answerOption7.getVisibility() == View.VISIBLE) {
            answerOption7.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
        }
        else if (answerOption6.getVisibility() == View.VISIBLE) {
            answerOption6.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
        }

        else {
            answerOption5.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option6)
    void removeAnswerOption6() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }
        else if (answerOption7.getVisibility() == View.VISIBLE) {
            answerOption7.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
        }

        else {
            answerOption6.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option7)
    void removeAnswerOption7() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }
        else if (answerOption8.getVisibility() == View.VISIBLE) {
            answerOption8.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
        }

        else {
            answerOption7.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option8)
    void removeAnswerOption8() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }
        else if (answerOption9.getVisibility() == View.VISIBLE) {
            answerOption9.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
        }

        else {
            answerOption8.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option9)
    void removeAnswerOption9() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        } else if (answerOption10.getVisibility() == View.VISIBLE) {
            answerOption10.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());

        }

        else {
            answerOption9.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option10)
    void removeAnswerOption10() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        } else if (answerOption11.getVisibility() == View.VISIBLE) {
            answerOption11.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());

        }
        else {
            answerOption10.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option11)
    void removeAnswerOption11() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }
        else if (answerOption12.getVisibility() == View.VISIBLE) {
            answerOption12.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
        }
        else {
            answerOption11.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option12)
    void removeAnswerOption12() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }
        else  if (answerOption13.getVisibility() == View.VISIBLE) {
            answerOption13.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
        }

        else {
            answerOption12.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.remove_option13)
    void removeAnswerOption13() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }
        else if (answerOption14.getVisibility() == View.VISIBLE) {
            answerOption14.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
        }

        else {
            answerOption13.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option14)
    void removeAnswerOption14() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }
        else if (answerOption15.getVisibility() == View.VISIBLE) {
            answerOption15.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
        }

        else {
            answerOption14.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option15)
    void removeAnswerOption15() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }
        else if (answerOption16.getVisibility() == View.VISIBLE) {
            answerOption16.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
        }

        else {
            answerOption15.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option16)
    void removeAnswerOption16() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }
        else if (answerOption17.getVisibility() == View.VISIBLE) {
            answerOption17.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
        }

        else {
            answerOption16.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option17)
    void removeAnswerOption17() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }

        else {
            answerOption17.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option18)
    void removeAnswerOption18() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }
        else if (answerOption19.getVisibility() == View.VISIBLE) {
            answerOption19.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
        }
        else if (answerOption18.getVisibility() == View.VISIBLE) {
            answerOption18.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
        }

        else {
            answerOption18.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option19)
    void removeAnswerOption19() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        if (answerOption20.getVisibility() == View.VISIBLE) {
            answerOption20.setVisibility(View.GONE);
            optionView2.setText(optionView3.getText().toString());
            optionView3.setText(optionView4.getText().toString());
            optionView4.setText(optionView5.getText().toString());
            optionView5.setText(optionView6.getText().toString());
            optionView6.setText(optionView7.getText().toString());
            optionView7.setText(optionView8.getText().toString());
            optionView8.setText(optionView9.getText().toString());
            optionView9.setText(optionView10.getText().toString());
            optionView10.setText(optionView11.getText().toString());
            optionView11.setText(optionView12.getText().toString());
            optionView12.setText(optionView13.getText().toString());
            optionView13.setText(optionView14.getText().toString());
            optionView14.setText(optionView15.getText().toString());
            optionView15.setText(optionView16.getText().toString());
            optionView16.setText(optionView17.getText().toString());
            optionView17.setText(optionView18.getText().toString());
            optionView18.setText(optionView19.getText().toString());
            optionView19.setText(optionView20.getText().toString());
        }

        else {
            answerOption19.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.remove_option20)
    void removeAnswerOption20() {
        addAnswerOptionView.setVisibility(View.VISIBLE);
        answerOption20.setVisibility(View.GONE);
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
