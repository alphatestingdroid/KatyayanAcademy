package com.appsfeature.login.util;

import com.helper.util.BaseConstants;

public class LoginConstant {

    public static final String OPEN_EDIT_PROFILE = "isOpenEditProfile";
    public static final String ERROR_PLEASE_TRY_LATER = "Error, please try later.";
    public static final String ERROR_INVALID_LOGIN_TYPE = "Error, invalid login type.";

    public static final String CATEGORY_PROPERTY = "category_property";
    public static final String EXTRA_PROPERTY = CATEGORY_PROPERTY;

    public static final String SERVER_TIME_OUT_MSG = BaseConstants.Error.MSG_ERROR;
    public static final String SERVER_TIME_OUT_TAG = "Alert!";
    public static final String PACKAGE_ACADEMY = "com.katyayanacademy.katyayanacademy";
    public static final String PACKAGE_SCHOOL = "com.katyayanschool.katyayanschool";

    public interface SharedPref {
        String IS_LOGIN_COMPLETE = "isLogComplete";
        String IS_REGISTRATION_COMPLETE = "isRegComplete";
        String USER_NAME = "userName";
        String FIRST_NAME = "first_name";
        String USER_PHOTO_URL = "photoUrl";
        String USER_ID_AUTO = "auto_id";
        String USER_UID = "userUid";
        String USER_EMAIL = "userEmail";
        String USER_EMAIL_VERIFIED = "userEmail_verified";
        String USER_PHONE = "userPhone";
        String USER_ADDRESS = "userAddress";
        String USER_STATE = "userState";
        String USER_POSTAL_CODE = "userPostalCode";
        String USER_GENDER = "user_gender";
        String USER_ABOUT_ME = "user_about_me";
        String USER_POINTS = "userPoints";
        String USER_DATE_OF_BIRTH = "user_date_of_birth";
        String APP_USER = "appUser";
        String FCM_TOKEN = "appUser";
        String LOGIN_PROVIDER = "login_provider";
        String PLAYER_ID = "PLAYER_ID";
        String IS_USER_NOT_VERIFIED = "isUserNotVerified";
        String IS_VERIFICATION_EMAIL_SENT = "isVerificationEmailSent";
        String USER_LOGIN_STATUS = "login_status" ;
        String ADMISSION_NO = "admission_no";
        String COURSE_ID = "course_id";
        String SUB_COURSE_ID = "sub_course_id";
        String PROFILE_DETAIL = "profile_detail";
        int USER_LOGIN_NOT = 0 ;
        int USER_LOGIN_HALF = 1 ;
        int USER_LOGIN_COMPLETE = 2 ;
    }
}
