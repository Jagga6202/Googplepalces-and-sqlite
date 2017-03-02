package com.getqueried.getqueried_android.answer.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.answer.AnswerQueriesActivity;
import com.getqueried.getqueried_android.model.AnswerOptionItem;
import com.getqueried.getqueried_android.model.DefaultAnswerItem;
import com.getqueried.getqueried_android.model.DisplayRatsliQuery;
import com.getqueried.getqueried_android.model.TxtInpAnswer;
import com.getqueried.getqueried_android.utils.Constants;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 22/12/16.
 */

public class DisplayRatsliFragment extends Fragment {

    public static final String TAG = "DisplayRatsliFragment";

  /*  @Bind(R.id.customSeekBar)
   SeekBar customSeekBar;*/

   /* @Bind(R.id.customprogressbar)
    ProgressBar customprogressbar;*/
   @Bind(R.id.mySeekBar)
   VerticalSeekBar customSeekBar;

    String minRatsli,maxRatsli;
    public AnswerProvided callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        minRatsli=bundle.getString("choiceMin");
        maxRatsli=bundle.getString("choiceMax");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratsli, container, false);


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

       /* customprogressbar.setMax(Integer.parseInt(maxRatsli));
        customprogressbar.setProgress(Integer.parseInt(minRatsli));*/


        customSeekBar.setMax(Integer.parseInt(maxRatsli));

        if(maxRatsli.equalsIgnoreCase("3"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue3,null));
            }
            customSeekBar.setProgress(1);
        }
        else if(maxRatsli.equalsIgnoreCase("5"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue5,null));
            }
            customSeekBar.setProgress(1);
        }
        else if(maxRatsli.equalsIgnoreCase("7"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.e("dsdsdsdsd","dfsfsdfsdfdsgf");
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue7,null));
            }
            customSeekBar.setProgress(1);
        }
        else if(maxRatsli.equalsIgnoreCase("10"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue10,null));
            }
            customSeekBar.setProgress(0);
        }
        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String str=String.valueOf(customSeekBar.getProgress());
                boolean isAnswerProvided = !str.isEmpty();
                callback.answerStateChanged(isAnswerProvided, -1, str);

            }
        });
    }

    public static DisplayRatsliFragment getInstance(AnswerProvided callback) {
        DisplayRatsliFragment fragment = new DisplayRatsliFragment();
        fragment.callback = callback;
        return fragment;
    }
}
