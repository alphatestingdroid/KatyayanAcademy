package com.appsfeature.login.util;

import android.content.Context;

import com.appsfeature.login.R;

import java.util.HashMap;

public class LoginDataUtil {

    private static LoginDataUtil mInstance;
    HashMap<Integer, String> subCategories = new HashMap<>();

    public static LoginDataUtil getInstance(Context context) {
        if(mInstance == null){
            mInstance = new LoginDataUtil(context);
        }
        return mInstance;
    }

    public LoginDataUtil(Context context) {
        final String[] subCourse = context.getResources().getStringArray(R.array.array_sub_course);
        for (int i = 0; i< subCourse.length; i++){
            subCategories.put(AppData.subCatIds[i], subCourse[i]);
        }
    }

    public HashMap<Integer, String> getSubCategories() {
        return subCategories;
    }
}
