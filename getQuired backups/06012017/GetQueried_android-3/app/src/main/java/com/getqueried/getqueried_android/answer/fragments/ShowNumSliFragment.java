package com.getqueried.getqueried_android.answer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.DefaultAnswerItem;
import com.getqueried.getqueried_android.model.NumSliAnswer;
import com.getqueried.getqueried_android.model.NuminpAnswer;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 03/01/17.
 */

public class ShowNumSliFragment extends Fragment {

    public static final String TAG = "ShowNumSliFragment";

    @Bind(R.id.textseek)
    TextView textSeek;

    @Bind(R.id.seekbar)
    SeekBar seekbar;

    @Bind(R.id.minseek)
    TextView minSeek;

    @Bind(R.id.maxseek)
    TextView maxSeek;


    public AnswerProvided callback;

    private ArrayList<NumSliAnswer> answerList;
    public String numslimax,numslimin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        numslimin=bundle.getString("choiceMin");
        numslimax=bundle.getString("choiceMax");
        answerList = (ArrayList<NumSliAnswer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numsli, container, false);
        ButterKnife.bind(this, view);
         minSeek.setText(numslimin);
        maxSeek.setText(numslimax);
        seekbar.setMax(Integer.parseInt(numslimax));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < answerList.size(); i++) {
            NumSliAnswer txtInpAnswer = answerList.get(i);
            Crashlytics.log(Log.DEBUG, TAG, "Answer Text : " + txtInpAnswer.getanswer());
            textSeek.setText(textSeek.getText() + "\n" + txtInpAnswer.getanswer());
            seekbar.setProgress(Integer.parseInt(txtInpAnswer.getanswer()));
            seekbar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }

    }

    public static ShowNumSliFragment getInstance() {
        return new ShowNumSliFragment();
    }


}
