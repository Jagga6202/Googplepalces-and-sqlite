package com.example.ideafoundation.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ideafoundation on 21/02/17.
 */

public class DeleteActivity extends AppCompatActivity {
TextView delete;
    ImageView delteimage;
    int key;
    String text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        delete=(TextView)findViewById(R.id.deletetext);
        delteimage=(ImageView)findViewById(R.id.deleteimage);
        Bundle extras = getIntent().getExtras();
        key=extras.getInt("position");
        text=extras.getString("text");
        delete.setText("text");
        delteimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    @Override
    public void finish() {
        Intent intent = new Intent();

        intent.putExtra("returnkey", key);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
