package com.appsfeature.education.util;

import android.app.Activity;
import android.content.Intent;

import com.appsfeature.education.education.LiveClassActivity;
import com.appsfeature.education.education.VideoLectureActivity;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.login.LoginSDK;

public class ClassUtil {


    public static void openLiveClassActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        extraProperty.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        activity.startActivity(new Intent(activity, LiveClassActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openVideoLectureActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        extraProperty.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        activity.startActivity(new Intent(activity, VideoLectureActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }
}
