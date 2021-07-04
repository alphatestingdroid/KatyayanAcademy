package com.appsfeature.login.network;

import android.content.Context;
import android.text.TextUtils;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.LoginType;
import com.appsfeature.login.model.LoginModel;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginConstant;
import com.config.config.ConfigConstant;
import com.config.config.ConfigManager;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.helper.network.BaseNetworkManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginNetwork extends BaseNetworkManager {
    private static LoginNetwork instance;
    private final Context context;


    public LoginNetwork(Context context) {
        this.context = context;

    }

    public static LoginNetwork getInstance(Context context) {
        if (instance == null) {
            instance = new LoginNetwork(context);
        }
        return instance;
    }

    public void forgetPassword(String patient_id, String newPassword, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patient_id + "");
        map.put("patient_new_password", newPassword + "");
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, ConfigConstant.HOST_LOGIN
                , isMobile ? NetworkApiEndPoint.MOBILE_FORGOT_PASS : NetworkApiEndPoint.EMAIL_CHANGE_PASSWORD
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<Profile>() {
                            }.getType(), new ParserConfigDataSimple<Profile>() {
                                @Override
                                public void onSuccess(Profile profile) {
                                    if (profile != null) {
                                        callback.onSuccess(profile);
                                    } else {
                                        callback.onError(new Exception(LoginConstant.ERROR_PLEASE_TRY_LATER));
                                    }
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onError(error);
                                }
                            });
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public void changePassword(String patient_id, String oldPassword, String newPassword, final LoginListener<Boolean> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patient_id + "");
        map.put("patient_old_password", oldPassword + "");
        map.put("patient_new_password", newPassword + "");
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, ConfigConstant.HOST_LOGIN
                , isMobile ? NetworkApiEndPoint.MOBILE_CHANGE_PASSWORD : NetworkApiEndPoint.EMAIL_CHANGE_PASSWORD
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            callback.onSuccess(true);
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }


    public void generateOTP(String mobileOrUsername, final LoginListener<Boolean> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>(1);
        if (isMobile) {
            map.put("patient_mobile", mobileOrUsername + "");
        } else {
            map.put("username", mobileOrUsername + "");
        }
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, ConfigConstant.HOST_LOGIN
                , isMobile ? NetworkApiEndPoint.MOBILE_OTP_GENERATE : NetworkApiEndPoint.EMAIL_FORGOT_PASS
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            callback.onSuccess(true);
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public void verifyOTP(String mobileOrUsername, String otp, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>(2);
        if (isMobile) {
            map.put("patient_mobile", mobileOrUsername + "");
        } else {
            map.put("username", mobileOrUsername + "");
        }
        map.put("patient_otp", otp + "");
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, ConfigConstant.HOST_LOGIN
                , isMobile ? NetworkApiEndPoint.MOBILE_VALIDATE_OTP : NetworkApiEndPoint.EMAIL_VALIDATE_OTP
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<Profile>() {
                            }.getType(), new ParserConfigDataSimple<Profile>() {
                                @Override
                                public void onSuccess(Profile profile) {
                                    if (profile != null) {
                                        callback.onSuccess(profile);
                                    } else {
                                        callback.onError(new Exception(LoginConstant.ERROR_PLEASE_TRY_LATER));
                                    }
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onError(error);
                                }
                            });
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public void loginUser(String mobileOrUsername, String password, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>(2);
        map.put("email", mobileOrUsername + "");
        map.put("password", password + "");
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST_FORM, ConfigConstant.HOST_LOGIN
                , isMobile ? NetworkApiEndPoint.MOBILE_LOGIN_USER : NetworkApiEndPoint.EMAIL_LOGIN_USER
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<Profile>>() {
                            }.getType(), new ParserConfigDataSimple<List<Profile>>() {
                                @Override
                                public void onSuccess(List<Profile> profile) {
                                    if (profile != null && profile.size() > 0) {
                                        callback.onSuccess(profile.get(0));
                                    } else {
                                        callback.onError(new Exception(LoginConstant.ERROR_PLEASE_TRY_LATER));
                                    }
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onError(error);
                                }
                            });
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public void signUp(String name, String mobile, String dob, String mGender, String pinCode, int mCourseId, int mSubCourseId, String collegeCode, LoginListener<Profile> callback) {
        callback.onPreExecute();
        Map<String, String> map = new HashMap<>();
        map.put("student_name", name + "");
        map.put("student_mobile", mobile + "");
        map.put("student_dob", dob + "");
        map.put("student_gender", mGender + "");
        map.put("pin_code", pinCode + "");
        map.put("course_id", mCourseId + "");
        map.put("sub_course_id", mSubCourseId + "");
        map.put("college_code", collegeCode + "");
        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST_FORM, ConfigConstant.HOST_LOGIN
                , NetworkApiEndPoint.MOBILE_SIGN_UP
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<Profile>>() {
                            }.getType(), new ParserConfigDataSimple<List<Profile>>() {
                                @Override
                                public void onSuccess(List<Profile> profile) {
                                    if (profile != null && profile.size() > 0) {
                                        callback.onSuccess(profile.get(0));
                                    } else {
                                        callback.onError(new Exception(LoginConstant.ERROR_PLEASE_TRY_LATER));
                                    }
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onError(error);
                                }
                            });
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public void patientProfileUpdate(String patient_id, String imagePath, String profile_name, String first_name
            , String last_name, String date_of_birth, String gender
            , String address, String city, String state, String pincode, String country
            , String email, String profile_picture_old, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        boolean isMobile = LoginSDK.getInstance().getLoginType() == LoginType.TYPE_MOBILE;
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patient_id + "");
        map.put("profile_name", profile_name + "");
        map.put("first_name", first_name + "");
        map.put("last_name", last_name + "");
        map.put("date_of_birth", date_of_birth + "");
        map.put("gender", gender + "");
        map.put("address", address + "");
        map.put("city", city + "");
        map.put("state", state + "");
        map.put("pincode", pincode + "");
        map.put("country", country + "");
        map.put("email", email + "");
        map.put("profile_picture_old", profile_picture_old + "");

        MultipartBody.Part fileToUpload = null;
        if (!TextUtils.isEmpty(imagePath)) {
            File file = new File(imagePath);

            if (file != null && file.exists()) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                fileToUpload = MultipartBody.Part.createFormData("profile_picture", file.getName(), requestBody);
            }
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), LoginSDK.getInstance().getUserName());

        LoginSDK.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST_FILE, ConfigConstant.HOST_LOGIN
                , NetworkApiEndPoint.PATIENT_PROFILE_UPDATE
                , map, requestBody, fileToUpload, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<Profile>() {
                            }.getType(), new ParserConfigDataSimple<Profile>() {
                                @Override
                                public void onSuccess(Profile profile) {
                                    if (profile != null) {
                                        callback.onSuccess(profile);
                                    } else {
                                        callback.onError(new Exception(LoginConstant.ERROR_PLEASE_TRY_LATER));
                                    }
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onError(error);
                                }
                            });
                        } else {
                            callback.onError(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }
}
