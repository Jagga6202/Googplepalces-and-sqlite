package com.getqueried.getqueried_android.answer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.NuminpAnswer;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 03/01/17.
 */

public class ShowNuminpFragment extends Fragment {

    public static final String TAG = "ShowNuminpFragment";
    private ArrayList<NuminpAnswer> answerList;
    @Bind(R.id.numinp_answer)
    TextView numinp_answer;
    public AnswerProvided callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        answerList = (ArrayList<NuminpAnswer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shownuminp, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < answerList.size(); i++) {
            NuminpAnswer txtInpAnswer = answerList.get(i);
            Crashlytics.log(Log.DEBUG, TAG, "Answer Text : " + txtInpAnswer.getanswer());
            numinp_answer.setText(numinp_answer.getText() + "\n" + txtInpAnswer.getanswer());
        }
    }

    public static ShowNuminpFragment getInstance() {
        return new ShowNuminpFragment();
    }

}
