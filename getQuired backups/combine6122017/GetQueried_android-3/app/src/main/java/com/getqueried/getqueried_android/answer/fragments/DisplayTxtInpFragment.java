package com.getqueried.getqueried_android.answer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTxtInpFragment extends Fragment {

    public static final String TAG = "DisplayTxtInpFragment";
    private ArrayList<TxtInpAnswer> answerList;

    @Bind(R.id.textView_query_answers)
    TextView queryAnswersView;

    public static DisplayTxtInpFragment getInstance() {
        return new DisplayTxtInpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        answerList = (ArrayList<TxtInpAnswer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Crashlytics.log(Log.DEBUG, TAG, "Query Answers : " + answerList);
        View view = inflater.inflate(R.layout.fragment_display_txt_inp, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < answerList.size(); i++) {
            TxtInpAnswer txtInpAnswer = answerList.get(i);
            Crashlytics.log(Log.DEBUG, TAG, "Answer Text : " + txtInpAnswer.getAnswerText());
            queryAnswersView.setText(queryAnswersView.getText() + "\n" + txtInpAnswer.getAnswerText());
        }
    }
}
