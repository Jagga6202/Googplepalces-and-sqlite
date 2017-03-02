package com.getqueried.getqueried_android.answer.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.AnswerOptionItem;
import com.getqueried.getqueried_android.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayTxtOptFragment extends Fragment {

    public static final String TAG = "DisplayTxtOptFragment";

    private boolean showingAnswers;

    //for answering to other users
    private int optionSelected = -1;
    public AnswerProvided callback;

    private ProgressBar[] progressBars = new ProgressBar[20];//Jagdeep 16 jan
    @Bind(R.id.progressBar0)
    public ProgressBar progressBar0;
    @Bind(R.id.progressBar1)
    public ProgressBar progressBar1;
    @Bind(R.id.progressBar2)
    public ProgressBar progressBar2;
    @Bind(R.id.progressBar3)
    public ProgressBar progressBar3;
    @Bind(R.id.progressBar4)
    public ProgressBar progressBar4;
    @Bind(R.id.progressBar5)
    public ProgressBar progressBar5;
    @Bind(R.id.progressBar6)
    public ProgressBar progressBar6;
    @Bind(R.id.progressBar7)
    public ProgressBar progressBar7;
    @Bind(R.id.progressBar8)
    public ProgressBar progressBar8;
    @Bind(R.id.progressBar9)
    public ProgressBar progressBar9;
    @Bind(R.id.progressBar10)
    public ProgressBar progressBar10;
    @Bind(R.id.progressBar11)
    public ProgressBar progressBar11;
    @Bind(R.id.progressBar12)
    public ProgressBar progressBar12;
    @Bind(R.id.progressBar13)
    public ProgressBar progressBar13;
    @Bind(R.id.progressBar14)
    public ProgressBar progressBar14;
    @Bind(R.id.progressBar15)
    public ProgressBar progressBar15;
    @Bind(R.id.progressBar16)
    public ProgressBar progressBar16;
    @Bind(R.id.progressBar17)
    public ProgressBar progressBar17;
    @Bind(R.id.progressBar18)
    public ProgressBar progressBar18;
    @Bind(R.id.progressBar19)
    public ProgressBar progressBar19;



    private TextView[] textViews = new TextView[20]; //Jagdeep 16 jan
    @Bind(R.id.textView_progress0)
    public TextView textView0;
    @Bind(R.id.textView_progress1)
    public TextView textView1;
    @Bind(R.id.textView_progress2)
    public TextView textView2;
    @Bind(R.id.textView_progress3)
    public TextView textView3;
    @Bind(R.id.textView_progress4)
    public TextView textView4;
    @Bind(R.id.textView_progress5)
    public TextView textView5;
    @Bind(R.id.textView_progress6)
    public TextView textView6;
    @Bind(R.id.textView_progress7)
    public TextView textView7;
    @Bind(R.id.textView_progress8)
    public TextView textView8;
    @Bind(R.id.textView_progress9)
    public TextView textView9;
    @Bind(R.id.textView_progress10)
    public TextView textView10;
    @Bind(R.id.textView_progress11)
    public TextView textView11;
    @Bind(R.id.textView_progress12)
    public TextView textView12;
    @Bind(R.id.textView_progress13)
    public TextView textView13;
    @Bind(R.id.textView_progress14)
    public TextView textView14;
    @Bind(R.id.textView_progress15)
    public TextView textView15;
    @Bind(R.id.textView_progress16)
    public TextView textView16;
    @Bind(R.id.textView_progress17)
    public TextView textView17;
    @Bind(R.id.textView_progress18)
    public TextView textView18;
    @Bind(R.id.textView_progress19)
    public TextView textView19;

    private TextView[] textOpts = new TextView[20]; //Jagdeep 16 jan
    @Bind(R.id.option1)
    public TextView textInfo0;
    @Bind(R.id.option2)
    public TextView textInfo1;
    @Bind(R.id.option3)
    public TextView textInfo2;
    @Bind(R.id.option4)
    public TextView textInfo3;
    @Bind(R.id.option5)
    public TextView textInfo4;
    @Bind(R.id.option6)
    public TextView textInfo5;
    @Bind(R.id.option7)
    public TextView textInfo6;
    @Bind(R.id.option8)
    public TextView textInfo7;
    @Bind(R.id.option9)
    public TextView textInfo8;
    @Bind(R.id.option10)
    public TextView textInfo9;
    @Bind(R.id.option11)
    public TextView textInfo10;
    @Bind(R.id.option12)
    public TextView textInfo11;
    @Bind(R.id.option13)
    public TextView textInfo12;
    @Bind(R.id.option14)
    public TextView textInfo13;
    @Bind(R.id.option15)
    public TextView textInfo14;
    @Bind(R.id.option16)
    public TextView textInfo15;
    @Bind(R.id.option17)
    public TextView textInfo16;
    @Bind(R.id.option18)
    public TextView textInfo17;
    @Bind(R.id.option19)
    public TextView textInfo18;
    @Bind(R.id.option20)
    public TextView textInfo19;

    private ArrayList<Integer> answers;
    private ArrayList<AnswerOptionItem> options;

    @OnClick({R.id.option1, R.id.option2, R.id.option3, R.id.option4,R.id.option5, R.id.option6, R.id.option7, R.id.option8,
            R.id.option9, R.id.option10, R.id.option11, R.id.option12,R.id.option13, R.id.option14, R.id.option15, R.id.option16,
            R.id.option17, R.id.option18, R.id.option19, R.id.option20})
    void selectAnswer(View view) {
        if (!showingAnswers) {
            int newOptionSelected = new Integer(view.getTag().toString()) - 1;
            if (newOptionSelected != optionSelected) {
                updateHighLight(newOptionSelected);
                optionSelected = newOptionSelected;
                callback.answerStateChanged(true, newOptionSelected, "");
            } else {
                unHighLightAll();
                optionSelected = -1;
                callback.answerStateChanged(false, -1, null);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        showingAnswers = bundle.getBoolean(Constants.Data.ANSWERS_PROVIDED);
        options = (ArrayList<AnswerOptionItem>) bundle.getSerializable(Constants.CreateQuery.OPTIONS);
        if (showingAnswers) {
            answers = (ArrayList<Integer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
        }

    }

    public static DisplayTxtOptFragment getInstance(AnswerProvided callback) {
        DisplayTxtOptFragment fragment = new DisplayTxtOptFragment();
        fragment.callback = callback;
        return fragment;
    }

    public DisplayTxtOptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_txt_opt, container, false);
        ButterKnife.bind(this, view);
        fullViewsArrays();

        // prepare views
        if (!showingAnswers) {
            for (int i = 0; i < 20; i++) {//Jagdeep 16 Jan
                progressBars[i].setVisibility(View.GONE);
                textViews[i].setVisibility(View.GONE);
            }
        } else {
            for (int i = 0; i < 20; i++) {//Jagdeep 16 Jan
                textOpts[i].setBackgroundColor(getResources().getColor(R.color.fullyTransparent));
            }
        }

        // add options to views
        TextView currTextOptView;
        for (int i = 0; i < options.size(); i++) {
            currTextOptView = textOpts[i];
            currTextOptView.setText(options.get(i).answerOptionText);
        }
        for (int i = options.size(); i < 20; i++) {//Jagdeep 16 Jan
            textOpts[i].setVisibility(View.GONE);
            progressBars[i].setVisibility(View.GONE);
            textViews[i].setVisibility(View.GONE);
        }

        // add answers to views if needed
        if (showingAnswers) {
            int answersAmount = 0;
            for (Integer answerVoted : answers) {
                answersAmount += answerVoted;
            }

            if (answersAmount == 0) {
                Crashlytics.log(Log.ERROR, TAG, "Answers amount is 0");
                return view;
            }
            ProgressBar currProgressBur;
            TextView currPercView;
            for (int i = 0; i < options.size(); i++) {
                currProgressBur = progressBars[i];
                currPercView = textViews[i];
                currProgressBur.setMax(100);
                currProgressBur.setProgress(currProgressBur.getMax() * answers.get(i) / answersAmount);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    currProgressBur.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                } else {
                    currProgressBur.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue),
                            PorterDuff.Mode.SRC_IN);
                }
                currPercView.setText(currProgressBur.getProgress() + "%");
            }
        }
        return view;
    }

    // TODO: 06.11.16 colors should be fetched from the R.color

    private void updateHighLight(int optionToHighLight) {
        unHighLightAll();
        textOpts[optionToHighLight].setBackgroundColor(Color.parseColor("#3786A9"));
    }

    private void unHighLightAll() {
        for (int i = 0; i < 20; i++) {//Jagdeep 16 Jan
            textOpts[i].setBackgroundColor(Color.parseColor("#D4D4D4"));
        }
    }

    private void fullViewsArrays() {
        progressBars[0] = progressBar0;
        progressBars[1] = progressBar1;
        progressBars[2] = progressBar2;
        progressBars[3] = progressBar3;
        progressBars[4] = progressBar4;
        progressBars[5] = progressBar5;
        progressBars[6] = progressBar6;
        progressBars[7] = progressBar7;
        progressBars[8] = progressBar8;
        progressBars[9] = progressBar9;
        progressBars[10] = progressBar10;
        progressBars[11] = progressBar11;
        progressBars[12] = progressBar12;
        progressBars[13] = progressBar13;
        progressBars[14] = progressBar14;
        progressBars[15] = progressBar15;
        progressBars[16] = progressBar16;
        progressBars[17] = progressBar17;
        progressBars[18] = progressBar18;
        progressBars[19] = progressBar19;
        textViews[0] = textView0;
        textViews[1] = textView1;
        textViews[2] = textView2;
        textViews[3] = textView3;
        textViews[4] = textView4;
        textViews[5] = textView5;
        textViews[6] = textView6;
        textViews[7] = textView7;
        textViews[8] = textView8;
        textViews[9] = textView9;
        textViews[10] = textView10;
        textViews[11] = textView11;
        textViews[12] = textView12;
        textViews[13] = textView13;
        textViews[14] = textView14;
        textViews[15] = textView15;
        textViews[16] = textView16;
        textViews[17] = textView17;
        textViews[18] = textView18;
        textViews[19] = textView19;
        textOpts[0] = textInfo0;
        textOpts[1] = textInfo1;
        textOpts[2] = textInfo2;
        textOpts[3] = textInfo3;
        textOpts[4] = textInfo4;
        textOpts[5] = textInfo5;
        textOpts[6] = textInfo6;
        textOpts[7] = textInfo7;
        textOpts[8] = textInfo8;
        textOpts[9] = textInfo9;
        textOpts[10] = textInfo10;
        textOpts[11] = textInfo11;
        textOpts[12] = textInfo12;
        textOpts[13] = textInfo13;
        textOpts[14] = textInfo14;
        textOpts[15] = textInfo15;
        textOpts[16] = textInfo16;
        textOpts[17] = textInfo17;
        textOpts[18] = textInfo18;
        textOpts[19] = textInfo19;
    }
}
