package com.appsfeature.login.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.fragment.mobile.MobileChangePassword;
import com.appsfeature.login.fragment.mobile.MobileForgotPassword;
import com.appsfeature.login.fragment.mobile.MobileLogin;
import com.appsfeature.login.fragment.mobile.MobileOTPVerification;
import com.appsfeature.login.fragment.mobile.MobileSignUp;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginPrefUtil;


/**
 * Created by Admin on 5/22/2017.
 */

public class MobileLoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        addLoginScreen();
    }

    private void startMainActivity() {
        finish();
        if (LoginSDK.getInstance().getListener() != null) {
            LoginSDK.getInstance().getListener().onSuccess(LoginSDK.getLoginCredentials());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LoginSDK.getInstance().getListener() != null) {
            LoginSDK.getInstance().getListener().onFailure(new Exception("Login failed."));
        }
    }

    private MobileLogin mobileLogin;

    public void addLoginScreen() {
        if (mobileLogin == null) {
            mobileLogin = MobileLogin.newInstance(new MobileLogin.Listener() {
                @Override
                public void addSignUpScreen() {
                    addSignup();
                }

                @Override
                public void addForgotPasswordScreen() {
                    addForgotPassword();
                }

                @Override
                public void onLoginSuccess() {
                    startMainActivity();
                }
            });
        }
        addFragment(mobileLogin, R.id.login_container, "login");
    }

    private MobileSignUp mobileSignUp;

    public void addSignup() {
        if (mobileSignUp == null) {
            mobileSignUp = MobileSignUp.newInstance(new MobileSignUp.Listener() {
                @Override
                public void addLoginOption() {
                    addLoginScreen();
                }

                @Override
                public void onSignUpCompleted(Profile profile) {
                    openSuccessRegistrationDialog(profile);
                }

                @Override
                public void onVerifyAndSignUp(String name, String mobile, String password) {
                    addVerifyOtpScreen(mobile, name, password, false);
                }
            });
        }
        addFragment(mobileSignUp, R.id.login_container, "signup");
    }

    private AlertDialog alertDialog;

    private void openSuccessRegistrationDialog(Profile profile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_registration_successful, null);
        TextView tvRegNo = dialogView.findViewById(R.id.tv_reg_no);
        TextView tvPassword = dialogView.findViewById(R.id.tv_password);
        tvRegNo.setText("Admission No : " + profile.getAdmissionNo());
        tvPassword.setText("Password : " + profile.getPassword());
        (dialogView.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                startMainActivity();
            }
        });
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private MobileForgotPassword mobileForgotPassword;

    public void addForgotPassword() {
        if (mobileForgotPassword == null) {
            mobileForgotPassword = MobileForgotPassword.newInstance(new MobileForgotPassword.Listener() {
                @Override
                public void onAddSignUpScreen() {
                    addSignup();
                }

                @Override
                public void addVerifyScreen(String mobile, boolean isOpenChangePasswordOption) {
                    addVerifyOtpScreen(mobile, null, null, isOpenChangePasswordOption);
                }
            });
        }
        addFragment(mobileForgotPassword, R.id.login_container, "forgotPassword");
    }

    private MobileOTPVerification mobileOTPVerification;

    private void addVerifyOtpScreen(String mobile, String name, String password, boolean isOpenChangePasswordOption) {
        if (mobileOTPVerification == null) {
            mobileOTPVerification = MobileOTPVerification.newInstance(mobile, name, password, isOpenChangePasswordOption, new MobileOTPVerification.Listener() {
                @Override
                public void addLoginOption() {
                    addLoginScreen();
                }

                @Override
                public void onLoginSuccess() {
                    startMainActivity();
                }

                @Override
                public void addPasswordChangeScreen() {
                    addPasswordChange(true);
                }
            });
        }
        addFragment(mobileOTPVerification, R.id.login_container, "OTPVerification");
    }

    private MobileChangePassword mobileChangePassword;

    public void addPasswordChange(boolean isForgetPass) {
        if (mobileChangePassword == null) {
            mobileChangePassword = MobileChangePassword.newInstance(isForgetPass, new MobileChangePassword.Listener() {
                @Override
                public void onPasswordChangedSuccess() {
                    startMainActivity();
                }
            });
        }
        addFragment(mobileChangePassword, R.id.login_container, "changePassword");
    }

}