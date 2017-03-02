package com.getqueried.getqueried_android.cameraAPI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.create_query.fragments.ImgOptQueryFragment;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaptureActivityCAPI1 extends Activity {

    private static final String TAG = "CamCaptActivity";
    private static final int MAX_IMAGES_AS_OPTION = 12;
    private static final int SELECT_PHOTO = 1;

    private Camera mCamera;
    public int currCameraID = 0;
    private CameraPreview mCameraPreview;

    @Bind(R.id.camera_preview)
    public FrameLayout previewFrame;

    @Bind(R.id.options)
    LinearLayout optionsLayout;

    private int imagesSelected = 0;
    private ArrayList<ImageButton> options = new ArrayList<>(MAX_IMAGES_AS_OPTION);
    private ArrayList<String> optionPaths = new ArrayList<>(MAX_IMAGES_AS_OPTION);
    @Bind(R.id.option1)
    public ImageButton option1;
    @Bind(R.id.option2)
    public ImageButton option2;
    @Bind(R.id.option3)
    public ImageButton option3;
    @Bind(R.id.option4)
    public ImageButton option4;
    @Bind(R.id.option5)
    public ImageButton option5;
    @Bind(R.id.option6)
    public ImageButton option6;
    @Bind(R.id.option7)
    public ImageButton option7;
    @Bind(R.id.option8)
    public ImageButton option8;
    @Bind(R.id.option9)
    public ImageButton option9;
    @Bind(R.id.option10)
    public ImageButton option10;
    @Bind(R.id.option11)
    public ImageButton option11;
    @Bind(R.id.option12)
    public ImageButton option12;

    @Bind(R.id.changeSplash)
    public Button changeFlash;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make activity FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera_capture_capi1);
        ButterKnife.bind(this);

        // get screen size
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Crashlytics.log(Log.INFO, TAG, "DisplayMetrix are :"
                + "\ndensityDpi " + metrics.densityDpi
                + "\ndensity " + metrics.density
                + "\nheightPx " + metrics.heightPixels
                + "\nwidthPx " + metrics.widthPixels);

        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
        options.add(option5);
        options.add(option6);
        options.add(option7);
        options.add(option8);
        options.add(option9);
        options.add(option10);
        options.add(option11);
        options.add(option12);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkCameraHardware(this)) {
            Toast.makeText(this, "There is no camera on device", Toast.LENGTH_SHORT).show();
            Crashlytics.log(Log.DEBUG, TAG, "There is no camera on device.");
            setResult(RESULT_CANCELED);
            finish();
        }

        mCamera = getCameraInstance();
        if (mCamera == null) {
            Crashlytics.log(Log.DEBUG, TAG, "Camera object is null.");
            setResult(RESULT_CANCELED);
            finish();
        } else {
            mCameraPreview = new CameraPreview(this, mCamera);
            previewFrame.addView(mCameraPreview);
        }
    }

    @OnClick(R.id.capture)
    public void capture(View view) {
        mCameraPreview.captureImage();
    }

    @OnClick(R.id.back)
    public void backPress(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.accept)
    public void accept(View v) {
        Intent data = new Intent();
        String[] options = new String[optionPaths.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = optionPaths.get(i);
        }
        data.putExtra("options", options);
        setResult(RESULT_OK, data);
        finish();
    }

    public void bindFromPath(String imagePath) {
        if (imagePath == null) {
            Crashlytics.logException(new Throwable("Bind from path. Path is null"));
            return;
        }
        if (imagesSelected >= MAX_IMAGES_AS_OPTION) {
            return;
        }
        optionsLayout.setVisibility(View.VISIBLE);
        int imageOrientation;
        try {
            imageOrientation = GeneralUtils.getImageOrientation(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(new File(imagePath)).fit().centerInside()
                .placeholder(getResources().getDrawable(R.drawable.photo))
                .into(options.get(imagesSelected));
        imagesSelected++;
        optionPaths.add(imagePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri imageUri = null;
            String imagePath;
            try {
                imageUri = intent.getData();
                imagePath = getRealPathFromURI(this, imageUri);
                Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);

            } catch (Exception e) {
                Crashlytics.log(Log.ERROR, TAG, "Caught Exception message : " + e.getMessage());
                imagePath = getRealPathFromURI_API19(this, imageUri);
                Crashlytics.log(Log.DEBUG, TAG, "Image Path : " + imagePath);
            }
            bindFromPath(imagePath);
        }
    }

    public static String getRealPathFromURI(Context context, Uri imageUri) throws IllegalArgumentException {
        String result;
        Cursor cursor = context.getContentResolver().query(imageUri, null, null, null, null);
        if (cursor == null) {
            result = imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            Crashlytics.log(Log.DEBUG, TAG, " Move first curesor : " + cursor.moveToFirst() + " Idx column : " + idx);
            if (idx == -1) {
                result = imageUri.getPath();
            } else {
                result = cursor.getString(idx);
                cursor.close();
            }
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = null;

        wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = 0;
        if (cursor != null) {
            columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();
        }

        return filePath;
    }

    @OnClick(R.id.chooseGallery)
    public void chooseFromGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @OnClick(R.id.changeCamera)
    public void changeCamera(View v) {
        if (currCameraID == Camera.getNumberOfCameras() - 1) {
            currCameraID = 0;
        } else {
            currCameraID++;
        }
        mCameraPreview.destroy();
        mCamera.release();
        mCamera = Camera.open(currCameraID);
        previewFrame.removeView(mCameraPreview);
        mCameraPreview = new CameraPreview(this, mCamera);
        previewFrame.addView(mCameraPreview);
    }

    @OnClick(R.id.changeSplash)
    public void changeSplashState(View v) {
        String newState = mCameraPreview.changeFlashState();
        if (newState == null) {
            changeFlash.setText(getResources().getString(R.string.flash_off));
            return;
        }
        switch (newState) {
            case Camera.Parameters.FLASH_MODE_OFF:
                changeFlash.setText(getResources().getString(R.string.flash_off));
                break;
            case Camera.Parameters.FLASH_MODE_AUTO:
                changeFlash.setText(getResources().getString(R.string.flash_auto));
                break;
            case Camera.Parameters.FLASH_MODE_ON:
                changeFlash.setText(getResources().getString(R.string.flash_on));
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Crashlytics.log(Log.DEBUG, TAG, "onPause is called.");
        // in case with qr code this objects are not used
        mCameraPreview.destroy();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crashlytics.log(Log.DEBUG, TAG, "onDestroy is called.");
    }

    /**
     * Check if this device has a mCamera
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance() {
        Camera camera = null;
        Camera.CameraInfo info = new Camera.CameraInfo();
        try {
            Crashlytics.log(Log.INFO, TAG, "Device has " + Camera.getNumberOfCameras() + " cameras.");
            currCameraID = 0;
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    Crashlytics.log(Log.INFO, TAG, "Found FACING_BACK camera, id is: " + i);
                    currCameraID = i;
                    break;
                }
            }
            camera = Camera.open(currCameraID); // attempt to get a Camera instance
            Crashlytics.log(Log.DEBUG, TAG, "Camera is received.");
        } catch (Exception e) {
            Toast.makeText(this, "Camera is unavailable", Toast.LENGTH_SHORT).show();
            Crashlytics.log(Log.DEBUG, TAG, "Camera is unavailable.");

        }
        return camera; // returns null if mCamera is unavailable
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
            Crashlytics.log(Log.DEBUG, TAG, "Camera released.");
        }
    }
}
