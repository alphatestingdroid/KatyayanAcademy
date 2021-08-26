package com.appsfeature.education.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.login.util.LoginConstant;
import com.config.config.ConfigConstant;
import com.config.config.ConfigManager;
import com.google.gson.reflect.TypeToken;
import com.helper.callback.Response;
import com.helper.network.BaseNetworkManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkManager extends BaseNetworkManager {
    private static NetworkManager instance;
    private final Context context;


    public NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }


    public void getDynamicData(ExtraProperty extraProperty, final Response.Callback<List<EducationModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("course_id", extraProperty.getCourseId() + "");
        map.put("sub_course_id", extraProperty.getSubCourseId() + "");
        String methodName = ApiEndPoint.GET_OFFLINE_CLASS;
        switch (extraProperty.getItemType()) {
            case ItemType.CATEGORY_TYPE_LIVE_CLASS:
                methodName = ApiEndPoint.GET_LIVE_CLASS;
                break;
            case ItemType.CATEGORY_TYPE_SUBJECT:
                methodName = ApiEndPoint.GET_SUBJECT;
                break;
            case ItemType.CATEGORY_TYPE_CHAPTER:
                methodName = ApiEndPoint.GET_CHAPTER;
                map.put("subject_id", extraProperty.getSubjectId() + "");
                map.put("lecture_type", extraProperty.getContentType() + "");
                break;
            case ItemType.CATEGORY_TYPE_OFFLINE_VIDEOS:
                methodName = ApiEndPoint.GET_OFFLINE_CLASS;
                map.put("subject_id", extraProperty.getSubjectId() + "");
                map.put("chapter_id", extraProperty.getChapterId() + "");
                break;
            case ItemType.CATEGORY_TYPE_OLD_VIDEOS:
                methodName = ApiEndPoint.GET_OLD_CLASS;
                map.put("subject_id", extraProperty.getSubjectId() + "");
                map.put("chapter_id", extraProperty.getChapterId() + "");
                map.put("lecture_type", extraProperty.getContentType() + "");
                break;
            default:
                break;
        }
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST_FORM, AppConstant.HOST_MAIN
                , methodName, map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<EducationModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<EducationModel>>() {
                                @Override
                                public void onSuccess(List<EducationModel> list) {
                                    callback.onSuccess(list);
                                }

                                @Override
                                public void onFailure(Exception error) {
                                    callback.onFailure(error);
                                }
                            });
                        } else {
                            callback.onFailure(new Exception(!TextUtils.isEmpty(data) ? data : LoginConstant.ERROR_PLEASE_TRY_LATER));
                        }
                    }
                });
    }

    public static void submitReview(String userId, String appointmentId, String rating, String review) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", userId);
        map.put("appointment_id", appointmentId );
        map.put("rating", rating );
        map.put("review", review );
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_APPOINTMENT_RATING
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            Log.d("submitReview", data);
                        } else {
                            Log.d("submitReview", !TextUtils.isEmpty(data) ? data : "");
                        }
                    }
                });
    }


}
