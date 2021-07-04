package com.appsfeature.login.fragment.mobile;

/**
 * Created by Admin on 5/22/2017.
 */

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appsfeature.login.R;
import com.appsfeature.login.fragment.BaseFragment;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;


public class MobileOTPVerification extends BaseFragment {

    private EditText etMobile, etOtp;
    private LinearLayout llSignup;
    private Listener mListener;
    private ProgressButton btnAction;
    private String mobileNumber;
    private boolean isOpenChangePasswordOption;
    private String name, password;

    public interface Listener {
        void addLoginOption();

        void onLoginSuccess();

        void addPasswordChangeScreen();
    }

    public static MobileOTPVerification newInstance(String mobileNumber, String name, String password, boolean isOpenChangePasswordOption, Listener mListener) {
        MobileOTPVerification fragment = new MobileOTPVerification();
        fragment.mListener = mListener;
        fragment.name = name;
        fragment.password = password;
        fragment.isOpenChangePasswordOption = isOpenChangePasswordOption;
        fragment.mobileNumber = mobileNumber;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_verify, container, false);
        initToolBarTheme(getActivity(), v, "Verify Mobile");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etMobile = (EditText) v.findViewById(R.id.et_employee_username);
        etOtp = (EditText) v.findViewById(R.id.et_employee_pin);
        llSignup = (LinearLayout) v.findViewById(R.id.ll_signup);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Submit")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etMobile)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etOtp)) {
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });


        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.addLoginOption();
                }
            }
        });

        btnAction.setOnEditorActionListener(etOtp, "Send OTP");

        etMobile.setText(mobileNumber);
    }


    private void executeTask() {
        String mobile = etMobile.getText().toString();
        String otp = etOtp.getText().toString();

        if (isOpenChangePasswordOption) {
            LoginNetwork.getInstance(getContext())
                    .verifyOTP(mobile, otp, new LoginListener<Profile>() {
                        @Override
                        public void onPreExecute() {
                            btnAction.startProgress();
                        }

                        @Override
                        public void onSuccess(Profile response) {
                            if (response != null) {
                                LoginPrefUtil.setProfile(response);
                                btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                    @Override
                                    public void onAnimationCompleted() {
                                        mListener.addPasswordChangeScreen();
                                    }
                                });
                            }else {
                                btnAction.revertProgress();
                                LoginUtil.showToast(getActivity(), "Couldn't find your Account. Please SignUp again.");
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            btnAction.revertProgress();
                            LoginUtil.showToast(getActivity(), e.getMessage());
                        }
                    });
        } else {
//            LoginNetwork.getInstance(getContext())
//                    .signUp(name, mobileNumber, password, otp, new LoginListener<Profile>() {
//                        @Override
//                        public void onPreExecute() {
//                            btnAction.startProgress();
//
//                        }
//
//                        @Override
//                        public void onSuccess(Profile response) {
//                            LoginPrefUtil.setProfile(response);
//                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
//                                @Override
//                                public void onAnimationCompleted() {
//                                    mListener.onLoginSuccess();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            btnAction.revertProgress();
//                            LoginUtil.showToast(getActivity(), e.getMessage());
//                        }
//                    });
        }


    }


}