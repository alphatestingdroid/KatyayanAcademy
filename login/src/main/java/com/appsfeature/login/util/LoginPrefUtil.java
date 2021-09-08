package com.appsfeature.login.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.model.Profile;
import com.google.gson.reflect.TypeToken;
import com.helper.util.GsonParser;

public class LoginPrefUtil {

    private static final String TAG = "LoginPrefUtil";

    public static String getString(String key) {
        if (getDefaultSharedPref() != null) {
            return decrypt(getDefaultSharedPref().getString(encrypt(key), ""));
        } else {
            return decrypt("");
        }
    }

    public static int getInt(String key) {
        if (getDefaultSharedPref() != null) {
            return getDefaultSharedPref().getInt(encrypt(key), 0);
        } else {
            return (0);
        }
    }

    public static int getIntDef(String key, int def) {
        return getDefaultSharedPref().getInt(encrypt(key), def);
    }

    public static float getFloat(String key) {
        return getDefaultSharedPref().getFloat(encrypt(key), 0);

    }

    public static long getLong(String key) {
        return getDefaultSharedPref().getLong(encrypt(key), 0);
    }

    public static boolean getBoolean(String key) {
        if (getDefaultSharedPref() != null) {
            return getDefaultSharedPref().getBoolean(key, false);
        } else {
            return false;
        }
    }


    private static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return input;
//        if (SupportUtil.isEmptyOrNull( input )) {
//            return input ;
//        } else {
//            return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
//        }
    }

    private static String decrypt(String input) {
        return input;
//        if (SupportUtil.isEmptyOrNull( input )) {
//            return input ;
//        } else {
//            return new String(Base64.decode(input, Base64.DEFAULT));
//        }
    }

    /**
     * Set String value for a particular key.
     */
    public static void setProfile(Profile profile) {
        if (getDefaultSharedPref() != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
            if (editor != null) {
                editor.putString(encrypt(LoginConstant.SharedPref.USER_ID_AUTO), getEmptyData(profile.getId() + ""));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_NAME), getEmptyData(profile.getName()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_PHOTO_URL), getEmptyData(profile.getProfilePicture()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_PHONE), getEmptyData(profile.getMobile()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_EMAIL), getEmptyData(profile.getEmail()));
                editor.putString(encrypt(LoginConstant.SharedPref.ADMISSION_NO), getEmptyData(profile.getAdmissionNo()));
                editor.putInt((LoginConstant.SharedPref.COURSE_ID), profile.getCourseId());
                editor.putInt((LoginConstant.SharedPref.SUB_COURSE_ID), profile.getSubCourseId());
                editor.apply();
                setLoginComplete(true);
                setRegComplete(true);
                setProfileDetail(profile);
            }
        }
    }

    private static void setProfileDetail(Profile profile) {
        setString(encrypt(LoginConstant.SharedPref.PROFILE_DETAIL), GsonParser.toJson(profile, new TypeToken<Profile>() {
        }));
    }

    public static Profile getProfileDetail() {
        return GsonParser.fromJson(getString(LoginConstant.SharedPref.PROFILE_DETAIL), new TypeToken<Profile>() {
        });
    }

    public static String getUserName() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.USER_NAME);
    }

    public static String getFirstName() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.FIRST_NAME);
    }

    /**
     * check value is : complete
     */
    public static String getAdmissionNo() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.ADMISSION_NO);
    }

    public static int getCourseId() {
        return LoginPrefUtil.getInt(LoginConstant.SharedPref.COURSE_ID);
    }

    public static int getSubCourseId() {
        return LoginPrefUtil.getInt(LoginConstant.SharedPref.SUB_COURSE_ID);
    }

    public static boolean isProfileCompleted() {
        return !TextUtils.isEmpty(LoginPrefUtil.getString(LoginConstant.SharedPref.ADMISSION_NO));
    }

    public static String getUserImage() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.USER_PHOTO_URL);
    }

    public static String getUserId() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.USER_ID_AUTO);
    }

    public static String getUserMobile() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.USER_PHONE);
    }

    public static String getEmailId() {
        return LoginPrefUtil.getString(LoginConstant.SharedPref.USER_EMAIL);
    }

    public static boolean isRegComplete() {
        return LoginPrefUtil.getBoolean(LoginConstant.SharedPref.IS_REGISTRATION_COMPLETE);
    }

    public static boolean isLoginComplete() {
        return LoginPrefUtil.getBoolean(LoginConstant.SharedPref.IS_LOGIN_COMPLETE);
    }

    public static void setRegComplete(boolean flag) {
        LoginPrefUtil.setBoolean(LoginConstant.SharedPref.IS_REGISTRATION_COMPLETE, flag);
    }

    public static void setLoginComplete(boolean flag) {
        LoginPrefUtil.setBoolean(LoginConstant.SharedPref.IS_LOGIN_COMPLETE, flag);
    }

    private static String getEmptyData(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

    public static void setString(String key, String values) {
        if (getDefaultSharedPref() != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
            if (editor != null) {
                editor.putString(encrypt(key), encrypt(values));
                editor.apply();
            }
        }
    }

    /**
     * Set int value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setInt(String key, int value) {
        if (getDefaultSharedPref() != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
            if (editor != null) {
                editor.putInt(encrypt(key), value);
                editor.apply();
            }
        }
    }

    /**
     * Set float value for a key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setFloat(String key, float value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.putFloat(encrypt(key), value);
        editor.apply();
    }

    /**
     * Set long value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setLong(String key, long value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.putLong(encrypt(key), value);
        editor.apply();
    }

    /**
     * Set boolean value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setBoolean(String key, boolean value) {
        if (getDefaultSharedPref() != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
            if (editor != null) {
                editor.putBoolean(encrypt(key), value);
                editor.apply();
            }
        }
    }

    /**
     * Clear all preferences.
     */
    public static void clearPreferences() {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getDefaultSharedPref() {
        if (sharedPreferences == null) {
            Context context = LoginSDK.getInstance().getContext();
            sharedPreferences = context.getSharedPreferences(context.getPackageName() + context.getString(R.string.preference_login), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}