package com.getqueried.getqueried_android.profile.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.MyFeedAdapter;
import com.getqueried.getqueried_android.model.FeedItem;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.getqueried.getqueried_android.utils.Constants.User.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class YourFeedFragment extends Fragment {

    private static final String TAG = "NewQueriesFragment";
    @Bind(R.id.ifNoItems)
    public TextView emptyListView;
    @Bind(R.id.recyclerView_user_feed)
    public RecyclerView recyclerViewUserFeed;

    private boolean dataInitialized = false;
    private String userId;
    private ArrayList<FeedItem> feeds;

    public static YourFeedFragment getInstance() {
        YourFeedFragment pagerFragment = new YourFeedFragment();
        return pagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefUtils.UserProfile userProfile = SharedPrefUtils.getUserProfile(getActivity());
        userId = "\"" + userProfile.uuid + "\"";

        getUserFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_your_feed, container, false);
        ButterKnife.bind(this, layout);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerViewUserFeed.setLayoutManager(llm);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!dataInitialized) {
            if (!initializeFeedData()) {
                dataInitialized = false;
                Crashlytics.log(Log.DEBUG, TAG, "List is empty. Setting empty view.");
                recyclerViewUserFeed.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
            }
        }
    }


    private void getUserFeed() {
        Crashlytics.log(Log.DEBUG, TAG, "getUserFeed called !!! ");
        JSONObject params = null;
        try {
            params = new JSONObject();
            params.put(USER_ID, userId);
            params.put(Constants.Query.TIME_FROM, "1970-01-01T00:00:00.000000Z");
            params.put(Constants.Query.TIME_TO, "2016-11-14T18:00:00.000000Z");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Crashlytics.log(Log.DEBUG, TAG, "Post User Feed Params : " + params);
        ((NetworkActivity) getActivity()).postJsonStringUrlEncodedWithToken(Constants.URL.userFeed, params,
                response -> {
                    Crashlytics.log(Log.DEBUG, TAG, "userFeed Query Response : " + response);
                    JSONArray items = null;
                    try {
                        items = new JSONArray(response);
                        feeds = new ArrayList<>(items.length());
                        JSONObject item;
                        for (int i = 0; i < items.length(); i++) {
                            item = items.getJSONObject(i);
                            FeedItem.FeedItemType type = FeedItem.FeedItemType.intToEnum(item.getInt("action"));
                            feeds.add(new FeedItem(type,
                                    item.getString(Constants.Feed.QUERY_ID),
                                    item.getString(Constants.Feed.SOURCE_ID),
                                    item.getString(Constants.Feed.TARGET_ID),
                                    (Date) item.get(Constants.Feed.TIMESTAMP)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        feeds = new ArrayList<>();
                    }
                    if (feeds.size() != 0) {
                        MyFeedAdapter adapter = new MyFeedAdapter(feeds, getActivity());
                        recyclerViewUserFeed.setAdapter(adapter);
                        recyclerViewUserFeed.setVisibility(View.VISIBLE);
                        emptyListView.setVisibility(View.GONE);
                    }
                }, null);
    }

    private boolean initializeFeedData() {
        dataInitialized = true;
        return false;
    }
}
