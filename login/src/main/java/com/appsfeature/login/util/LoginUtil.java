package com.appsfeature.login.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.model.Profile;
import com.helper.util.BaseUtil;

import java.io.IOException;
import java.io.InputStream;

public class LoginUtil extends BaseUtil {
    private static final long ANIM_DURATION_MEDIUM = 500;

    public static void setSlideAnimation(Fragment fragment, int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade slideTransition = new Fade(Fade.IN);
            slideTransition.setDuration(ANIM_DURATION_MEDIUM);
//            ChangeBounds changeBoundsTransition = new ChangeBounds();
//            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void hideKeybord(Activity activity) {
        if(activity!=null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View f = activity.getCurrentFocus();
            if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
                imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
            else
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public static void initToolBarForThemeActivity(final Activity act, View v, String title) {
        ImageView ivBack = (ImageView) v.findViewById(R.id.iv_action_back);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_titile);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybord(act);
                act.finish();
            }
        });
    }

    public static void saveUserProfileData(Profile loginUser) {
        if (loginUser != null) {
            LoginPrefUtil.setProfile(loginUser);
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static void loadUserImage(String photoUrl, ImageView ivProfilePic) {
        loadUserImage(photoUrl, ivProfilePic, R.drawable.ic_user_profile);
    }
    public static void loadUserImage(String photoUrl, ImageView ivProfilePic, int res) {
        if (!TextUtils.isEmpty(photoUrl)) {
            if (photoUrl.startsWith("http://") || photoUrl.startsWith("https://") || photoUrl.startsWith("file://")) {
                LoginSDK.getInstance().getPicasso().load(photoUrl).resize(240, 240).centerCrop().placeholder(res).transform(new CircleTransform()).into(ivProfilePic);
            }
        } else {
            ivProfilePic.setImageResource(res);
        }

    }


    public static String getAssets(Context context,String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return json;
    }
    public static boolean isValidUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        } else {
            return url.startsWith("file://") || url.startsWith("http://") || url.startsWith("https://");
        }
    }

    public static String getFileNameFromUrl(String fileUrl) {
        String fileName = null;
        try {
            if (!TextUtils.isEmpty(fileUrl) && isValidUrl(fileUrl)) {
                if (fileUrl.contains(".")) {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                } else {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                }
            }
            if (TextUtils.isEmpty(fileName)) {
                fileName = "Error-" + System.currentTimeMillis();
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error-" + System.currentTimeMillis();
        }
    }
}
