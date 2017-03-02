package com.getqueried.getqueried_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.webkit.MimeTypeMap;

import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Gaurav on 10/12/2016.
 */
public class ImageUtils {

    private static final String TAG = "ImageUtils";
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1111;
    public static final int SELECT_IMAGE_FROM_GALLERY = 1112;
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_GALLERY = 2;

    private static final String LINE_START = "--";
    private static final String LINE_END = "\r\n";
    private static final String BOUNDARY = "+++++";

    private static String method = "POST";

    // Returns true if image uploaded successfully i.e. response code is 202 else false
    public static boolean uploadProfileImage(Context context, String requestUrl, Bitmap imageBitmap, String imagePath) throws IOException {

        String contentDisposition = "Content-Disposition: form-data; name=\"profileImage\"; filename=\"profileImage.jpg\"";
        String contentType = "Content-Type: application/octet-stream";

        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Crashlytics.log(Log.ERROR, TAG, "Malformed url exception. " + e.getMessage());
        }

        HttpURLConnection httpUrlConnection;
        if (url != null) {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout((int) Constants.ONE_MINUTE);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod(method);
            httpUrlConnection.setChunkedStreamingMode(16 * 1024);
            httpUrlConnection.setRequestProperty("Transfer-Encoding", "chunked");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");

            // Set headers
            httpUrlConnection.setRequestProperty("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(context).access_token);
            httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // Body of the request
            DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());
            request.write(buildStartPayload(contentDisposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            request.write(getImageBytes(getResizedBitmap(imageBitmap, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            request.write(buildEndPayload().getBytes(Constants.CHARSET_UTF_8));

            request.flush();
            request.close();

            // Ensure we got the HTTP 200 response code
            int responseCode = httpUrlConnection.getResponseCode();
            String responseMessage = httpUrlConnection.getResponseMessage();

            InputStream is;
            // Read the response
            if (responseCode != 202) {
                Crashlytics.log(Log.ERROR, TAG, String.format("Received the response code %d from the Response message %s : ",
                        responseCode, responseMessage));
                is = httpUrlConnection.getErrorStream();
            } else {
                is = httpUrlConnection.getInputStream();
                Crashlytics.log(Log.DEBUG, TAG, String.format("Received the response code %d from the Response message %s : ",
                        responseCode, responseMessage));
                return true;
            }

            // Read the response
            if (is != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(bytes)) != -1) {
                    baos.write(bytes, 0, bytesRead);
                }
                byte[] bytesReceived = baos.toByteArray();
                baos.close();
                is.close();
                String responseString = new String(bytesReceived);
                Crashlytics.log(Log.DEBUG, TAG, "Profile Image upload response : " + responseString);
            }

            httpUrlConnection.disconnect();
        }
        return false;
    }

    public static boolean uploadImageQuery(Context context, String urlString, String method,
                                           JSONObject jsonObject, Bitmap largeImageBitmap) throws IOException {

        String contentDisposition = "Content-Disposition: form-data; name=\"questionImage-0\"; " +
                "filename=\"questionImage-0.jpg\"";
        String contentType = "Content-Type: application/octet-stream";

        String jsonContentDisposition = "Content-Disposition: form-data; name=\"json\"";
        String jsonContentType = "Content-Type: application/json";

        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout((int) Constants.ONE_MINUTE);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setChunkedStreamingMode(16 * 1024);
        httpURLConnection.setRequestProperty("Transfer-Encoding", "chunked");

        // Set Headers
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(context).access_token);
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        // Set body of the request
        DataOutputStream dataOS = new DataOutputStream(httpURLConnection.getOutputStream());
        Crashlytics.log(Log.DEBUG, TAG, "Json object body : " + jsonObject.toString());
        dataOS.write(buildJsonPayload(jsonContentDisposition, jsonContentType,
                jsonObject.toString()).getBytes(Constants.CHARSET_UTF_8));
        dataOS.write(LINE_END.getBytes());
        if (largeImageBitmap != null) {
            dataOS.write(buildStartPayload(contentDisposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(largeImageBitmap, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END + LINE_END).getBytes());
        }
        dataOS.write(buildEndPayload().getBytes(Constants.CHARSET_UTF_8));

        Crashlytics.log(Log.DEBUG, TAG, "OutputStram body : " + dataOS.toString());

        dataOS.flush();
        dataOS.close();

        // Ensure we got the HTTP 200 response code
        int responseCode = httpURLConnection.getResponseCode();
        String responseMessage = httpURLConnection.getResponseMessage();
        Crashlytics.log(Log.DEBUG, TAG, "Response code for upload image query : " + responseCode + " Message : " + responseMessage);

        // Read the response
        InputStream is;
        if (responseCode != 202) {
            Crashlytics.log(Log.ERROR, TAG, String.format("Received the response code %d from the URL %s", responseCode, url));
            is = httpURLConnection.getErrorStream();
        } else {
            Crashlytics.log(Log.DEBUG, TAG, String.format("Received the response code %d from the URL %s", responseCode, url));
            is = httpURLConnection.getInputStream();
            return true;
        }

        if (is != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(bytes)) != -1) {
                baos.write(bytes, 0, bytesRead);
            }
            byte[] bytesReceived = baos.toByteArray();
            baos.close();
            is.close();

            String response = new String(bytesReceived);
            Crashlytics.log(Log.DEBUG, TAG, "Response:" + response);
        }

        httpURLConnection.disconnect();
        return false;
    }

