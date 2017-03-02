package com.getqueried.getqueried_android.answer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.AnswerQueriesActivity;
import com.getqueried.getqueried_android.model.DefaultAnswerItem;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 22/12/16.
 */

public class DisplayNumsliFragment extends Fragment {

    public static final String TAG = "DisplayNumsliFragment";

    @Bind(R.id.textseek)
    TextView textSeek;

    @Bind(R.id.seekbar)
    SeekBar seekbar;

    @Bind(R.id.minseek)
    TextView minSeek;

    @Bind(R.id.maxseek)
    TextView maxSeek;
    public AnswerProvided callback;
    private boolean answersProvided;
    private ArrayList<Integer> answers;
    public String numslimax,numslimin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        answersProvided = bundle.getBoolean(Constants.Data.ANSWERS_PROVIDED);
        numslimin=bundle.getString("choiceMin");
        numslimax=bundle.getString("choiceMax");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numsli, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        textSeek.setText(numslimin);
        minSeek.setText(numslimin);
        maxSeek.setText(numslimax);
        seekbar.setMax(Integer.parseInt(numslimax));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSeek.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String str=textSeek.getText().toString();
                boolean isAnswerProvided = !str.isEmpty();
                callback.answerStateChanged(isAnswerProvided, -1, str);

            }
        });


    }

    public static DisplayNumsliFragment getInstance(AnswerProvided callback) {
        DisplayNumsliFragment fragment = new DisplayNumsliFragment();
        fragment.callback = callback;
        return fragment;
    }
}
