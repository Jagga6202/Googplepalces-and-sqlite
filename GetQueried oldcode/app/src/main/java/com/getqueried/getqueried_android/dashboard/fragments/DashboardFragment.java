package com.getqueried.getqueried_android.dashboard.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.MyQueriesFragmentAdapter;
import com.getqueried.getqueried_android.answer.AnswerQueriesActivity;
import com.getqueried.getqueried_android.create_query.AskQuestionActivity;
import com.getqueried.getqueried_android.model.Query;
import com.getqueried.getqueried_android.model.QueryList;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ItemClickSupport;
import com.getqueried.getqueried_android.utils.NetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple Fragment subclass to display the answer results.
 */
public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    private FragmentType type;

    @Bind(R.id.ifNoItems)
    public TextView emptyListView;
    @Bind(R.id.items)
    public RecyclerView recyclerViewQueries;
    public QueryList myQueriesList;
    // array with all queries json
    private JSONArray jsonArrayQuery;


    private String ifEmptyText;
    private String URL_TO_GET_LIST_OF_IDS;
    private String URL_TO_GET_FEED_FOR_LIST_OF_IDS;

    @OnClick(R.id.fab_create_query)
    void createQuery() {
        GeneralUtils.route((AppCompatActivity) getContext(), AskQuestionActivity.class);
    }

    public static DashboardFragment getInstance(Context context, FragmentType type) {
        DashboardFragment fragment = new DashboardFragment();
        fragment.type = type;
        fragment.URL_TO_GET_FEED_FOR_LIST_OF_IDS = Constants.URL.getQueriesStructure;
        switch (type) {
            case MY_ANSWERS:
                fragment.ifEmptyText = context.getString(R.string.no_answers);
                fragment.URL_TO_GET_LIST_OF_IDS = Constants.URL.answeredQueries;
                break;
            case MY_QUERIES:
                fragment.ifEmptyText = context.getString(R.string.no_questions);
                fragment.URL_TO_GET_LIST_OF_IDS = Constants.URL.createdQueriesId;
                break;
            case NEW_QUERIES:
                fragment.ifEmptyText = context.getString(R.string.no_questions);
                fragment.URL_TO_GET_LIST_OF_IDS = Constants.URL.getQueryIdList;
                break;
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_queries, container, false);
        ButterKnife.bind(this, view);
        emptyListView.setText(ifEmptyText);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerViewQueries.setLayoutManager(llm);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Crashlytics.log(Log.DEBUG, TAG, "onViewCreated is called !!!!");
        getAnsweredQueryId();
    }

    // Get list of ids of queries answered by the user
    private void getAnsweredQueryId() {
        ((NetworkActivity) getActivity()).getString(
                URL_TO_GET_LIST_OF_IDS, response -> {
                    Crashlytics.log(Log.DEBUG, TAG, "Dashboard id : " + response);
                    getQueryResults(response);
                }, null);
    }

    // Get result of the queries using queryId
    private void getQueryResults(String queryIdList) {
        Crashlytics.log(Log.DEBUG, TAG, "Answered Queries Id List : " + queryIdList);
        ((NetworkActivity) getActivity()).postStringUrlEncoded(URL_TO_GET_FEED_FOR_LIST_OF_IDS,
                queryIdList, response -> {
                    ArrayList<Query> queryList = new ArrayList<>();
                    myQueriesList = new QueryList();
                    try {
                        jsonArrayQuery = new JSONArray(response);
                        Crashlytics.log(Log.DEBUG, TAG, "Length Query structure  array : " + jsonArrayQuery.length());
                        for (int i = 0; i < jsonArrayQuery.length(); i++) {
                            queryList.add(new Query(jsonArrayQuery.getJSONObject(i)
                                    .getJSONArray(Constants.CreateQuery.QUESTION_LIST)
                                    .getJSONObject(0).getString(Constants.CreateQuery.QUESTION_TEXT),
                                    null, null));
                        }
                    } catch (JSONException e) {
                        Crashlytics.log(Log.ERROR, TAG, "Query is " + jsonArrayQuery);
                        e.printStackTrace();
                    }
                    myQueriesList.setQueries(queryList);
                    setUpView();
                }, null);
    }

    private void setUpView() {
        if (!myQueriesList.getQueries().isEmpty()) {
            Crashlytics.log(Log.DEBUG, TAG, "Setting list adapter. List size : " + myQueriesList.getQueries().size());

            MyQueriesFragmentAdapter adapter = new MyQueriesFragmentAdapter(getActivity(), myQueriesList.getQueries());
            recyclerViewQueries.setAdapter(adapter);

            // OnClick listener for one of the items of the list
            ItemClickSupport.addTo(recyclerViewQueries)
                    .setOnItemClickListener((recyclerView, position, v) -> {
                        Intent intent = new Intent(getActivity(), AnswerQueriesActivity.class);
                        intent.putExtra(Constants.Data.IS_FOR_SHOWING_RESULT, type != FragmentType.NEW_QUERIES);
                        intent.putExtra(Constants.Data.ARRAY_OF_QUERIES, jsonArrayQuery.toString());
                        intent.putExtra(Constants.Data.QUERY_POSITION, position);
                        startActivity(intent);
                    });
        } else {
            Crashlytics.log(Log.DEBUG, TAG, "List is empty. Setting empty view.");
            recyclerViewQueries.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }
    }
}
