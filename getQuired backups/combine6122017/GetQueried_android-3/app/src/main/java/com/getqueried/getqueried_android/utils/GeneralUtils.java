package com.getqueried.getqueried_android.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.registration.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Gaurav on 9/4/2016.
 */
public class GeneralUtils {

    private static final String TAG = "GeneralUtils";

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void routeWithFinish(AppCompatActivity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void routeAndClearBackStack(Activity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void route(AppCompatActivity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
    }

    public static void setToolbarTitle(AppCompatActivity activity, String HEADER_TITLE) {
        TextView headerTitle;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.app_bar);
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        if (toolbar != null) {
            headerTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            headerTitle.setText(HEADER_TITLE);
        }
    }

    public static void setToolbarBackButton(AppCompatActivity currentActivity, Class<?> resultActivity) {
        Toolbar toolbar = (Toolbar) currentActivity.findViewById(R.id.app_bar);
        currentActivity.setSupportActionBar(toolbar);
        if (currentActivity.getSupportActionBar() != null)
            currentActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(currentActivity.getBaseContext(), resultActivity);
                currentActivity.startActivity(intent);
                currentActivity.finish();
            });
        }
    }

    public static void setToolbarBackButton(AppCompatActivity currentActivity) {
        Toolbar toolbar = (Toolbar) currentActivity.findViewById(R.id.app_bar);
        currentActivity.setSupportActionBar(toolbar);
        if (currentActivity.getSupportActionBar() != null)
            currentActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> {
                currentActivity.finish();
            });
        }
    }

    public static void closeActivityButton(AppCompatActivity currentActivity) {
        Toolbar toolbar = (Toolbar) currentActivity.findViewById(R.id.app_bar);
        currentActivity.setSupportActionBar(toolbar);
        if (currentActivity.getSupportActionBar() != null)
            currentActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close);
            toolbar.setNavigationOnClickListener(v -> {
                currentActivity.finish();
            });
        }
    }

    public static void parseJwtToken(Context context, String accessToken, SharedPrefUtils.UserProfile userProfile) {
        String[] tokenArray = accessToken.split(Pattern.quote("."));
        byte[] payloadDataBase64 = Base64.decode(tokenArray[1], Base64.DEFAULT);
        Crashlytics.log(Log.DEBUG, TAG, "Base64 : " + new String(payloadDataBase64));
        try {
            JSONObject jsonObject = new JSONObject(new String(payloadDataBase64));
            userProfile.saveJwtTokenPayloadData(
                    jsonObject.getLong(Constants.TokenPayload.EXP),
                    jsonObject.getLong(Constants.TokenPayload.IAT),
                    jsonObject.getString(Constants.TokenPayload.ISS),
                    jsonObject.getString(Constants.TokenPayload.KID),
                    jsonObject.getString(Constants.TokenPayload.UUID)
            );
            SharedPrefUtils.saveUserProfile(context, userProfile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void logout(Activity activity) {
        SharedPrefUtils.getUserPref(activity).edit().clear().apply();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void hideSoftKeyboard(AppCompatActivity activity, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Bitmap retrieveContactPhoto(Context context, String contactID) {
        Bitmap photo = null;
        InputStream inputStream = null;
        try {
            inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactID)));
            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return photo;
    }

    // IMAGE PROCESSING methods
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "GET_QUERIED_" + timeStamp + "_";
        // set folder name
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/Pictures");
        // create folder if it doesn't exist
        if (!storageDir.exists()) {
            boolean isCreated = storageDir.mkdir();

            if (!isCreated) {
                return null;
            }
        }
        //create empty file in folder
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static byte[] getByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static void fixImageOrientation(String imagePath) {
        int orientation = 0;
        try {
            Crashlytics.log(Log.DEBUG, TAG, "Image Path inside fixImageOrientation : " + imagePath);
            orientation = GeneralUtils.getImageOrientation(imagePath);
            // Handling special case for phones which takes photos in landscape mode
            Bitmap imageBitmap = GeneralUtils.rotateImage(imagePath, orientation);
            Crashlytics.log(Log.DEBUG, TAG, "Image Bitmap : " + imageBitmap);
            GeneralUtils.writeBitmap(imageBitmap, imagePath, 85);
        } catch (IOException e) {
            Crashlytics.log(Log.ERROR, TAG, "Exif Info not available. " + e.getMessage());
        }
    }

    /**
     * For big files it's better to use this method on compressed bitmap instead of fixImageOrientation(String).
     * @param bitmap - compressed bitmap of file
     * @param pathToBitmap - full file path
     * @return rotated bitmap, null if Exception during.
     */
    public static Bitmap fixImageOrientation(Bitmap bitmap, String pathToBitmap) {
        Bitmap res = null;
        try {
            int orientation = getImageOrientation(pathToBitmap);
            res = GeneralUtils.rotateImage(bitmap, orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int getImageOrientation(String photoPath) throws IOException {
        ExifInterface ei = new ExifInterface(photoPath);
        return ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
    }

    public static Bitmap rotateImage(String path, int orientation) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return rotateImage(bitmap, orientation);
    }

    public static Bitmap rotateImage(Bitmap source, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return source;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return source;
        }

        Bitmap retVal;
        matrix.postRotate(orientation);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    public static Bitmap nativeRotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    public static void writeBitmap(Bitmap source, String path, int compression) {
        OutputStream fOut = null;
        File file = new File(path);
        try {
            fOut = new FileOutputStream(file);
            // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            source.compress(Bitmap.CompressFormat.JPEG, compression, fOut);
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    /*public static void setNavHeaderUserName(Context context, NavigationView mNavigationView){
        View navHeaderView = mNavigationView.getHeaderView(0);
        TextView navUserName = (TextView) navHeaderView.findViewById(R.id.nav_userName);
        navUserName.setText(SharedPrefUtils.getUserPref(context).getString(Constants.FIRSTNAME, null));
    }*/
}
