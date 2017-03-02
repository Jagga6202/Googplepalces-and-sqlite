package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import  android.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.NavigationView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideafoundation.meetaapp.Adapter.NeedTableAdapter;
import com.example.ideafoundation.meetaapp.Adapter.NewUserNeedAdapter;
import com.example.ideafoundation.meetaapp.Adapter.UserNeedAdapter;
import com.example.ideafoundation.meetaapp.model.DefaultNeed;
import com.example.ideafoundation.meetaapp.model.Need;
import com.example.ideafoundation.meetaapp.util.NewUserNeedHandler;
import com.example.ideafoundation.meetaapp.util.UserNeedHandler;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ideafoundation on 31/01/17.
 */

public class CreateNeedActivity extends AppCompatActivity
{
           Toolbar mToolbar;
   // ArrayList<Need> dataModels;
   // private static NeedTableAdapter adapter;
    ArrayList<DefaultNeed> dataModels;
    private static NewUserNeedAdapter adapter;
     ListView listview;
    EditText search_view;
    Button submit;

           DrawerLayout mDrawerLayout;
    UserNeedHandler handler;
    NewUserNeedHandler newHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_need_activity);
        handler=new UserNeedHandler(this);
        newHandler= new NewUserNeedHandler(this);
        initview();
        initToolbar();
        Log.e("list size",""+handler.getAllContacts().size());

    }



           void initToolbar(){
               mToolbar = (Toolbar)findViewById(R.id.toolbar);
               setSupportActionBar(mToolbar);
               getSupportActionBar().setDisplayHomeAsUpEnabled(false);
               getSupportActionBar().setHomeButtonEnabled(false);
               TextView header=(TextView)mToolbar.findViewById(R.id.header);
               header.setText("CreateNeed");
               ImageView click=(ImageView)mToolbar.findViewById(R.id.click);
               click.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                    Intent intent=new Intent(CreateNeedActivity.this,ActivityNav.class);
                       startActivity(intent);
                   }
               });

           }
public void initview()
{
    listview=(ListView)findViewById(R.id.listview);
    search_view=(EditText)findViewById(R.id.search_view);
    submit=(Button)findViewById(R.id.submit);
    dataModels=handler.getAllContacts();
    adapter=new NewUserNeedAdapter(dataModels,this);
    /*dataModels=new ArrayList<>();
    dataModels.add(new Need("accounting"));
    dataModels.add(new Need("airport"));
    dataModels.add(new Need("amusement_park"));
    dataModels.add(new Need("aquarium"));
    dataModels.add(new Need("art_gallery"));
    dataModels.add(new Need("atm"));
    dataModels.add(new Need("bakery"));
    dataModels.add(new Need("bank"));
    dataModels.add(new Need("bar"));
    dataModels.add(new Need("beauty_salon"));
    dataModels.add(new Need("bicycle_store"));
    dataModels.add(new Need("book_store"));
    dataModels.add(new Need("bowling_alley"));
    dataModels.add(new Need("bus_station"));
    dataModels.add(new Need("cafe"));
    dataModels.add(new Need("campground"));
    dataModels.add(new Need("car_dealer"));
    dataModels.add(new Need("car_rental"));
    dataModels.add(new Need("car_wash"));
    dataModels.add(new Need("casino"));
    dataModels.add(new Need("cemetery"));
    dataModels.add(new Need("church"));
    dataModels.add(new Need("city_hall"));
    dataModels.add(new Need("clothing_store"));
    dataModels.add(new Need("convenience_store"));
    dataModels.add(new Need("courthouse"));
    dataModels.add(new Need("dentist"));
    dataModels.add(new Need("department_store"));
    dataModels.add(new Need("doctor"));
    dataModels.add(new Need("electrician"));
    dataModels.add(new Need("electronics_store"));
    dataModels.add(new Need("embassy"));
    dataModels.add(new Need("fire_station"));
    dataModels.add(new Need("florist"));
    dataModels.add(new Need("funeral_home"));
    dataModels.add(new Need("furniture_store"));
    dataModels.add(new Need("gas_station"));
    dataModels.add(new Need("gym"));
    dataModels.add(new Need("hair_care"));
    dataModels.add(new Need("hardware_store"));
    dataModels.add(new Need("hindu_temple"));
    dataModels.add(new Need("home_goods_store"));
    dataModels.add(new Need("hospital"));
    dataModels.add(new Need("insurance_agency"));
    dataModels.add(new Need("jewelry_store"));
    dataModels.add(new Need("laundry"));
    dataModels.add(new Need("lawyer"));
    dataModels.add(new Need("library"));
    dataModels.add(new Need("liquor_store"));
    dataModels.add(new Need("local_government_office"));
    dataModels.add(new Need("locksmith"));
    dataModels.add(new Need("lodging"));
    dataModels.add(new Need("meal_delivery"));
    dataModels.add(new Need("meal_takeaway"));
    dataModels.add(new Need("mosque"));
    dataModels.add(new Need("movie_rental"));
    dataModels.add(new Need("movie_theater"));
    dataModels.add(new Need("moving_company"));
    dataModels.add(new Need("museum"));
    dataModels.add(new Need("night_club"));
    dataModels.add(new Need("painter"));
    dataModels.add(new Need("park"));
    dataModels.add(new Need("parking"));
    dataModels.add(new Need("pet_store"));
    dataModels.add(new Need("pharmacy"));
    dataModels.add(new Need("physiotherapist"));
    dataModels.add(new Need("plumber"));
    dataModels.add(new Need("police"));
    dataModels.add(new Need("post_office"));
    dataModels.add(new Need("real_estate_agency"));
    dataModels.add(new Need("restaurant"));
    dataModels.add(new Need("roofing_contractor"));
    dataModels.add(new Need("rv_park"));
    dataModels.add(new Need("school"));
    dataModels.add(new Need("shoe_store"));
    dataModels.add(new Need("shopping_mall"));
    dataModels.add(new Need("spa"));
    dataModels.add(new Need("stadium"));
    dataModels.add(new Need("storage"));
    dataModels.add(new Need("store"));
    dataModels.add(new Need("subway_station"));
    dataModels.add(new Need("synagogue"));
    dataModels.add(new Need("taxi_stand"));
    dataModels.add(new Need("train_station"));
    dataModels.add(new Need("transit_station"));
    dataModels.add(new Need("travel_agency"));
    dataModels.add(new Need("university"));
    dataModels.add(new Need("veterinary_care"))	;
    dataModels.add(new Need("zoo"));
    adapter =new NeedTableAdapter(dataModels,this);*/
    listview.setAdapter(adapter);
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Need dataModel= dataModels.get(position);
                String name=dataModel.getPlaces();
                Intent intent=new Intent(CreateNeedActivity.this,MapActivity.class);
                intent.putExtra("places",name);
                startActivity(intent);
                Log.e("Name",name);*/
        }
    });
    listview.setTextFilterEnabled(true);
    search_view.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = search_view.getText().toString().toLowerCase(Locale.getDefault());
            adapter.filter(text);
        }
    });

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           //ArrayList<Need> actorList = ((NeedTableAdapter)listview.getAdapter()).getSelectActorList();
           ArrayList<DefaultNeed> actorList = ((NewUserNeedAdapter)listview.getAdapter()).getSelectActorList();
            handler.deleteone(actorList);
            newHandler.allpalces(actorList);
            Toast.makeText(CreateNeedActivity.this,""+actorList.size(),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(CreateNeedActivity.this, UserNeeds.class);
            intent.putExtra("list",actorList);
            startActivity(intent);
        }
    });

}

}
