package com.appsfeature.education;

import android.app.Activity;

import com.appsfeature.education.listeners.ContentType;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.login.LoginSDK;
import com.browser.BrowserSdk;

public class DirectionUtil {

    public static void onMainActivityClick(Activity activity, int id) {
        if (id == R.id.option_1) {
            ClassUtil.openLiveClassActivity(activity);
        } else if (id == R.id.option_2) {
            ClassUtil.openCategoryActivity(activity, ContentType.TYPE_VIDEO, activity.getString(R.string.title_option_2), false);
        } else if (id == R.id.option_3) {
            ClassUtil.openCategoryActivity(activity, ContentType.TYPE_VIDEO, activity.getString(R.string.title_option_3), true);
        } else if (id == R.id.option_4) {
            ClassUtil.openCategoryActivity(activity, ContentType.TYPE_PDF, activity.getString(R.string.title_option_4), true);
        } else if (id == R.id.option_5) {
            LoginSDK.getInstance().openLoginPage(activity, true);
        } else if (id == R.id.option_6) {
            BrowserSdk.open(activity, activity.getString(R.string.title_option_6), AppConstant.URL_CLASS_SCHEDULE);
        }
    }
}
