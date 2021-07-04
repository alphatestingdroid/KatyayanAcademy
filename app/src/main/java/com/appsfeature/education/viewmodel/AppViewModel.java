package com.appsfeature.education.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.doctor.VideoModel;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.ExtraProperty;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.model.SlotModel;
import com.appsfeature.education.network.NetworkManager;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppViewModel extends ViewModel {
    private AppCallback.View viewCallback;
    private Context context;
    private NetworkManager networkHandler;
    private ExtraProperty extraProperty;

    public ExtraProperty getExtraProperty() {
        return extraProperty;
    }

    public void initialize(Activity activity, Intent intent) {
        this.context = activity;
        this.viewCallback = (AppCallback.View) activity;
        networkHandler = new NetworkManager(context);
//        databaseHandler = DatabaseHandler(context);
        setArguments(intent);
    }

    public void initialize(Fragment fragment, Bundle bundle) {
        this.context = fragment.getActivity();
        this.viewCallback = (AppCallback.View) fragment;
        networkHandler = new NetworkManager(context);
//        databaseHandler = DatabaseHandler(context);
        setArguments(bundle);
    }

    private void setArguments(Intent intent) {
        if (intent.getSerializableExtra(AppConstant.CATEGORY_PROPERTY) instanceof ExtraProperty) {
            extraProperty = (ExtraProperty) intent.getSerializableExtra(AppConstant.CATEGORY_PROPERTY);
        } else {
            SupportUtil.showToast(context, AppConstant.INVALID_PROPERTY);
        }
    }

    private void setArguments(Bundle bundle) {
        if (bundle.getSerializable(AppConstant.CATEGORY_PROPERTY) instanceof ExtraProperty) {
            extraProperty = (ExtraProperty) bundle.getSerializable(AppConstant.CATEGORY_PROPERTY);
        } else {
            SupportUtil.showToast(context, AppConstant.INVALID_PROPERTY);
        }
    }

    public void getLiveClass(int courseId) {
        viewCallback.onStartProgressBar();
        networkHandler.getVideoLecture(courseId, true, new Response.Callback<List<VideoModel>>() {
            @Override
            public void onSuccess(List<VideoModel> response) {
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setVideoList(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }

    public void getVideoLecture(int courseId) {
        viewCallback.onStartProgressBar();
        networkHandler.getVideoLecture(courseId, false, new Response.Callback<List<VideoModel>>() {
            @Override
            public void onSuccess(List<VideoModel> response) {
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setVideoList(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }

    public void getDoctorSlotView(String doctorId, String appointmentDate) {
        viewCallback.onStartProgressBar();
        networkHandler.getDoctorSlotView(doctorId, appointmentDate, new Response.Callback<List<SlotModel>>() {
            @Override
            public void onSuccess(List<SlotModel> response) {
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setSlotModel(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }

    public void getPatientProfile(String patientId) {
        viewCallback.onStartProgressBar();
        networkHandler.getPatientProfile(patientId, new Response.Callback<PatientModel>() {
            @Override
            public void onSuccess(PatientModel response) {
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setPatientModel(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }

    public void getPatientBookingHistory(String patientId) {
        viewCallback.onStartProgressBar();
        networkHandler.getPatientBookingHistory(patientId, new Response.Callback<List<AppointmentModel>>() {
            @Override
            public void onSuccess(List<AppointmentModel> response) {
                if (response != null && response.size() > 0)
                    Collections.sort(response, new Comparator<AppointmentModel>() {
                        @Override
                        public int compare(AppointmentModel item, AppointmentModel item2) {
                            Date value = getDate(item.getCreatedAt());
                            Date value2 = getDate(item2.getCreatedAt());
                            return value2.compareTo(value);
                        }
                    });
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setAppointmentModel(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }

    public static Date getDate(String cDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).parse(cDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public void bookPatientAppointment(String patient_id, String doctor_id, String booking_date, String booking_slot
            , String booking_type, String patient_name, String patient_gender, String patient_relation, String patient_dob, final Response.Callback<AppointmentModel> callback) {
        viewCallback.onStartProgressBar();
        networkHandler.bookPatientAppointment(patient_id, doctor_id, booking_date, booking_slot
                , booking_type, patient_name, patient_gender, patient_relation, patient_dob, new Response.Callback<AppointmentModel>() {
                    @Override
                    public void onSuccess(AppointmentModel response) {
                        viewCallback.onStopProgressBar();
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        viewCallback.onStopProgressBar();
                        callback.onFailure(e);
                    }
                });
    }

    public void addNewFamilyMember(String userId, String name, String gender, String relation
            , String dob, final Response.Callback<PatientModel> callback) {
        viewCallback.onStartProgressBar();
        networkHandler.addNewFamilyMember(userId, name, gender, relation
                , dob, new Response.Callback<PatientModel>() {
                    @Override
                    public void onSuccess(PatientModel response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        viewCallback.onStopProgressBar();
                        callback.onFailure(e);
                    }
                });
    }

    public void deleteFamilyMember(String userId, String name, String gender, String relation, String dob, final Response.Callback<PatientModel> callback) {
        networkHandler.deleteFamilyMember(userId, name, gender, dob, new Response.Callback<PatientModel>() {
            @Override
            public void onSuccess(PatientModel response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }


    public void cancelAppointment(String userId, String appointmentId, boolean isShowProgress, final Response.Callback<List<AppointmentModel>> callback) {
        if(isShowProgress) {
            viewCallback.onStartProgressBar();
        }
        networkHandler.cancelAppointment(userId, appointmentId, new Response.Callback<List<AppointmentModel>>() {
            @Override
            public void onSuccess(List<AppointmentModel> response) {
                if(isShowProgress) {
                    viewCallback.onStopProgressBar();
                }
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception e) {
                if(isShowProgress) {
                    viewCallback.onStopProgressBar();
                }
                callback.onFailure(e);
            }
        });
    }


}
