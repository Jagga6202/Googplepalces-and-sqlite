package com.example.ideafoundation.wheelspinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toast toast;
Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
button=(Button)findViewById(R.id.button);

        final LoopView loopView = (LoopView) findViewById(R.id.loopView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopView.setVisibility(View.VISIBLE);
            }
        });
        final ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            if(i==0)
            {
                list.add("Primary School");
            }
            else if(i==1)
            {
                list.add("High School");
            }
            else if(i==2)
            {
                list.add("Vocational training");
            }
            else if(i==3)
            {
                list.add("Bachelor Degree");

            }
            else if(i==4)
            {
                list.add("Master Degree");
            }


        }


        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
               Toast.makeText(MainActivity.this,list.get(index),Toast.LENGTH_LONG).show();
                loopView.setVisibility(View.GONE);
            }
        });

        loopView.setItems(list);
    }

}
