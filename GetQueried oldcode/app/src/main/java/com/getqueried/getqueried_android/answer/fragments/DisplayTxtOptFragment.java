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

    private ProgressBar[] progressBars = new ProgressBar[Constants.MAX_OPTIONS];
    @Bind(R.id.progressBar0)
    public ProgressBar progressBar0;
    @Bind(R.id.progressBar1)
    public ProgressBar progressBar1;
    @Bind(R.id.progressBar2)
    public ProgressBar progressBar2;
    @Bind(R.id.progressBar3)
    public ProgressBar progressBar3;

    private TextView[] textViews = new TextView[Constants.MAX_OPTIONS];
    @Bind(R.id.textView_progress0)
    public TextView textView0;
    @Bind(R.id.textView_progress1)
    public TextView textView1;
    @Bind(R.id.textView_progress2)
    public TextView textView2;
    @Bind(R.id.textView_progress3)
    public TextView textView3;

    private TextView[] textOpts = new TextView[Constants.MAX_OPTIONS];
    @Bind(R.id.option1)
    public TextView textInfo0;
    @Bind(R.id.option2)
    public TextView textInfo1;
    @Bind(R.id.option3)
    public TextView textInfo2;
    @Bind(R.id.option4)
    public TextView textInfo3;

    private ArrayList<Integer> answers;
    private ArrayList<AnswerOptionItem> options;

    @OnClick({R.id.option1, R.id.option2, R.id.option3, R.id.option4})
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
            for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
                progressBars[i].setVisibility(View.GONE);
                textViews[i].setVisibility(View.GONE);
            }
        } else {
            for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
                textOpts[i].setBackgroundColor(getResources().getColor(R.color.fullyTransparent));
            }
        }

        // add options to views
        TextView currTextOptView;
        for (int i = 0; i < options.size(); i++) {
            currTextOptView = textOpts[i];
            currTextOptView.setText(options.get(i).answerOptionText);
        }
        for (int i = options.size(); i < Constants.MAX_OPTIONS; i++) {
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
        for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
            textOpts[i].setBackgroundColor(Color.parseColor("#D4D4D4"));
        }
    }

    private void fullViewsArrays() {
        progressBars[0] = progressBar0;
        progressBars[1] = progressBar1;
        progressBars[2] = progressBar2;
        progressBars[3] = progressBar3;
        textViews[0] = textView0;
        textViews[1] = textView1;
        textViews[2] = textView2;
        textViews[3] = textView3;
        textOpts[0] = textInfo0;
        textOpts[1] = textInfo1;
        textOpts[2] = textInfo2;
        textOpts[3] = textInfo3;
    }
}
