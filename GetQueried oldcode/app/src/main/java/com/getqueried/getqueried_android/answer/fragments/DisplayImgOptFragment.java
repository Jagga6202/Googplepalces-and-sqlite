package com.getqueried.getqueried_android.answer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.AnswerOptionItem;
import com.getqueried.getqueried_android.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by just_me on 05.11.16.
 */
public class DisplayImgOptFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "DisplayImgOptFragment";

    private View[] filters = new View[Constants.MAX_OPTIONS];
    @Bind(R.id.filter1)
    public View filter1;
    @Bind(R.id.filter2)
    public View filter2;
    @Bind(R.id.filter3)
    public View filter3;
    @Bind(R.id.filter4)
    public View filter4;

    private ImageView[] options = new ImageView[Constants.MAX_OPTIONS];
    @Bind(R.id.imageView_option1)
    public ImageView option1;
    @Bind(R.id.imageView_option2)
    public ImageView option2;
    @Bind(R.id.imageView_option3)
    public ImageView option3;
    @Bind(R.id.imageView_option4)
    public ImageView option4;

    private TextView[] textViews = new TextView[Constants.MAX_OPTIONS];
    @Bind(R.id.textInfo1)
    public TextView textView1;
    @Bind(R.id.textInfo2)
    public TextView textView2;
    @Bind(R.id.textInfo3)
    public TextView textView3;
    @Bind(R.id.textInfo4)
    public TextView textView4;

    private ArrayList<AnswerOptionItem> pictures;
    private ArrayList<Integer> answers;
    private boolean answersProvided;
    public int optionSelected = -1;
    AnswerProvided callback;

    public static DisplayImgOptFragment getInstance(AnswerProvided callback) {
        DisplayImgOptFragment fragment = new DisplayImgOptFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        answersProvided = bundle.getBoolean(Constants.Data.ANSWERS_PROVIDED);
        pictures = (ArrayList<AnswerOptionItem>) bundle.getSerializable(Constants.CreateQuery.OPTIONS);
        if (answersProvided) {
            answers = (ArrayList<Integer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
        }
    }

    @OnClick({R.id.imageView_option1, R.id.imageView_option2, R.id.imageView_option3, R.id.imageView_option4})
    void selectAnswer(View view) {
        if (!answersProvided) {
            int newOptionSelected = Integer.valueOf(view.getTag().toString()) - 1;
            if (newOptionSelected >= pictures.size()) {
                return;
            }
            if (newOptionSelected != optionSelected) {
                optionSelected = newOptionSelected;
                callback.answerStateChanged(true, newOptionSelected, "");
                updateHighLight(newOptionSelected);
            } else {
                optionSelected = -1;
                callback.answerStateChanged(false, -1, null);
                unHighLightAll();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_img_opt, container, false);
        ButterKnife.bind(this, view);
        fillTheOptionsList();

        if (pictures == null) {
            Crashlytics.logException(new Throwable("pictures list is null"));
            return view;
        }

        for (int i = 0; i < pictures.size(); i++) {
            if (pictures.get(i).answerOptionImage.length() == 0) {
                options[i].setEnabled(false);
            } else {
                String imageUrl = Constants.URL.getQueryImage + pictures.get(i).answerOptionImage;
                String imageDescription = pictures.get(i).answerOptionText;
                Picasso.with(getActivity())
                        .load(imageUrl).placeholder(getResources().getDrawable(R.drawable.photo))
                        .fit().centerInside().into(options[i]);
                textViews[i].setText(imageDescription);
            }
        }

        // provide answers if needed
        if (answersProvided) {
            int allVoted = 0;
            for (int i = 0; i < answers.size(); i++) {
                allVoted += answers.get(i);
            }

            if (allVoted == 0) {
                Crashlytics.log(Log.ERROR, TAG, "voted amount is zero");
                return view;
            }
            for (int i = 0; i < pictures.size(); i++) {
                textViews[i].append(" " + 100 * answers.get(i) / allVoted + "%");
            }
        }
        return view;
    }

    private void updateHighLight(int optionToHighLight) {
        unHighLightAll();
        filters[optionToHighLight].setBackgroundColor(Color.parseColor("#55EE0000"));
    }

    private void unHighLightAll() {
        for (int i = 0; i < Constants.MAX_OPTIONS; i++) {
            filters[i].setBackgroundColor(getResources().getColor(R.color.fullyTransparent));
        }
    }

    private void fillTheOptionsList() {
        filters[0] = filter1;
        filters[1] = filter2;
        filters[2] = filter3;
        filters[3] = filter4;
        textViews[0] = textView1;
        textViews[1] = textView2;
        textViews[2] = textView3;
        textViews[3] = textView4;
        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        options[3] = option4;
    }
}
