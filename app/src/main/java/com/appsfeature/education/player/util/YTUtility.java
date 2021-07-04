package com.appsfeature.education.player.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.appsfeature.education.doctor.VideoModel;
import com.appsfeature.education.model.ExtraProperty;
import com.appsfeature.education.player.YTPlayerActivity;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.login.LoginSDK;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.helper.util.BaseUtil;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class YTUtility {

    public static void showKeyboard(View view, Context activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void openExternalYoutubeVideoPlayer(Activity activity, String googleApiKey, String youtubeVideoId) {
        try {
            activity.startActivity(YouTubeStandalonePlayer.createVideoIntent(activity, googleApiKey, youtubeVideoId, 0, true, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openExternalYoutubePlaylistPlayer(Activity activity, String googleApiKey, String playlistId) {
        try {
            activity.startActivity(YouTubeStandalonePlayer.createPlaylistIntent(activity, googleApiKey, playlistId, 0, 0, true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openYoutubeApp(Activity activity, String videoId) {
        try {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + videoId));
            try {
                activity.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                activity.startActivity(webIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playVideo(Context context, VideoModel item) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setVideoId(getVideoIdFromUrl(item.getLectureVideo()));
        extraProperty.setVideoModel(item);
        try {
            context.startActivity(new Intent(context, YTPlayerActivity.class)
                    .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getVideoIdFromUrl(String lectureVideo) {
        try {
            if (!TextUtils.isEmpty(lectureVideo) && BaseUtil.isValidUrl(lectureVideo)) {
                return lectureVideo.substring(lectureVideo.lastIndexOf("/") + 1);
            }else {
                return lectureVideo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return lectureVideo;
        }
    }

}
