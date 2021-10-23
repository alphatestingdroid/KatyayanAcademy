package com.appsfeature.education.util;

import android.app.Activity;
import android.content.Intent;

import com.appsfeature.education.education.CategoryListActivity;
import com.appsfeature.education.education.LiveClassActivity;
import com.appsfeature.education.education.ContentActivity;
import com.appsfeature.education.education.PreClassActivity;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.listeners.ContentType;
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

    public static void openCategoryActivity(Activity activity, @ContentType int contentType, String title, boolean isOldVideos) {
        openCategoryActivity(activity, ItemType.CATEGORY_TYPE_SUBJECT, contentType, title, isOldVideos);
    }

    public static void openCategoryActivity(Activity activity, @ItemType int itemType, @ContentType int contentType, String title) {
        openCategoryActivity(activity, itemType, contentType, title, false);
    }

    /**
     * @param itemType: open activity type by @ItemType
     * @param contentType: is @ContentType.TYPE_VIDEO or TYPE_PDF
     * @param title : Activity title
     * @param isOldVideos : decide to open old or offline videos
     */
    public static void openCategoryActivity(Activity activity, @ItemType int itemType, @ContentType int contentType, String title, boolean isOldVideos) {
        ExtraProperty property = new ExtraProperty();
        property.setTitle(title);
        property.setCourseId(LoginSDK.getInstance().getCourseId());
        property.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        property.setItemType(itemType);
        property.setContentType(contentType);
        property.setOldVideos(isOldVideos);
        openActivity(activity, property);
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
            case ItemType.CATEGORY_TYPE_PRE_CLASS:
                return PreClassActivity.class;
            default:
                return null;
        }
    }
}
