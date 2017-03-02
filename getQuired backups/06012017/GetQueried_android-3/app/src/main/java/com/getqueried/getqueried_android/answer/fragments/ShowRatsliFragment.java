package com.getqueried.getqueried_android.answer.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.NumSliAnswer;
import com.getqueried.getqueried_android.model.RatsliAnswer;
import com.getqueried.getqueried_android.utils.Constants;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ideafoundation on 04/01/17.
 */

public class ShowRatsliFragment extends Fragment {


    public static final String TAG = "ShowRatsliFragment";

  /*  @Bind(R.id.customSeekBar)
   SeekBar customSeekBar;*/

    /* @Bind(R.id.customprogressbar)
     ProgressBar customprogressbar;*/
    @Bind(R.id.mySeekBar)
    VerticalSeekBar customSeekBar;

    String minRatsli,maxRatsli;
    private ArrayList<RatsliAnswer> answerList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        minRatsli=bundle.getString("choiceMin");
        maxRatsli=bundle.getString("choiceMax");
        answerList = (ArrayList<RatsliAnswer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showratsli, container, false);
        ButterKnife.bind(this, view);
        customSeekBar.setMax(Integer.parseInt(maxRatsli));
        customSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        if(maxRatsli.equalsIgnoreCase("3"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue3,null));
            }
        }
        else if(maxRatsli.equalsIgnoreCase("5"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue5,null));
            }
        }
        else if(maxRatsli.equalsIgnoreCase("7"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.e("dsdsdsdsd","dfsfsdfsdfdsgf");
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue7,null));
            }
        }
        else if(maxRatsli.equalsIgnoreCase("10"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customSeekBar.setBackground(getResources().getDrawable(R.drawable.slidervalue10,null));
            }
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < answerList.size(); i++) {
            RatsliAnswer txtInpAnswer = answerList.get(i);
            Log.e("Ratsli value",""+txtInpAnswer.getanswer());
            String str=txtInpAnswer.getanswer();
            String str1=str.substring(1,str.length()-1);
            customSeekBar.setProgress(Integer.parseInt(str1));

        }

    }

    public static ShowRatsliFragment getInstance() {
        return new ShowRatsliFragment();
    }
}
