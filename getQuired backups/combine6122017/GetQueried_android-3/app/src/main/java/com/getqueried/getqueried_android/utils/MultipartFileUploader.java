package com.getqueried.getqueried_android.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MultipartFileUploader {

    private static final String TAG = "MultipartFileUploader";

    public static String uploadImage(Context context, String requestUrl, JSONObject jsonObject, String imagePath,
                                     Dialog dialog) {
        String charset = "UTF-8";
        File uploadFile1 = new File(imagePath);

        try {
            MultipartUtility multipart = new MultipartUtility(requestUrl, charset);

            multipart.addHeaderField("Content-Type", "multipart/form-data");
            multipart.addHeaderField("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(context).access_token);

            multipart.addFormField("json=", jsonObject.toString());
            multipart.addFilePart("fileUpload", uploadFile1);
            //multipart.addFilePart("fileUpload", uploadFile2);

            List<String> response = multipart.finish();

            Crashlytics.log(Log.DEBUG, TAG, "SERVER REPLIED:");

            for (String line : response) {
                Crashlytics.log(Log.DEBUG, TAG, line);
            }
        } catch (IOException ex) {
            Crashlytics.log(Log.ERROR, TAG, ex.getMessage());
        }

        return null;
    }
}