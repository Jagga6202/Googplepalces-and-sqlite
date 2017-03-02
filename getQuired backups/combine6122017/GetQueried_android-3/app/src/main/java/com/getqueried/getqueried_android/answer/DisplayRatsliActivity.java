package com.getqueried.getqueried_android.answer;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.model.DefaultAnswerItem;
import com.getqueried.getqueried_android.utils.NetworkActivity;

import java.util.ArrayList;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ideafoundation on 26/12/16.
 */

public class DisplayRatsliActivity extends NetworkActivity {

    public static ArrayList<DefaultAnswerItem> displayRatsliQueries;
    private static final String HEADER_TITLE = "Answer Query";
    public static final String TAG = "DisplayRatsliActivity";
    @Bind(R.id.layout_multiple_answer)
    LinearLayout multipleAnswer;

    @Bind(R.id.previous_img)
    ImageView previous_img;

    @Bind(R.id.previous_text)
    TextView previous_text;

    @Bind(R.id.next_img)
    ImageView next_img;

    @Bind(R.id.next_text)
    TextView next_text;

    @Bind(R.id.comment_layout)
    LinearLayout commentLayout;

    @Bind(R.id.comment)
    TextView comment;

    @Bind(R.id.textView_question_num1)
    TextView textViewQuestionNum1;

    @Bind(R.id.textView_question_num2)
    TextView textViewQuestionNum2;

    @Bind(R.id.textView_question_num3)
    TextView textViewQuestionNum3;

    @Bind(R.id.textView_questionText)
    TextView questionTextView;

    @Bind(R.id.imageView_anonymous)
    ImageView anonymousImageView;

    @Bind(R.id.header_layout)
    LinearLayout headerLayout;

    @Bind(R.id.profile_image)
    CircleImageView profileImageView;

    @Bind(R.id.textView_user_name)
    TextView userNameView;

    @Bind(R.id.textView_footer_message)
    TextView footerTextView;
    @Bind(R.id.layout_post_answer)
    LinearLayout footerLayout;
}
