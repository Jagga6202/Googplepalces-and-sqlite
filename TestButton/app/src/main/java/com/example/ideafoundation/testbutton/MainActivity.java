package com.example.ideafoundation.testbutton;

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn,btn2;
    ImageView img;
    boolean isText=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn1);
        img=(ImageView)findViewById(R.id.imageView);
        btn.setOnClickListener(this);

    }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn1:

                if(!isText)
                {
                    img.setBackground(getDrawable(R.drawable.closeblack));
                    btn.setText("Hello");
                    isText=true;
                }
                else
                {
                    img.setBackground(getDrawable(R.drawable.maleactive));
                    btn.setText("Click");
                    isText=false;
                }
                break;

        }
    }
}
