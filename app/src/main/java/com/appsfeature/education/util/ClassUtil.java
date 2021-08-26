package com.appsfeature.education.util;

import android.app.Activity;
import android.content.Intent;

import com.appsfeature.education.education.CategoryListActivity;
import com.appsfeature.education.education.LiveClassActivity;
import com.appsfeature.education.education.ContentActivity;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.login.LoginSDK;

public class ClassUtil {


    public static void openLiveClassActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setTitle("Live Class");
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        extraProperty.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        extraProperty.setItemType(ItemType.CATEGORY_TYPE_LIVE_CLASS);
        openActivity(activity, extraProperty);
    }

    public static void openCategoryActivity(Activity activity, int contentType, String title) {
        openCategoryActivity(activity, contentType, title, false);
    }

    public static void openCategoryActivity(Activity activity, int contentType, String title, boolean isOldVideos) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setTitle(title);
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        extraProperty.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        extraProperty.setItemType(ItemType.CATEGORY_TYPE_SUBJECT);
        extraProperty.setContentType(contentType);
        extraProperty.setOldVideos(isOldVideos);
        openActivity(activity, extraProperty);
    }

    public static void openActivity(Activity activity, ExtraProperty extraProperty) {
        Class<?> aClass = getCatClass(extraProperty.getItemType());
        if(aClass != null) {
            Intent intent = new Intent(activity, aClass);
            intent.putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty);
            activity.startActivity(intent);
        }else {
            SupportUtil.showToastCentre(activity, "Update later");
        }
    }

    public static Class<?> getCatClass(int itemType) {
        switch (itemType) {
            case ItemType.CATEGORY_TYPE_LIVE_CLASS:
                return LiveClassActivity.class;
            case ItemType.CATEGORY_TYPE_OLD_VIDEOS:
            case ItemType.CATEGORY_TYPE_OFFLINE_VIDEOS:
                return ContentActivity.class;
            case ItemType.CATEGORY_TYPE_SUBJECT:
                return CategoryListActivity.class;
            case ItemType.CATEGORY_TYPE_CHAPTER:
                return CategoryListActivity.class;
            default:
                return null;
        }
    }
}
