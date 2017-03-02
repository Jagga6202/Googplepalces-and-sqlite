package com.getqueried.getqueried_android.create_query.helper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GetContactsList extends AsyncTask<String, String, List<FollowersInfo>> {

    private RecyclerView recyclerViewFollowers;

    private static final String TAG = "GetContactsList";
    private Context context;
    private Activity activity;

    public GetContactsList(Activity activity, RecyclerView recyclerViewFollowers) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.recyclerViewFollowers = recyclerViewFollowers;
    }

    @Override
    protected void onPreExecute() {
        Crashlytics.log(Log.DEBUG, TAG, "Inside onPreExecute");
        //GeneralUtils.displayDialog(progressDialog, "Fetching Contact Details ..");
    }

    @Override
    protected List<FollowersInfo> doInBackground(String... params) {
        List<FollowersInfo> contactListData = new ArrayList<FollowersInfo>();
        FollowersInfo listInfo;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        } catch (SecurityException e) {
            //SecurityException can be thrown if we don't have the right permissions
            Crashlytics.log(Log.ERROR, TAG, "Permission denied. " + e.getMessage());
        }


        if (cursor != null) {
            try {
                HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();

                int indexOfNormalizedNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
                int indexOfDisplayName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indexOfDisplayNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int indexOfContactId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

                String contactId;

                if (cursor.moveToFirst()) {
                    while (cursor.moveToNext()) {
                        String normalizedNumber = cursor.getString(indexOfNormalizedNumber);
                        if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                            contactId = cursor.getString(indexOfContactId);
                            //haven't seen this number yet: do something with this contact!
                            listInfo = new FollowersInfo();
                            listInfo.name = cursor.getString(indexOfDisplayName);
                            listInfo.number = cursor.getString(indexOfDisplayNumber);
                            if (GeneralUtils.retrieveContactPhoto(context, contactId) != null)
                                listInfo.icon = GeneralUtils.retrieveContactPhoto(context, contactId);
                            else
                                listInfo.icon = BitmapFactory.decodeResource(context.getResources(),
                                        R.drawable.profile_placeholder_icon);

                            contactListData.add(listInfo);
                            Collections.sort(contactListData);
                        } else {
                            //don't do anything with this contact because we've already found this number
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }
        Crashlytics.log(Log.DEBUG, TAG, "Contact list size:" + contactListData.size());
        return contactListData;
    }

    @Override
    protected void onPostExecute(List<FollowersInfo> contactList) {
        //GeneralUtils.removeDialog(progressDialog);
        Crashlytics.log(Log.DEBUG, TAG, "Inside onPostExecute");
        /*FollowersListAdapter adapter = new FollowersListAdapter(context, contactList);
        recyclerViewFollowers.setAdapter(adapter);

        recyclerViewFollowers.addItemDecoration(new RecyclerViewItemDecoration(context));
        recyclerViewFollowers.setLayoutManager(new LinearLayoutManager(context));*/
    }
}