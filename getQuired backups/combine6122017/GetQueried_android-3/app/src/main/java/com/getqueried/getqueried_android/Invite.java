package com.getqueried.getqueried_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.GameRequestDialog;

public class Invite extends AppCompatActivity {
    GameRequestDialog requestDialog;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager,
                new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        String id = result.getRequestId();
                        //  Toast.makeText(getApplicationContext(),"Send request",Toast.LENGTH_SHORT).show();
                        String appLinkUrl, previewImageUrl;

                        appLinkUrl = "http://getqueried-android.pagedemo.co/?__hstc=152250342.1e67a1dd962fb6e8a2544cef64b9f411.1481670093790.1481757107476.1482481073315.6&__hssc=152250342.1.1482481073315&__hsfp=3213607277";
                        previewImageUrl = "https://pbs.twimg.com/profile_images/737247131371704320/fanGrG3R.jpg";

                        if (AppInviteDialog.canShow()) {
                            AppInviteContent content = new AppInviteContent.Builder()
                                    .setApplinkUrl(appLinkUrl)
                                    .setPreviewImageUrl(previewImageUrl)
                                    .build();
                            AppInviteDialog.show(getParent(), content);
                        }
                    }
                    public void onCancel() {}
                    public void onError(FacebookException error) {}
                }
        );
        GameRequestContent content = new GameRequestContent.Builder()
                .setMessage("Come play this level with me")
                .setTo("1151489514943970") // I will work tomorrow on dynamic FB Id
                .build();
        requestDialog.show(content);
        Log.d("Facebook Test","content"+content);
    }

    private void onClickRequestButton() {
        //Toast.makeText(getApplicationContext(),"Send request",Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

