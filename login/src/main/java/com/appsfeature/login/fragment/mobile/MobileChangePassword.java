package com.appsfeature.login.fragment.mobile;

/**
 * Created by Admin on 5/22/2017.
 */

import android.app.Activity;
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


public class MobileChangePassword extends BaseFragment {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private LinearLayout llSignup;
    private Listener mListener;
    private ProgressButton btnAction;
    private boolean isForgetPass;
    private Activity activity;

    public interface Listener {
        void onPasswordChangedSuccess();
    }

    public static MobileChangePassword newInstance(boolean isForgetPass, Listener mListener) {
        MobileChangePassword fragment = new MobileChangePassword();
        fragment.isForgetPass = isForgetPass;
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_change_pass, container, false);
        activity = getActivity();
        initToolBarTheme(getActivity(), v, "Change Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {
        etOldPassword = v.findViewById(R.id.et_employee_old_pass);
        etNewPassword = v.findViewById(R.id.et_employee_new_pass);
        etConfirmPassword = v.findViewById(R.id.et_employee_confirm_pass);
//        llSignup = v.findViewById(R.id.ll_signup);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Send Request")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isForgetPass && !FieldValidation.isEmpty(getContext(), etOldPassword)) {
                            return;
                        }else if (!FieldValidation.isEmpty(getContext(), etNewPassword)) {
                            return;
                        }else if (!FieldValidation.isEmpty(getContext(), etConfirmPassword)) {
                            return;
                        } else if (!etNewPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
                            LoginUtil.showToast(getContext(), "Entered both password miss match.");
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });
        btnAction.setText("Submit");

//        llSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.onAddSignUpScreen();
//                }
//            }
//        });

        btnAction.setOnEditorActionListener(etConfirmPassword, "Submit");

        etOldPassword.setVisibility(isForgetPass ? View.GONE : View.VISIBLE);
    }


    private void executeTask() {
        String newPassword = etNewPassword.getText().toString();
        String oldPassword = etOldPassword.getText().toString();
        String userId = LoginPrefUtil.getUserId();
        if(isForgetPass) {
            LoginNetwork.getInstance(getContext())
                    .forgetPassword(userId, newPassword, new LoginListener<Profile>() {
                        @Override
                        public void onPreExecute() {
                            btnAction.startProgress();

                        }

                        @Override
                        public void onSuccess(Profile response) {
                            LoginPrefUtil.setProfile(response);
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    mListener.onPasswordChangedSuccess();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            btnAction.revertProgress();
                            LoginUtil.showToast(activity, e.getMessage());
                        }
                    });
        }else {
            LoginNetwork.getInstance(getContext())
                    .changePassword(userId, oldPassword, newPassword, new LoginListener<Boolean>() {
                        @Override
                        public void onPreExecute() {
                            btnAction.startProgress();
                        }

                        @Override
                        public void onSuccess(Boolean response) {
                            LoginUtil.showToast(getContext(), "Password changed successful.");
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    mListener.onPasswordChangedSuccess();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            LoginUtil.showToast(activity, e.getMessage());
                            btnAction.revertProgress();
                        }
                    });
        }


    }


}