package com.getqueried.getqueried_android.cameraAPI;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.services.common.Crash;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A basic Camera preview class
 * created by Ostap on 21.01.16
 */
@SuppressLint("ViewConstructor")
@SuppressWarnings("deprecation")
@TargetApi(16)
class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private static final String TAG = "CameraPreview";
    private static final int FOCUS_AREA_SIZE = 50;

    private SurfaceHolder mHolder;
    private Activity mActivity;
    private Camera mCamera;
    private Handler handler = new Handler();

    // for preview size
    // as default, will be changed
    int measuredWidth = 1280, measuredHeight = 720;
    List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;
    private int angleToRotate = 0;
    private Rect lastFocusArea = calculateFocusArea(0, 0);
    private volatile boolean isCapturing = false;
    private volatile boolean isCapturingFirstFailed = false;

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        File createImageFile() throws IOException {
            File root = android.os.Environment.getExternalStorageDirectory();
            Calendar c = Calendar.getInstance();
            String imageFileName = "capturedPhoto" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE)
                    + c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".jpg";
            File images = new File(root.getAbsolutePath() + "/getQueried");
            boolean succ = images.mkdirs();
            Crashlytics.log(Log.DEBUG, TAG, "Image file created in " + succ + images.getAbsolutePath());
            File image = new File(images.getAbsolutePath(), imageFileName);
            if (!image.exists()) {
                succ = image.createNewFile();
                Crashlytics.log(Log.DEBUG, TAG, "Image file creation success : " + succ);
            }
            return image;
        }

        void restartPreview() {
            mCamera.startPreview();
            Crashlytics.log(Log.DEBUG, TAG, "Preview stopped, callback disconnected.");
        }

        @Override
        public void onPictureTaken(byte[] imgData, Camera camera) {
            Runnable storePicture = () -> {
                Crashlytics.log(Log.DEBUG, TAG, "onPictureTaken Entered");
                if (imgData == null) {
                    Crashlytics.log(Log.ERROR, TAG, "onPictureTaken: Image data is null, nothing to save.");
                    return;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long start = System.nanoTime();

                FileOutputStream stream = null;
                File mImageFile = null;
                try {
                    Bitmap originalImage = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                    originalImage = GeneralUtils.nativeRotate(originalImage, (((CaptureActivityCAPI1) mActivity).currCameraID == 0) ? angleToRotate : 360 - angleToRotate);
                    mImageFile = createImageFile();
                    stream = new FileOutputStream(mImageFile);
                    stream.write(GeneralUtils.getByteArray(originalImage));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (stream != null) {
                            stream.flush();
                            stream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Crashlytics.log(Log.DEBUG, TAG, "File created in " + Long.toString(System.nanoTime() - start) + " in thread " + Thread.currentThread());

                    Intent data = new Intent();
                    String path;
                    if (mImageFile == null) {
                        path = null;
                    } else {
                        path = mImageFile.getAbsolutePath();
                    }
                    data.putExtra("image", path);
                    mActivity.setResult(Activity.RESULT_OK, data);
                    mActivity.runOnUiThread(() -> ((CaptureActivityCAPI1) mActivity).bindFromPath(path));
                }
            };
            Thread thread = new Thread(storePicture);
            thread.setPriority(1);
            thread.setName("Picture saver");
            thread.start();
            mCamera.startPreview();
        }
    };

    public CameraPreview(Activity activity, Camera camera) {
        super(activity);
        mActivity = activity;
        mCamera = camera;

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mHolder = getHolder();
        mHolder.addCallback(this);
        setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                focusOnTouch(event);
            }
            return true;
        });
    }

    private void focusOnTouch(MotionEvent event) {
        if (mCamera != null) {
            if (!isFocusAvailable()) {
                Crashlytics.log(Log.INFO, TAG, "Focus is unavailable");
                return;
            }
            lastFocusArea = calculateFocusArea(event.getX(), event.getY());
            focusOn(lastFocusArea);

        } else {
            Crashlytics.log(Log.ERROR, TAG, "mCamera is null when trying to focus in API1");
        }
    }

    private boolean isFocusAvailable() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getFlashMode() != null;
    }

    private void focusOn(Rect focusRect) {
        if (!isFocusAvailable()) {
            Crashlytics.log(Log.INFO, TAG, "Focus is unavailable");
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Area> focusAreasArray = new ArrayList<>();
        focusAreasArray.add(new Camera.Area(focusRect, 1000));

        parameters.setFocusAreas(focusAreasArray);
        mCamera.setParameters(parameters);
        try {
            mCamera.autoFocus(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Crashlytics.log(Log.DEBUG, TAG, "Focus set on: " + focusRect.toString());
    }

    private Rect calculateFocusArea(float x, float y) {
        int left = clamp(Float.valueOf((x / this.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf((y / this.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }

    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
        int result;
        if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
            if (touchCoordinateInCameraReper > 0) {
                result = 1000 - focusAreaSize / 2;
            } else {
                result = -1000 + focusAreaSize / 2;
            }
        } else {
            result = touchCoordinateInCameraReper - focusAreaSize / 2;
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Crashlytics.log(Log.DEBUG, TAG, "Entered onMeasure");
        Crashlytics.log(Log.DEBUG, TAG, "params are: " + Integer.toString(widthMeasureSpec) + "; " + Integer.toString(heightMeasureSpec));
        measuredWidth = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        measuredHeight = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Crashlytics.log(Log.DEBUG, TAG, "width and height are:" + Integer.toString(measuredWidth) + "; " + Integer.toString(measuredHeight));
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Crashlytics.log(Log.DEBUG, TAG, "entered surfaceCreated");
        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, measuredWidth, measuredHeight);
            Crashlytics.log(Log.DEBUG, TAG, "preview size set to: " + mPreviewSize.height + " x " + mPreviewSize.width);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Crashlytics.log(Log.INFO, TAG, "Entered surfaceChanged");
        Crashlytics.log(Log.DEBUG, TAG, "Params are: " + holder.toString() + "; " + Integer.toString(format) + "; " + Integer.toString(width) + "; " + Integer.toString(height));
        if (mHolder.getSurface() == null) {
            Crashlytics.log(Log.DEBUG, TAG, "surface does not exist");
            return;
        }

        if (mCamera == null) {
            Crashlytics.log(Log.DEBUG, TAG, "camera is null");
            return;
        }

        Camera.Parameters parameters = mCamera.getParameters();
        angleToRotate = ImageUtils.getRoatationAngle(mActivity, ((CaptureActivityCAPI1) mActivity).currCameraID);
        mCamera.setDisplayOrientation(angleToRotate);
        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Collections.sort(sizes, (lhs, rhs) -> lhs.width * lhs.height - rhs.width * rhs.height);
        Camera.Size size = null;
        Camera.Size iter;
        for (int i = 0; i < sizes.size() && size == null; i++) {
            iter = sizes.get(i);
            if (iter.width > Constants.IMG_WIDTH && iter.height > Constants.IMG_HEIGHT) {
                size = iter;
            }
        }
        if (size == null) {
            size = sizes.get(sizes.size()-1);
        }
        parameters.setPictureSize(size.width, size.height);
        parameters.setJpegQuality(100);
        setAutoFocus();
        try {
            try {
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            Crashlytics.log(Log.DEBUG, TAG, "Camera preview started success!");
        } catch (Exception e) {
            Crashlytics.log(Log.DEBUG, TAG, "Camera preview started UNsuccess!");
            e.printStackTrace();
        }
    }

    // called by Activity
    public void destroy() {
        mHolder.removeCallback(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Crashlytics.log(Log.DEBUG, TAG, "surfaceDestroyed");
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int targetWidth, int targetHeight) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) targetHeight / targetWidth;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            if (isCapturing) {
                isCapturing = false;
                isCapturingFirstFailed = false;
                makePhoto();
            }
            Crashlytics.log(Log.DEBUG, TAG, "Focus success!");
        } else {
            if (isCapturing) {
                isCapturingFirstFailed = true;
            }
            Crashlytics.log(Log.DEBUG, TAG, "Focus failed!");
        }
        if (isCapturing && isCapturingFirstFailed) {
            isCapturing = false;
            isCapturingFirstFailed = false;
            makePhoto();
        }
    }

    private void setAutoFocus() {
        if (!isFocusAvailable()) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> modes = parameters.getSupportedFocusModes();
        if (modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        } else {
            if (modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            } else {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
        }
        parameters.setFocusAreas(null);
        mCamera.setParameters(parameters);
    }

    private void makePhoto() {
        mCamera.takePicture(null, null, pictureCallback);
        setAutoFocus();
    }

    public void captureImage() {
        if (isFocusAvailable()) {
            isCapturing = true;
            isCapturingFirstFailed = false;
            focusOn(lastFocusArea);
        } else {
            makePhoto();
        }
    }

    public String changeFlashState() {
        Camera.Parameters parameters = mCamera.getParameters();
        String flashState = parameters.getFlashMode();
        if (flashState == null) {
            return null;
        }
        String newState = null;
        switch (flashState) {
            case Camera.Parameters.FLASH_MODE_OFF:
                newState = Camera.Parameters.FLASH_MODE_AUTO;
                break;
            case Camera.Parameters.FLASH_MODE_AUTO:
                newState = Camera.Parameters.FLASH_MODE_ON;
                break;
            case Camera.Parameters.FLASH_MODE_ON:
                newState = Camera.Parameters.FLASH_MODE_OFF;
                break;
        }
        parameters.setFlashMode(newState);
        mCamera.setParameters(parameters);
        return newState;
    }
}
