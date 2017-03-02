package com.getqueried.getqueried_android.answer.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import rx.internal.operators.OperatorSkipWhile;

/**
 * Created by just_me on 05.11.16.
 */
public class DisplayImgOptFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "DisplayImgOptFragment";
    public String minImgOpt,maxImgOpt;
    private View[] filters = new View[Constants.MAX_OPTIONS];
    @Bind(R.id.filter1)
    public View filter1;
    @Bind(R.id.filter2)
    public View filter2;
    @Bind(R.id.filter3)
    public View filter3;
    @Bind(R.id.filter4)
    public View filter4;
    @Bind(R.id.filter5)
    public View filter5;
    @Bind(R.id.filter6)
    public View filter6;
    @Bind(R.id.filter7)
    public View filter7;
    @Bind(R.id.filter8)
    public View filter8;
    @Bind(R.id.filter9)
    public View filter9;
    @Bind(R.id.filter10)
    public View filter10;


    private ImageView[] options = new ImageView[Constants.MAX_OPTIONS];
    @Bind(R.id.imageView_option1)
    public ImageView option1;
    @Bind(R.id.imageView_option2)
    public ImageView option2;
    @Bind(R.id.imageView_option3)
    public ImageView option3;
    @Bind(R.id.imageView_option4)
    public ImageView option4;
    @Bind(R.id.imageView_option5)
    public ImageView option5;
    @Bind(R.id.imageView_option6)
    public ImageView option6;
    @Bind(R.id.imageView_option7)
    public ImageView option7;
    @Bind(R.id.imageView_option8)
    public ImageView option8;
    @Bind(R.id.imageView_option9)
    public ImageView option9;
    @Bind(R.id.imageView_option10)
    public ImageView option10;


    private TextView[] textViews = new TextView[Constants.MAX_OPTIONS];
    @Bind(R.id.textInfo1)
    public TextView textView1;
    @Bind(R.id.textInfo2)
    public TextView textView2;
    @Bind(R.id.textInfo3)
    public TextView textView3;
    @Bind(R.id.textInfo4)
    public TextView textView4;
    @Bind(R.id.textInfo5)
    public TextView textView5;
    @Bind(R.id.textInfo6)
    public TextView textView6;
    @Bind(R.id.textInfo7)
    public TextView textView7;
    @Bind(R.id.textInfo8)
    public TextView textView8;
    @Bind(R.id.textInfo9)
    public TextView textView9;
    @Bind(R.id.textInfo10)
    public TextView textView10;



    private ArrayList<AnswerOptionItem> pictures;
    private ArrayList<Integer> answers;
    private boolean answersProvided;
    public int optionSelected = -1;
    public AnswerProvided callback;
    ArrayList multiselect;
    public AnswerProvidedMultiselect callabckMulti;

    /*public static DisplayImgOptFragment getInstance(AnswerProvided callback) {
        DisplayImgOptFragment fragment = new DisplayImgOptFragment();
        fragment.callback = callback;
        return fragment;
    }*/

    public static DisplayImgOptFragment getInstance(AnswerProvided callback) {
        Log.e("inside callback","inside callback");
        DisplayImgOptFragment fragment = new DisplayImgOptFragment();
        fragment.callback = callback;
        return fragment;
    }

    public static DisplayImgOptFragment getInstance(AnswerProvidedMultiselect callabckMulti) {

        DisplayImgOptFragment fragment = new DisplayImgOptFragment();
        fragment.callabckMulti = callabckMulti;
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        answersProvided = bundle.getBoolean(Constants.Data.ANSWERS_PROVIDED);
        pictures = (ArrayList<AnswerOptionItem>) bundle.getSerializable(Constants.CreateQuery.OPTIONS);
        minImgOpt=bundle.getString("choiceMin");
        maxImgOpt=bundle.getString("choiceMax");
        Log.e("pictures size",pictures.toString()+"."+minImgOpt+"."+maxImgOpt);
        if (answersProvided) {
            answers = (ArrayList<Integer>) bundle.getSerializable(Constants.Answer.ANSWER_LIST);
            Log.e("answers size",answers.toString());
        }
    }

    @OnClick({R.id.imageView_option1, R.id.imageView_option2, R.id.imageView_option3, R.id.imageView_option4,
            R.id.imageView_option5, R.id.imageView_option6, R.id.imageView_option7, R.id.imageView_option8,
            R.id.imageView_option9, R.id.imageView_option10})
    void selectAnswer(View view) {
        int newOptionSelected = Integer.valueOf(view.getTag().toString())-1;
        Log.e("number of options"," "+newOptionSelected);
        if (newOptionSelected >= pictures.size()) {
            return;
        }
        /*if (!answersProvided) {

            if(minImgOpt==maxImgOpt)
            {

                if (newOptionSelected != optionSelected) {
                    updateHighLight(newOptionSelected);
                    optionSelected = newOptionSelected;
                    callback.answerStateChanged(true, newOptionSelected+1, "");
                } else {
                    unHighLightAll();
                    optionSelected = -1;
                    callback.answerStateChanged(false, -1, null);
                }

            }


           else
            {
                if (newOptionSelected != optionSelected) {

                    optionSelected = newOptionSelected;
                    ColorDrawable back=(ColorDrawable) filters[optionSelected].getBackground();
                    int color=back.getColor();

                    if(color==Color.parseColor("#55EE0000"))
                    {
                        filters[optionSelected].setBackgroundColor(getResources().getColor(R.color.fullyTransparent));
                        if(multiselect.size()!=0)
                        {
                            multiselect.remove(multiselect.indexOf(optionSelected));
                        }

                    }
                    else
                    {
                        multiselect.add(optionSelected+1);
                        callabckMulti.answerStateChangedmultiselect(true, multiselect, "");
                        filters[optionSelected].setBackgroundColor(Color.parseColor("#55EE0000"));
                    }

                }
                else
                {
                    filters[optionSelected].setBackgroundColor(getResources().getColor(R.color.fullyTransparent));
                    optionSelected = -1;
                    callabckMulti.answerStateChangedmultiselect(false, null, null);
                }
            }
        }*/
        if (!answersProvided)
        {

            if(maxImgOpt.equals(minImgOpt))
            {

                if (newOptionSelected != optionSelected)
                {
                    updateHighLight(newOptionSelected);
                    optionSelected = newOptionSelected;
                    callback.answerStateChanged(true, newOptionSelected+1, "");
                }
                else
                {
                    unHighLightAll();
                    optionSelected = -1;
                    callback.answerStateChanged(false, -1, null);
                }

            }
            else
            {

                if (newOptionSelected != optionSelected)
                {
                    //updateHighLight(newOptionSelected);
                    optionSelected = newOptionSelected;
                    ColorDrawable back=(ColorDrawable) filters[optionSelected].getBackground();
                    int color=back.getColor();
                    if(color==Color.parseColor("#55EE0000"))
                    {
                        filters[optionSelected].setBackgroundColor(Color.parseColor("#D4D4D4D4"));
                        if(multiselect.size()!=0)
                        {
                            multiselect.remove(multiselect.indexOf(optionSelected+1));
                        }
                    }
                    else
                    {
                        multiselect.add(optionSelected+1);
                        Log.e("number of options"," "+multiselect);
                        callabckMulti.answerStateChangedmultiselect(true, multiselect, "");
                        filters[optionSelected].setBackgroundColor(Color.parseColor("#55EE0000"));
                    }



                }
                else
                {
                    //unHighLightAll();
                    filters[optionSelected].setBackgroundColor(Color.parseColor("#D4D4D4D4"));
                    optionSelected = -1;
                    callabckMulti.answerStateChangedmultiselect(false, null, null);
                }
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
        multiselect=new ArrayList();
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

        for(int i=pictures.size();i<10;i++)
        {
            options[i].setVisibility(View.GONE);
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
        filters[4] = filter5;
        filters[5] = filter6;
        filters[6] = filter7;
        filters[7] = filter8;
        filters[8] = filter9;
        filters[9] = filter10;

        textViews[0] = textView1;
        textViews[1] = textView2;
        textViews[2] = textView3;
        textViews[3] = textView4;
        textViews[4] = textView5;
        textViews[5] = textView6;
        textViews[6] = textView7;
        textViews[7] = textView8;
        textViews[8] = textView9;
        textViews[9] = textView10;

        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        options[3] = option4;
        options[4] = option5;
        options[5] = option6;
        options[6] = option7;
        options[7] = option8;
        options[8] = option9;
        options[9] = option10;

    }
}
