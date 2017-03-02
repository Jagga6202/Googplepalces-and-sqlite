package com.getqueried.getqueried_android.create_query;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.getqueried.getqueried_android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditImageOptionActivity extends AppCompatActivity {

    @Bind(R.id.imageView_option)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image_option);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] byteArray = bundle.getByteArray("ImageDrawable");
            if (byteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(bmp);
            }

        }
    }
}