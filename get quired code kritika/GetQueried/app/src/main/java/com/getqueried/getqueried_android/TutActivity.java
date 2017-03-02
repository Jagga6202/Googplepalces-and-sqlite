package com.getqueried.getqueried_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.app.Activity;
import android.widget.Toast;

import com.getqueried.getqueried_android.dashboard.DashboardActivity;

public class TutActivity extends Activity {
ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tut);
        ImageView myImage = (ImageView) findViewById(R.id.myImage);
        int opacity = 200; // from 0 to 255
        myImage.setBackgroundColor(opacity * 0x1000000);
        Intent intent=new Intent(TutActivity.this, DashboardActivity.class);
        intent.putExtra("popshow", true);
        startActivity(intent);
       /* Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.activity_tut, null));
        settingsDialog.show();*/
    }

}