    private static File createImageFile() throws IOException {
        File root = android.os.Environment.getExternalStorageDirectory();
        Calendar c = Calendar.getInstance();
        String imageFileName = "log" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE)
                + c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".log";
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
    
    public static boolean uploadImpOptQueryWithImage(Context context, String urlString, String method,
                                                     JSONObject jsonObject, Bitmap imageBitmap,
                                                     Bitmap imageBitmap1, Bitmap imageBitmap2, Bitmap imageBitmap3,
                                                     Bitmap imageBitmap4) throws IOException {

        File log = createImageFile();
        FileOutputStream logStream =  new FileOutputStream(log);

        String questionImageDisposition = "Content-Disposition: form-data; name=\"questionImage-0\"; " +
                "filename=\"questionImage-0.jpg\"";
        String answerOptionImage0Disposition = "Content-Disposition: form-data; name=\"answerOptionImage-0-0\"; " +
                "filename=\"answerOptionImage-0-0.jpg\"";
        String answerOptionImage1Disposition = "Content-Disposition: form-data; name=\"answerOptionImage-0-1\"; " +
                "filename=\"answerOptionImage-0-1.jpg\"";
        String answerOptionImage2Disposition = "Content-Disposition: form-data; name=\"answerOptionImage-0-2\"; " +
                "filename=\"answerOptionImage-0-2.jpg\"";
        String answerOptionImage3Disposition = "Content-Disposition: form-data; name=\"answerOptionImage-0-3\"; " +
                "filename=\"answerOptionImage-0-3.jpg\"";
        String contentType = "Content-Type: application/octet-stream";

        String jsonContentDisposition = "Content-Disposition: form-data; name=\"json\"";
        String jsonContentType = "Content-Type: application/json";

        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        logStream.write((urlString + LINE_END).getBytes(Constants.CHARSET_UTF_8));

        httpURLConnection.setConnectTimeout((int) Constants.ONE_MINUTE);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setChunkedStreamingMode(16 * 1024);
        httpURLConnection.setRequestProperty("Transfer-Encoding", "chunked");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
        logStream.write(("timeout: " + Constants.ONE_MINUTE + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("doOutput: " + true + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("doInput: " + true + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("useCaches: " + false + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("method: " + method + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("chunkedStreamingMode: " + 16*1024 + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("Transfer-Encoding:" + "chunked" + LINE_END).getBytes(Constants.CHARSET_UTF_8));

        // Set Headers
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + SharedPrefUtils.getUserProfile(context).access_token);
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        logStream.write(("Authorization:" + "Bearer " + SharedPrefUtils.getUserProfile(context).access_token + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(("Content-Type:" + "multipart/form-data; boundary=" + BOUNDARY + LINE_END).getBytes(Constants.CHARSET_UTF_8));

        // Set body of the request
        DataOutputStream dataOS = new DataOutputStream(httpURLConnection.getOutputStream());
        // JSON body
        dataOS.write(buildJsonPayload(jsonContentDisposition, jsonContentType,
                jsonObject.toString()).getBytes(Constants.CHARSET_UTF_8));
        logStream.write(buildJsonPayload(jsonContentDisposition, jsonContentType,
                jsonObject.toString()).getBytes(Constants.CHARSET_UTF_8));


        // Question Image if available
        if (imageBitmap != null) {
            dataOS.write(buildStartPayload(questionImageDisposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(imageBitmap, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END + LINE_END).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(buildStartPayload(questionImageDisposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(("<ImageData>" + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        }
        //AnswerOption0 Image
        if (imageBitmap1 != null) {
            dataOS.write(buildStartPayload(answerOptionImage0Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(imageBitmap1, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END + LINE_END).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(buildStartPayload(answerOptionImage0Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(("<ImageData>" + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        }
        //AnswerOption1 Image
        if (imageBitmap2 != null) {
            dataOS.write(buildStartPayload(answerOptionImage1Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(imageBitmap2, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END + LINE_END).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(buildStartPayload(answerOptionImage1Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(("<ImageData>" + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        }
        //AnswerOption2 Image
        if (imageBitmap3 != null) {
            dataOS.write(buildStartPayload(answerOptionImage2Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(imageBitmap3, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END + LINE_END).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(buildStartPayload(answerOptionImage2Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(("<ImageData>" + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        }
        //AnswerOption3 Image
        if (imageBitmap4 != null) {
            dataOS.write(buildStartPayload(answerOptionImage3Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            dataOS.write(getImageBytes(getResizedBitmap(imageBitmap4, Constants.IMG_WIDTH, Constants.IMG_HEIGHT)));
            dataOS.write((LINE_END).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(buildStartPayload(answerOptionImage3Disposition, contentType).getBytes(Constants.CHARSET_UTF_8));
            logStream.write(("<ImageData>" + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        }

        dataOS.write(buildEndPayload().getBytes(Constants.CHARSET_UTF_8));
        logStream.write(buildEndPayload().getBytes(Constants.CHARSET_UTF_8));

        dataOS.flush();
        dataOS.close();

        // Ensure we got the HTTP 200 response code
        int responseCode = httpURLConnection.getResponseCode();
        logStream.write(("ResponseCode: " + responseCode + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        String responseMessage = httpURLConnection.getResponseMessage();
        logStream.write(("ResponseMessage: " + responseMessage + LINE_END).getBytes(Constants.CHARSET_UTF_8));
        Crashlytics.log(Log.DEBUG, TAG, "Response code for upload image query with answerOptions : " + responseCode + " Message : " + responseMessage);

        // Read the response
        InputStream is;
        if (responseCode != 202) {
            Crashlytics.log(Log.ERROR, TAG, String.format("Received the response code %d from the URL %s", responseCode, url));
            is = httpURLConnection.getErrorStream();
        } else {
            Crashlytics.log(Log.DEBUG, TAG, String.format("Received the response code %d from the URL %s", responseCode, url));
            is = httpURLConnection.getInputStream();
            return true;
        }

        if (is != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(bytes)) != -1) {
                baos.write(bytes, 0, bytesRead);
            }
            byte[] bytesReceived = baos.toByteArray();
            baos.close();
            is.close();

            String response = new String(bytesReceived);
            logStream.write(("response: " + response + LINE_END).getBytes(Constants.CHARSET_UTF_8));
            Crashlytics.log(Log.DEBUG, TAG, "Response:" + response);
        }

        httpURLConnection.disconnect();
        logStream.flush();
        logStream.close();
        return false;
    }


    private static String buildJsonPayload(String contentDisposition, String contentType, String jsonString) {
        // This is the standard format for a multipart request
        Crashlytics.log(Log.DEBUG, TAG, "Building json payload !!");
        StringBuilder requestBody = new StringBuilder();
        requestBody.append(LINE_START);
        requestBody.append(BOUNDARY);
        requestBody.append(LINE_END);
        requestBody.append(contentDisposition);
        requestBody.append(LINE_END);
        requestBody.append(contentType);
        requestBody.append(LINE_END);
        requestBody.append(LINE_END);
        requestBody.append(jsonString);
        requestBody.append(LINE_END);
        Crashlytics.log(Log.DEBUG, TAG, "Start form : " + requestBody);
        return requestBody.toString();
    }

    private static String buildStartPayload(String contentDisposition, String contentType) {
        // This is the standard format for a multipart request
        StringBuilder requestBody = new StringBuilder();
        requestBody.append(LINE_START);
        requestBody.append(BOUNDARY);
        requestBody.append(LINE_END);
        requestBody.append(contentDisposition);
        requestBody.append(LINE_END);
        requestBody.append(contentType);
        requestBody.append(LINE_END);
        requestBody.append(LINE_END);
        Crashlytics.log(Log.DEBUG, TAG, "Start form : " + requestBody);
        return requestBody.toString();
    }

    private static String buildEndPayload() {
        // This is the standard format for a multipart request
        StringBuilder requestBody = new StringBuilder();
        requestBody.append(LINE_END + LINE_START + BOUNDARY + LINE_START + LINE_END + LINE_END);
        Crashlytics.log(Log.DEBUG, TAG, "End : " + requestBody.toString());
        return requestBody.toString();
    }

    private static byte[] getImageBytes(Bitmap image) {
        // build the image data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // url = file path or whatever suitable URL you want.
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
    }

    public static Bitmap decodeBitmapFromPath(String imagePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int getRoatationAngle(Activity mContext, int cameraId) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    public static String intentCamera(Activity activity, int code) {
        Crashlytics.log(Log.DEBUG, TAG, "Starting camera intent.");
        String selectedPhotoPath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = GeneralUtils.createImageFile();
                if (photoFile == null) {
                    return null;
                }
                selectedPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                activity.startActivityForResult(takePictureIntent, code);
            }
        }
        return selectedPhotoPath;
    }

    public static void intentGallery(Activity activity, int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, code);
        }
    }
}
