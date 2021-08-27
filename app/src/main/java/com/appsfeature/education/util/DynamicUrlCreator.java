package com.appsfeature.education.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.player.util.YTUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.reflect.TypeToken;
import com.helper.callback.Response;
import com.helper.util.BaseDynamicUrlCreator;
import com.helper.util.BaseUtil;
import com.pdfviewer.util.PDFDynamicShare;

import java.util.HashMap;

public class DynamicUrlCreator extends BaseDynamicUrlCreator {

    public static final String TYPE_VIDEO = "type_video";
    private Response.Progress progressListener;

    public DynamicUrlCreator(Context context) {
        super(context);
    }

    public static void openActivity(Activity activity, Uri url, String extraData) {
        if(url != null){
            if(url.getQueryParameter(ACTION_TYPE).equals(PDFDynamicShare.TYPE_PDF)) {
                PDFDynamicShare.open(activity, url, extraData);
            }else if(url.getQueryParameter(ACTION_TYPE).equals(TYPE_VIDEO)) {
                //Handle your module event here.
                ExtraProperty extraProperty = fromJson(extraData, new TypeToken<ExtraProperty>(){});
                if (extraProperty != null) {
                    YTUtility.playVideo(activity, extraProperty);
                }
            }
        }
    }

    public void shareVideo(String id, ExtraProperty extraData, String description) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", id);
        param.put(ACTION_TYPE, TYPE_VIDEO);
        String extraDataJson = toJson(extraData, new TypeToken<ExtraProperty>() {
        });
        showProgress(View.VISIBLE);
        generate(param, extraDataJson, new DynamicUrlCreator.DynamicUrlCallback() {
            @Override
            public void onDynamicUrlGenerate(String url) {
                showProgress(View.GONE);
                Log.d(DynamicUrlCreator.class.getSimpleName(), "Url:" + url);
                shareMe(description, url);
            }

            @Override
            public void onError(Exception e) {
                showProgress(View.GONE);
                Log.d(DynamicUrlCreator.class.getSimpleName(), "onError:" + e.toString());
                shareMe(description, getPlayStoreLink());
            }
        });
    }

    private String getPlayStoreLink() {
        return "http://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    @Override
    protected void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, BaseDynamicUrlCreator.DynamicUrlCallback callback) {
        String uriPrefix = getDynamicUrl();
        if (!TextUtils.isEmpty(uriPrefix)) {
            DynamicLink.Builder builder = FirebaseDynamicLinks.getInstance()
                    .createDynamicLink()
                    .setLink(deepLink)
                    .setDomainUriPrefix(uriPrefix)
                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                            .setMinimumVersion(minVersion)
                            .build());

            // Build the dynamic link
            builder.buildShortDynamicLink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                @Override
                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                    if (task.isComplete() && task.isSuccessful() && task.getResult() != null
                            && task.getResult().getShortLink() != null) {
                        callback.onDynamicUrlGenerate(task.getResult().getShortLink().toString());
                    } else {
                        callback.onError(new Exception(task.getException()));
                    }
                }
            });
        } else {
            callback.onError(new Exception("Invalid Dynamic Url"));
        }
    }

    @Override
    protected void onDeepLinkIntentFilter(Activity activity) {
        if (activity != null && activity.getIntent() != null) {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(activity.getIntent())
                    .addOnSuccessListener(activity, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData linkData) {
                            if(resultCallBack != null) {
                                if (linkData != null && linkData.getLink() != null) {
                                    resultCallBack.onDynamicUrlResult(linkData.getLink()
                                            , BaseDynamicUrlCreator.EncryptData.decode(linkData.getLink().getQueryParameter(PARAM_EXTRA_DATA)));
                                } else {
                                    resultCallBack.onError(new Exception("Invalid Dynamic Url"));
                                }
                            }
                        }
                    })
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (resultCallBack != null) {
                                resultCallBack.onError(e);
                            }
                        }
                    });
        }
    }

    private void showProgress(int visibility) {
        if(visibility == View.VISIBLE) {
            if (progressListener != null) {
                progressListener.onStartProgressBar();
            }else {
                BaseUtil.showDialog(context, "Processing, Please wait...", true);
            }
        }else {
            if (progressListener != null) {
                progressListener.onStopProgressBar();
            }else {
                BaseUtil.hideDialog();
            }
        }
    }

    public void addProgressListener(Response.Progress progressListener) {
        this.progressListener = progressListener;
    }

    private void shareMe(String description, String deepLink) {
        if (BaseUtil.isValidUrl(deepLink)) {
            String openLink = "\nChick here to watch video : \n" + deepLink;

            String extraText = description + "\n\n" + openLink;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, extraText);
            intent.setType("text/plain");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, "Share With"));
        }
    }
}