package com.appsfeature.education.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.doctor.VideoModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.SlotModel;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.login.util.LoginConstant;
import com.config.config.ConfigConstant;
import com.config.config.ConfigManager;
import com.google.gson.reflect.TypeToken;
import com.helper.callback.Response;
import com.helper.network.BaseNetworkManager;
import com.helper.util.BaseConstants;

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

    public void getVideoLecture(int courseId, boolean isLiveClass, final Response.Callback<List<VideoModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("course_id", courseId + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST_FORM, AppConstant.HOST_MAIN
                , isLiveClass ? ApiEndPoint.GET_LIVE_CLASS : ApiEndPoint.GET_OFFLINE_CLASS
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<VideoModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<VideoModel>>() {
                                @Override
                                public void onSuccess(List<VideoModel> list) {
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

    public void getDoctorSlotView(String doctorId, String appointmentDate, final Response.Callback<List<SlotModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("doctor_id", doctorId + "");
        map.put("appointment_date", appointmentDate + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_GET, AppConstant.HOST_MAIN
                , ApiEndPoint.DOCTOR_SLOT_VIEW
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<SlotModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<SlotModel>>() {
                                @Override
                                public void onSuccess(List<SlotModel> list) {
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

    public void getDoctorProfile(String doctorId, final Response.Callback<List<DoctorModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("doctor_id", doctorId + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_GET, AppConstant.HOST_MAIN
                , ApiEndPoint.DOCTOR_PROFILE_VIEW
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<DoctorModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<DoctorModel>>() {
                                @Override
                                public void onSuccess(List<DoctorModel> list) {
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

    public void getPatientProfile(String patientId, final Response.Callback<PatientModel> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patientId + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_GET, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_PROFILE_VIEW
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<PatientModel>() {
                            }.getType(), new ParserConfigDataSimple<PatientModel>() {
                                @Override
                                public void onSuccess(PatientModel list) {
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


    public void getPatientBookingHistory(String patientId, final Response.Callback<List<AppointmentModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patientId + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_GET, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_BOOKING_HISTORY
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<AppointmentModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<AppointmentModel>>() {
                                @Override
                                public void onSuccess(List<AppointmentModel> list) {
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


    public void bookPatientAppointment(String patient_id, String doctor_id, String booking_date, String booking_slot
            , String booking_type, String patient_name, String patient_gender, String patient_relation, String patient_dob, final Response.Callback<AppointmentModel> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", patient_id + "");
        map.put("doctor_id", doctor_id + "");
        map.put("booking_date", booking_date + "");
        map.put("booking_slot", booking_slot + "");
        map.put("booking_type", booking_type + "");
        map.put("patient_name", patient_name + "");
        map.put("patient_gender", patient_gender + "");
        map.put("patient_relation", patient_relation + "");
        map.put("patient_dob", patient_dob + "");
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_APPOINTMENT_SUBMIT
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<AppointmentModel>() {
                            }.getType(), new ParserConfigDataSimple<AppointmentModel>() {
                                @Override
                                public void onSuccess(AppointmentModel item) {
                                    if (item != null) {
                                        callback.onSuccess(item);
                                    } else {
                                        callback.onFailure(new Exception(BaseConstants.NO_DATA));
                                    }
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


    public void addNewFamilyMember(String userId, String name, String gender, String relation
            , String dob, final Response.Callback<PatientModel> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", userId + "");
        map.put("family_name", name + "");
        map.put("family_gender", gender + "");
        map.put("family_relation", relation + "");
        map.put("family_dob", dob + ""); //dd/MM/yyyy
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_FAMILY_UPDATE
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<PatientModel>() {
                            }.getType(), new ParserConfigDataSimple<PatientModel>() {
                                @Override
                                public void onSuccess(PatientModel item) {
                                    if (item != null) {
                                        callback.onSuccess(item);
                                    } else {
                                        callback.onFailure(new Exception(BaseConstants.NO_DATA));
                                    }
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


    public void deleteFamilyMember(String userId, String name, String gender, String dob, final Response.Callback<PatientModel> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", userId + "");
        map.put("family_name", name + "");
        map.put("family_dob", dob + ""); //dd/MM/yyyy
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_FAMILY_REMOVE
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<PatientModel>() {
                            }.getType(), new ParserConfigDataSimple<PatientModel>() {
                                @Override
                                public void onSuccess(PatientModel item) {
                                    callback.onSuccess(item);
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

    public void cancelAppointment(String userId, String appointmentId, final Response.Callback<List<AppointmentModel>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("patient_id", userId);
        map.put("appointment_id", appointmentId );
        AppApplication.getInstance().getConfigManager().getData(ConfigConstant.CALL_TYPE_POST, AppConstant.HOST_MAIN
                , ApiEndPoint.PATIENT_APPOINTMENT_CANCEL
                , map, new ConfigManager.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, String data) {
                        if (status && !TextUtils.isEmpty(data)) {
                            parseConfigData(data, new TypeToken<List<AppointmentModel>>() {
                            }.getType(), new ParserConfigDataSimple<List<AppointmentModel>>() {
                                @Override
                                public void onSuccess(List<AppointmentModel> item) {
                                    callback.onSuccess(item);
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
