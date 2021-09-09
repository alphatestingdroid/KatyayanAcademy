package com.appsfeature.login.util;

import android.content.Context;

import com.appsfeature.login.R;

import java.util.HashMap;

public class LoginDataUtil {

    private static LoginDataUtil mInstance;
    HashMap<Integer, String> subCategories = new HashMap<>();
    private HashMap<Integer, String> schoolClassNameList = new HashMap<>();

    public static LoginDataUtil getInstance(Context context) {
        if(mInstance == null){
            mInstance = new LoginDataUtil(context);
        }
        return mInstance;
    }

    public LoginDataUtil(Context context) {
        initAcademyList(context);
        initSchoolList();
    }

    private void initSchoolList() {
        schoolClassNameList.put(1, "Play Group");
        schoolClassNameList.put(2, "Nursery");
        schoolClassNameList.put(3, "KG");
        schoolClassNameList.put(4, "I");
        schoolClassNameList.put(5, "II");
        schoolClassNameList.put(6, "III");
        schoolClassNameList.put(7, "IV");
        schoolClassNameList.put(8, "V");
        schoolClassNameList.put(9, "VI");
        schoolClassNameList.put(10, "VII");
        schoolClassNameList.put(11, "VIII");
        schoolClassNameList.put(12, "IX");
        schoolClassNameList.put(13, "X");
        schoolClassNameList.put(14, "XI");
        schoolClassNameList.put(15, "XII");
    }

    private void initAcademyList(Context context) {
        final String[] subCourse = context.getResources().getStringArray(R.array.array_sub_course);
        for (int i = 0; i< subCourse.length; i++){
            subCategories.put(AppData.subCatIds[i], subCourse[i]);
        }
    }

    public HashMap<Integer, String> getSubCategories() {
        return subCategories;
    }

    public HashMap<Integer, String> getSchoolClassNameList() {
        return schoolClassNameList;
    }
}
