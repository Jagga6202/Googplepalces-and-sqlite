package com.getqueried.getqueried_android;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FriendsAddActivity extends AppCompatActivity implements View.OnClickListener {
    MyCustomAdapter dataAdapter = null;
    Toolbar toolbar;
    ListView listView;
    Button selectcb,deselectcb,bt;
    //   EditText inputSearch;

    ArrayList<HashMap<String, Object>> searchResults;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_add);
        listView = (ListView) findViewById(R.id.listView);
        selectcb=(Button)findViewById(R.id.selectallbtn);
        deselectcb=(Button)findViewById(R.id.deselectallbtn);
        //Generate list View from ArrayList
        displayListView();
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        // inputSearch = (EditText) findViewById(R.id.inputSearch);
        bt = new Button(this);
        bt.setText("Send");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.FILL_PARENT);
        params.gravity = Gravity.RIGHT;
        params.setMargins(0, 0, 0, 12);
        //   bt.setLayoutParams(params);
        bt.setOnClickListener(this);
        toolbar.addView(bt);
        selectcb.setOnClickListener(this);
        deselectcb.setOnClickListener(this);


    }

    private void displayListView() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String jsondata = data.getString("SharedPreferences", "");

        Log.i("Friendlist", jsondata);
        JSONArray friendslist;
        ArrayList<Country> friends = new ArrayList<Country>();

        try {
            friendslist = new JSONArray(jsondata);

            for (int l = 0; l < friendslist.length(); l++) {
                String imagePath = friendslist.getJSONObject(l).getString("id");
                Log.d("Friendlist", imagePath);
                Country country = new Country(friendslist.getJSONObject(l).getString("id"), friendslist.getJSONObject(l).getString("name"), false);
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
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.selectallbtn:
                //   Toast.makeText(getApplicationContext(),"Selectall clicked",Toast.LENGTH_SHORT).show();
                for(int i=0; i < listView.getChildCount(); i++){
                    ViewGroup item = (ViewGroup)listView.getChildAt(i);
                    CheckBox checkbox = (CheckBox)item.findViewById(R.id.checkBox1);
                    checkbox.setChecked(true);
                }
                break;
            case R.id.deselectallbtn:
                //   Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                for(int i=0; i < listView.getChildCount(); i++){
                    ViewGroup item = (ViewGroup)listView.getChildAt(i);
                    CheckBox checkbox = (CheckBox)item.findViewById(R.id.checkBox1);
                    checkbox.setChecked(false);
                }
                break;
            default:
                //Toast.makeText(getApplicationContext(),"Button clicked",Toast.LENGTH_SHORT).show();
               /* String appLinkUrl, previewImageUrl;

                appLinkUrl = "http://getqueried-android.pagedemo.co/?__hstc=152250342.1e67a1dd962fb6e8a2544cef64b9f411.1481670093790.1481757107476.1482481073315.6&__hssc=152250342.1.1482481073315&__hsfp=3213607277";
                previewImageUrl = "https://pbs.twimg.com/profile_images/737247131371704320/fanGrG3R.jpg";

                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();
                    AppInviteDialog.show(this, content);
                }*/
                Intent intent=new Intent(FriendsAddActivity.this,Invite.class);
                startActivity(intent);
                break;
        }

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
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getId() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
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

            return convertView;

        }

    }
}

