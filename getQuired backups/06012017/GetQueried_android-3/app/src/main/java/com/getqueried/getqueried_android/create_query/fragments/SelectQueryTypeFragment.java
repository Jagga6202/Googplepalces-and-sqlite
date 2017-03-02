package com.getqueried.getqueried_android.create_query.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.fragments.AnswerProvided;
import com.getqueried.getqueried_android.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectQueryTypeFragment extends Fragment {

    private static final String TAG = "SelectQueryTypeFragment";

    AnswerProvided callback;

    @OnClick({R.id.imageButton_text_query, R.id.imageButton_image_query})
    void textQuery(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.imageButton_text_query:
                callback.answerStateChanged(true, -1, Constants.CreateQuery.TXTOPT);
                transaction.replace(R.id.query_type_layout, new TxtOptQueryFragment(), "DisplaySelectedContact");
                break;
            case R.id.imageButton_image_query:
                callback.answerStateChanged(true, -1, Constants.CreateQuery.IMGOPT);
                transaction.replace(R.id.query_type_layout, new ImgOptQueryFragment(), "DisplaySelectedContact");
                break;
            default:
                Crashlytics.logException(new Throwable("This should never be happened"));
        }
        transaction.commit();
    }

    public static SelectQueryTypeFragment createInstance(AnswerProvided callback) {
        SelectQueryTypeFragment res = new SelectQueryTypeFragment();
        res.callback = callback;
        return res;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_query_type, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
