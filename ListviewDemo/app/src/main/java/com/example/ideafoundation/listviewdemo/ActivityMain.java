package com.example.ideafoundation.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 21/02/17.
 */

public class ActivityMain extends AppCompatActivity {
ListView listview;
    ArrayList<Model> arrayList;
    MyAdapter adapter;
    private static final int REQUEST_CODE = 10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=(ListView)findViewById(R.id.list1);
        arrayList=new ArrayList<>();

        arrayList.add(new Model("accounting"));
        arrayList.add(new Model("airport"));
        arrayList.add(new Model("amusement_park"));
        arrayList.add(new Model("aquarium"));
        arrayList.add(new Model("art_gallery"));
        arrayList.add(new Model("atm"));
        arrayList.add(new Model("bakery"));
        arrayList.add(new Model("bank"));
        arrayList.add(new Model("bar"));
        arrayList.add(new Model("beauty_salon"));
        arrayList.add(new Model("bicycle_store"));
        arrayList.add(new Model("book_store"));
        arrayList.add(new Model("bowling_alley"));
        arrayList.add(new Model("bus_station"));
        arrayList.add(new Model("cafe"));
        arrayList.add(new Model("campground"));
        arrayList.add(new Model("car_dealer"));
        arrayList.add(new Model("car_rental"));
        arrayList.add(new Model("car_wash"));
        arrayList.add(new Model("casino"));
        arrayList.add(new Model("cemetery"));
        arrayList.add(new Model("church"));
        arrayList.add(new Model("city_hall"));
        arrayList.add(new Model("clothing_store"));
        arrayList.add(new Model("convenience_store"));
       adapter=new MyAdapter(this,arrayList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ActivityMain.this, DeleteActivity.class);
                i.putExtra("position", position);
                i.putExtra("text",arrayList.get(position).getName());
                startActivityForResult(i, REQUEST_CODE);

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("returnkey")) {
                int result = data.getExtras().getInt("returnkey");
                if (result >= 0 ) {
                    adapter.remove(result);
                }
            }
        }
    }
}
