package com.getqueried.getqueried_android.profile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.FriendsAddActivity;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.adapter.FindPeopleFragmentAdapter;
import com.getqueried.getqueried_android.model.Metadata;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.RecyclerViewItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.getqueried.getqueried_android.utils.Constants.User.DESCRIPTION;
import static com.getqueried.getqueried_android.utils.Constants.User.IMAGEPATH;
import static com.getqueried.getqueried_android.utils.Constants.User.NAME;
import static com.getqueried.getqueried_android.utils.Constants.User.USER_ID;

public class FindPeopleFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "FindPeopleFragment";
    private View mainView;
    private TextView emptyListView,addFbFriends;
    private EditText searchPeople;
    private RecyclerView recyclerViewFindPeople;

    ArrayList<Metadata> metadataList = new ArrayList<>();


    public static FindPeopleFragment getInstance() {
        FindPeopleFragment pagerFragment = new FindPeopleFragment();
        return pagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_find_people, container, false);
        recyclerViewFindPeople = (RecyclerView) layout.findViewById(R.id.recyclerView_find_people);
        emptyListView = (TextView) layout.findViewById(R.id.empty_view_find_people);
        mainView = layout.findViewById(R.id.main_parent);
        addFbFriends=(TextView)layout.findViewById(R.id.add_fb_friends);
        addFbFriends.setOnClickListener(this);
        searchPeople=(EditText)layout.findViewById(R.id.search_people) ;
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerViewFindPeople.setLayoutManager(llm);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //API call returns list of user ids which are not being followed currently
        getUserNotFollowing();

    }


    private void getUserNotFollowing() {
        Crashlytics.log(Log.DEBUG, TAG, "getUserNotFollowing called !!! ");
        ((NetworkActivity) getActivity()).getString(Constants.URL.usersNotFollowing,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Crashlytics.log(Log.DEBUG, TAG, "getUserNotFollowing response : " + jsonArray);
                        String[] userNotFollowing = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            userNotFollowing[i] = jsonArray.getString(i).replace("\\[|\\]", "");
                            Crashlytics.log(Log.DEBUG, TAG, userNotFollowing[i]);
                            getUserMetadata((NetworkActivity) getActivity(),
                                    "\"" + userNotFollowing[i] + "\"");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                null);
    }

    public Metadata getUserMetadata(NetworkActivity networkActivity,
                                    String userId) {
        Crashlytics.log(Log.DEBUG, TAG, "getUserMetadata called !!! ");
        networkActivity.postStringUrlEncoded(Constants.URL.userMetadata, "[" + userId + "]", response -> {
            Crashlytics.log(Log.DEBUG, TAG, "User Metadata Response : " + response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                String uuid = jsonObject.getString(USER_ID);
                String name = jsonObject.getString(NAME);
                String description = jsonObject.getString(DESCRIPTION);
                String imagePath = jsonObject.getString(IMAGEPATH);
                Log.i("Image","Image: "+imagePath);
                Metadata metadata = new Metadata(uuid, name, description, imagePath);
                metadataList.add(metadata);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setUpView();
        }, null);
        return null;
    }

    public void setUpView() {
        if (!(metadataList.size() == 0)) {
            FindPeopleFragmentAdapter adapter = new FindPeopleFragmentAdapter(
                    getActivity(),
                    mainView,
                    metadataList,
                    (NetworkActivity) getActivity()
                    //this // callback listener for adding follower
            );
            recyclerViewFindPeople.setAdapter(adapter);
            addTextListener();
            recyclerViewFindPeople.addItemDecoration(new RecyclerViewItemDecoration(getActivity().getApplicationContext()));

        } else {
            Crashlytics.log(Log.DEBUG, TAG, "List is empty. Setting empty view.");
            recyclerViewFindPeople.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }
    }

    private void addTextListener() {


            searchPeople.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {}

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence query, int start, int before, int count) {

                    query = query.toString().toLowerCase();

                    final List<Metadata> filteredList = new ArrayList<>();

                    for (int i = 0; i < metadataList.size(); i++) {

                        final String text = metadataList.get(i).toString().toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(metadataList.get(i));
                        }
                    }

                    recyclerViewFindPeople.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    FindPeopleFragmentAdapter adapter = new FindPeopleFragmentAdapter(
                            getActivity(),
                            mainView,
                            filteredList,
                            (NetworkActivity) getActivity()
                            //this // callback listener for adding follower
                    );
                    recyclerViewFindPeople.setAdapter(adapter);
                    adapter.notifyDataSetChanged();  // data set changed
                }
            });

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getActivity(), FriendsAddActivity.class);
       // Log.i("Something wrong: ","Checkk");
        startActivity(i);
    }
}
