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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.AnswerQueriesActivity;
import com.getqueried.getqueried_android.model.DefaultAnswerItem;
import com.getqueried.getqueried_android.model.NumImpQuery;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 22/12/16.
 */

public class DisplayNuminpFragment extends Fragment {
    public static final String TAG = "DisplayNuminpFragment";

    @Bind(R.id.answer_numinp)
    EditText answer_numinp;

   public AnswerProvided callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numinp, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String currText = editable.toString();
            boolean isAnswerProvided = !currText.isEmpty();
            callback.answerStateChanged(isAnswerProvided, -1, currText);
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        answer_numinp.addTextChangedListener(textListener);
    }

    public static DisplayNuminpFragment getInstance(AnswerProvided callback) {
        DisplayNuminpFragment fragment = new DisplayNuminpFragment();
        fragment.callback = callback;
        return fragment;
    }
}
