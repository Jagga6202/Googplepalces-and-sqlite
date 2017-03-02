package com.getqueried.getqueried_android.answer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.AnswerQueriesActivity;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.getqueried.getqueried_android.utils.Constants.Answer.ANONYMOUS;
import static com.getqueried.getqueried_android.utils.Constants.Answer.ANSWER_LIST;
import static com.getqueried.getqueried_android.utils.Constants.Answer.QUERYID;
import static com.getqueried.getqueried_android.utils.Constants.Answer.QUERYOBJ;
import static com.getqueried.getqueried_android.utils.Constants.Answer.QUESTION;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextInpFragment extends Fragment {

    public static final String TAG = "TextInpFragment";

    @Bind(R.id.editText_answer)
    EditText answerTextView;

    private AnswerProvided callback;

    public static TextInpFragment getInstance(AnswerProvided callback) {
        TextInpFragment fragment = new TextInpFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_inp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        hideSoftKeyboard();
        answerTextView.addTextChangedListener(textListener);
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

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}