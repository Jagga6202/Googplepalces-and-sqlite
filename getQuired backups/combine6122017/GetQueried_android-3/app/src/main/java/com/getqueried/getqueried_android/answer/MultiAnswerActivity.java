package com.getqueried.getqueried_android.answer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.MyPageAdapter;
import com.getqueried.getqueried_android.answer.fragments.TextInpFragment;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.NetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ideafoundation on 06/01/17.
 */

public class MultiAnswerActivity extends NetworkActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;

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

    // if answers is presented
    @Bind(R.id.layout_post_query)
    LinearLayout layoutPostQuery;
    @Bind(R.id.textView_answers_count)
    TextView answersCount;
    @Bind(R.id.textView_like_count)
    TextView likeCount;
    @Bind(R.id.textView_share_count)
    TextView shareCount;

    MyPageAdapter pageAdapter;
    Bundle bundle;
    JSONArray singleQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multianswer);
        bundle=getIntent().getExtras();
        try {
            singleQuery=new JSONArray(bundle.getString("SingleQuery"));
            Log.e("bundle data",""+singleQuery+" "+bundle.getInt(Constants.Data.QUERY_POSITION));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Fragment> fragments = getFragments();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager =
                (ViewPager)findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(TextInpFragment.getInstance());
        fList.add(TextInpFragment.getInstance());
        fList.add(TextInpFragment.getInstance());

        return fList;
    }
}
