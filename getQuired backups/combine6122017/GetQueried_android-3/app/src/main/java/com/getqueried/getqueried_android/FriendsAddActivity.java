package com.getqueried.getqueried_android;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.GameRequestDialog;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAddActivity extends AppCompatActivity implements View.OnClickListener {
    MyCustomAdapter dataAdapter = null;
    Toolbar toolbar;
    ListView listView;
    boolean flag = true;
    GameRequestDialog requestDialog;
    CallbackManager callbackManager;
    JSONArray friendslist;
    ArrayList<Country> friends = new ArrayList<Country>();
    Button selectcb,deselectcb,bt;
    EditText inputSearch;
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<HashMap<String, Object>> searchResults;
    TextView donebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_add);
        listView = (ListView) findViewById(R.id.listView);
        selectcb=(Button)findViewById(R.id.selectallbtn);
        deselectcb=(Button)findViewById(R.id.deselectallbtn);
        donebtn=(TextView)findViewById(R.id.donebtn);

        //Generate list View from ArrayList
        displayListView();
        checkButtonClick();
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        toolbar.setTitle("SUGGESTED FRIENDS");
        setSupportActionBar(toolbar);
        String fontPath = "fonts/open-sans.semibold.ttf";
        String regularfontPath="fonts/open-sans.regular.ttf";
        // text view label
        Button txtGhost = (Button) findViewById(R.id.selectallbtn);
        Button txtGhos = (Button) findViewById(R.id.deselectallbtn);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tff = Typeface.createFromAsset(getAssets(), regularfontPath);
        // Applying font
        txtGhost.setTypeface(tf);
        txtGhos.setTypeface(tf);
        //  inputSearch = (EditText) findViewById(R.id.inputSearch);
       /* bt = new Button(this);
        bt.setText("DONE");

        bt.setTextColor(Color.WHITE);
        bt.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        bt.setGravity(Gravity.RIGHT);
        params.setMargins(0, 5, 0,4 );
        bt.setLayoutParams(params);
        bt.setOnClickListener(this);
        bt.setTextAppearance(this, R.style.ButtonFontStyle);
*/
//        toolbar.addView(bt);
        selectcb.setOnClickListener(this);
        deselectcb.setOnClickListener(this);
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookSdk.sdkInitialize(FriendsAddActivity.this);
                callbackManager = CallbackManager.Factory.create();
                requestDialog = new GameRequestDialog(FriendsAddActivity.this);
                requestDialog.registerCallback(callbackManager,
                        new FacebookCallback<GameRequestDialog.Result>() {
                            public void onSuccess(GameRequestDialog.Result result) {
                                String id = result.getRequestId();
                                //  Toast.makeText(getApplicationContext(),"Send request",Toast.LENGTH_SHORT).show();
                                String appLinkUrl, previewImageUrl;

                                appLinkUrl = "https://fb.me/1681936655399505";
                                previewImageUrl = "https://pbs.twimg.com/profile_images/737247131371704320/fanGrG3R.jpg";

                                if (AppInviteDialog.canShow()) {
                                    AppInviteContent content = new AppInviteContent.Builder()
                                            .setApplinkUrl(appLinkUrl)
                                            .setPreviewImageUrl(previewImageUrl)
                                            .build();
                                    AppInviteDialog.show(FriendsAddActivity.this, content);
                                }
                            }

                            public void onCancel() {
                            }

                            public void onError(FacebookException error) {
                            }
                        }
                );
                GameRequestContent content = new GameRequestContent.Builder()
                        .setMessage("Come play this level with me")
                        .setTo(StringUtils.join(mylist, ","))
                        .build();

                requestDialog.show(content);
            }
        });

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.selectallbtn);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.friends;
                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    if (country.isSelected()) {
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }
    private void displayListView() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String jsondata = data.getString("SharedPreferences", "");

        Log.i("Friendlist", jsondata);

        try {
            friendslist = new JSONArray(jsondata);

            for (int l = 0; l < friendslist.length(); l++) {
                String imagePath = friendslist.getJSONObject(l).getString("picture");
                JSONObject json= (JSONObject) new JSONTokener(imagePath).nextValue();
                JSONObject json2 = json.getJSONObject("data");
                String Imagepath = (String) json2.get("url");
                Country country = new Country(friendslist.getJSONObject(l).getString("id"),friendslist.getJSONObject(l).getString("name"),Imagepath, false);
                friends.add(country);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.country_info, friends);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        if(flag==true){
            ArrayList<Country> countryList = dataAdapter.friends;
            for ( int i=0; i < listView.getCount(); i++) {
                listView.setItemChecked(i, true);
                Country country = countryList.get(i);
                mylist.add(country.getCode());
            }
        }else
        {
            mylist.clear();
        }
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                CheckBox chk = (CheckBox) findViewById(R.id.checkBox1);

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.selectallbtn:
                int itemCount =listView.getCount();
                ArrayList<Country> countryList = dataAdapter.friends;
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                for ( int i=0; i < itemCount; i++) {
                    listView.setItemChecked(i, true);
                    Country country = countryList.get(i);
                    mylist.add(country.getCode());
                    flag = true;
                    dataAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.deselectallbtn:
                //   Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                for(int i=0; i < listView.getChildCount(); i++){
                    ViewGroup item = (ViewGroup)listView.getChildAt(i);
                    CheckBox checkbox = (CheckBox)item.findViewById(R.id.checkBox1);
                    checkbox.setChecked(false);
                    //checkbox.setButtonDrawable(R.drawable.checkmark_grey);
                    mylist.clear();

                }
                break;
            default:
                // Toast.makeText(getApplicationContext(),"Button clicked",Toast.LENGTH_SHORT).show();



                break;

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> friends;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> friends) {
            super(context, textViewResourceId, friends);
            this.friends = new ArrayList<Country>();
            this.friends.addAll(friends);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            CircleImageView img;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.img=(CircleImageView)convertView.findViewById(R.id.profilepic);
                holder.name.setTag(position);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Country country = (Country) cb.getTag();
                        //   mylist.clear();
                       /* Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + country.getCode() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        country.setSelected(cb.isChecked());
                        if (country.getCode() != ""&&cb.isChecked()) {
                            mylist.add(country.getCode());
                        }
                        if(cb.isChecked()==false){
                            mylist.remove(country.getCode());
                        }
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = friends.get(position);
            holder.code.setText(country.getName());

            // holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);
            holder.name.setChecked(flag);
            Log.d("FFLAGGGG",""+flag);
            Picasso.with(getApplicationContext()).load(country.getThumbnailUrl()).into(holder.img);
            return convertView;

        }

    }
}

