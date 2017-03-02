package com.getqueried.getqueried_android.create_query;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by just_me on 16.11.16.
 */
public class AddImageDescription extends NetworkActivity {

    private static final String TAG = "AddImageDescription";

    private Intent resultIntent = new Intent();
    private String imagePath;
    private String imageDescription;

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.imageDescription)
    public EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);
        ButterKnife.bind(this);

        // get data
        Bundle data = getIntent().getExtras();
        imagePath = data.getString(Constants.CreateQuery.IMAGE_PATH);
        imageDescription = data.getString(Constants.CreateQuery.IMAGE_DESCRIPTION);

        // update view
        Picasso.with(this).load(new File(imagePath)).resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).centerCrop().into(image);
        description.setText(imageDescription);
    }

    @OnClick(R.id.rotateButton)
    public void rotateButton(View v) {
        Crashlytics.log(Log.DEBUG, TAG, "Image rotated");
        GeneralUtils.rotateImage(imagePath, ExifInterface.ORIENTATION_ROTATE_270);
        Picasso.with(this).load(new File(imagePath)).resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).centerCrop().into(image);
        resultIntent.putExtra(Constants.CreateQuery.IS_IMAGE_ROTATED, true);
    }

    @OnClick(R.id.deleteButton)
    public void deleteImage(View v) {
        resultIntent.putExtra(Constants.CreateQuery.IS_IMAGE_DELETED, true);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @OnClick(R.id.submitDescription)
    public void submitDescription(View v) {
        resultIntent.putExtra(Constants.CreateQuery.IS_IMAGE_DELETED, false);
        resultIntent.putExtra(Constants.CreateQuery.IMAGE_DESCRIPTION, description.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
