package com.appsfeature.education;

import android.app.Activity;

import com.appsfeature.education.activity.AppBrowserActivity;
import com.appsfeature.education.listeners.ContentType;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.login.LoginSDK;
import com.browser.BrowserSdk;

public class DirectionUtil {

    public static void onMainActivityClick(Activity activity, int id) {
        if (id == R.id.option_1) {
            ClassUtil.openLiveClassActivity(activity);
        } else if (id == R.id.option_2) {
            AppBrowserActivity.open(activity, activity.getString(R.string.title_option_2), AppConstant.BASE_URL_TEST_SERIES + LoginSDK.getInstance().getAdmissionNo());
        } else if (id == R.id.option_5) {
            LoginSDK.getInstance().openLoginPage(activity, true);
        }
    }
}
