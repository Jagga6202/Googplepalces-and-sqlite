package com.example.ideafoundation.listcheckboxdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 08/02/17.
 */

public class Newactivity extends AppCompatActivity {
ListView listView;
    ArrayList<Service> filelist;
    NewAdapter newAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        listView=(ListView) findViewById(R.id.listview2);

        filelist=  (ArrayList<Service>)getIntent().getSerializableExtra("list");
        newAdapter=new NewAdapter(this,filelist);
       listView.setAdapter(newAdapter);
        for(int i=0;i<filelist.size();i++)
        {
            Log.e("name",""+filelist.get(i).getName());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("name",""+filelist.get(position).getName());
            }
        });

    }
}
