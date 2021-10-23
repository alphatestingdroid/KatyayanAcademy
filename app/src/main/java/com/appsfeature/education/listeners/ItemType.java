package com.appsfeature.education.listeners;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ItemType.CATEGORY_TYPE_DEFAULT,
        ItemType.CATEGORY_TYPE_LIVE_CLASS,
        ItemType.CATEGORY_TYPE_OFFLINE_VIDEOS,
        ItemType.CATEGORY_TYPE_SUBJECT,
        ItemType.CATEGORY_TYPE_CHAPTER,
        ItemType.CATEGORY_TYPE_OLD_VIDEOS,
        ItemType.CATEGORY_TYPE_PRE_CLASS
})
@Retention(RetentionPolicy.SOURCE)
public @interface ItemType {
    int CATEGORY_TYPE_DEFAULT = 1000;
    int CATEGORY_TYPE_LIVE_CLASS = 1001;
    int CATEGORY_TYPE_OFFLINE_VIDEOS = 1002;
    int CATEGORY_TYPE_SUBJECT = 1003;
    int CATEGORY_TYPE_CHAPTER = 1004;
    int CATEGORY_TYPE_OLD_VIDEOS = 1005;
    int CATEGORY_TYPE_PRE_CLASS = 1006;
}
