package com.appsfeature.education.listeners;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ContentType.TYPE_VIDEO, ContentType.TYPE_PDF})
@Retention(RetentionPolicy.SOURCE)
public @interface ContentType {
    int TYPE_VIDEO = 1;
    int TYPE_PDF = 2;
}
