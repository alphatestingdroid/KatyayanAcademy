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
import com.appsfeature.login.dialog.ErrorDialog;
import com.appsfeature.login.fragment.BaseFragment;
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;


public class MobileForgotPassword extends BaseFragment {

    private EditText etUsername;
    private LinearLayout llSignup;
    private Listener mListener;
    private ProgressButton btnAction;

    public interface Listener {
        void onAddSignUpScreen();
        void addVerifyScreen(String mobile, boolean isOpenChangePasswordOption);
    }

    public static MobileForgotPassword newInstance(Listener mListener) {
        MobileForgotPassword fragment = new MobileForgotPassword();
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_forgot, container, false);
        initToolBarTheme(getActivity(), v, "Forgot Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = (EditText) v.findViewById(R.id.et_employee_username);
        llSignup = (LinearLayout) v.findViewById(R.id.ll_signup);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Reset Password")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etUsername)) {
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });


        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onAddSignUpScreen();
                }
            }
        });

        btnAction.setOnEditorActionListener(etUsername,"Send OTP");

    }


    private void executeTask() {
        String username = etUsername.getText().toString();

        LoginNetwork.getInstance(getContext())
                .generateOTP(username, new LoginListener<Boolean>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Boolean response) {
                        btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                            @Override
                            public void onAnimationCompleted() {
                                mListener.addVerifyScreen(etUsername.getText().toString(), true);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        if (getActivity() != null) {
                            ErrorDialog.newInstance(getActivity(), e.getMessage()).show();
                        }
                        btnAction.revertProgress();
                    }
                });


    }


}