package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.ideafoundation.meetaapp.Adapter.NeedUserAdapter;
import com.example.ideafoundation.meetaapp.Adapter.RecyclerViewAdapter;
import com.example.ideafoundation.meetaapp.Adapter.UserNeedAdapter;
import com.example.ideafoundation.meetaapp.model.DefaultNeed;
import com.example.ideafoundation.meetaapp.util.NewUserNeedHandler;
import com.example.ideafoundation.meetaapp.util.SwipeDetector;
import com.example.ideafoundation.meetaapp.util.UserNeedHandler;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;

import com.example.ideafoundation.meetaapp.Adapter.SwipeRecycleradapter;
import com.example.ideafoundation.meetaapp.model.Need;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by ideafoundation on 08/02/17.
 */

public class UserNeeds extends AppCompatActivity {
    Toolbar mToolbar;
    //ArrayList<Need> dataModels;
    ArrayList<DefaultNeed> dataModels;
     //UserNeedAdapter adapter;
    NeedUserAdapter adapter;
    ListView listview;
    NewUserNeedHandler newHandler;
   // RecyclerView recyclerView;
  //  UserNeedHandler handler;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_need);
       newHandler=new NewUserNeedHandler(this);
       // dataModels=(ArrayList<Need>)getIntent().getSerializableExtra("list");
       // dataModels=(ArrayList<DefaultNeed>)getIntent().getSerializableExtra("list");
       dataModels=newHandler.getAllContacts();
       adapter=new NeedUserAdapter(dataModels,this);
       //adapter=new UserNeedAdapter(dataModels,this);
       listview=(ListView)findViewById(R.id.listview1) ;
       listview.setAdapter(adapter);

       listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               //Need dataModel= dataModels.get(position);
               DefaultNeed dataModel= dataModels.get(position);
              // String name=dataModel.getPlaces();
               String name=dataModel.getPlaces_default();
               Intent intent=new Intent(UserNeeds.this,MapActivity.class);
               intent.putExtra("places",name);
               String str= String.valueOf(name.charAt(0));
               intent.putExtra("words",str);
               startActivity(intent);
               Log.e("Name",name+"str"+str);
           }
       });
       initToolbar();
   }


    void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView header=(TextView)mToolbar.findViewById(R.id.header);
        header.setText("User Need");
        ImageView click=(ImageView)mToolbar.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserNeeds.this,ActivityNav.class);
                startActivity(intent);
            }
        });

    }


}
