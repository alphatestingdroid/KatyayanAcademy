package com.appsfeature.login.activity;

import android.os.Bundle;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.fragment.mobile.MobileChangePassword;
import com.appsfeature.login.fragment.mobile.MobileForgotPassword;
import com.appsfeature.login.fragment.mobile.MobileLogin;
import com.appsfeature.login.fragment.mobile.MobileOTPVerification;
import com.appsfeature.login.fragment.mobile.MobileSignUp;
import com.appsfeature.login.util.LoginUtil;


/**
 * Created by Admin on 5/22/2017.
 */

public class ChangePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        addPasswordChange( false);
    }

    private MobileChangePassword mobileChangePassword;

    public void addPasswordChange(boolean isForgetPass) {
        if(mobileChangePassword == null){
            mobileChangePassword = MobileChangePassword.newInstance(isForgetPass, new MobileChangePassword.Listener() {
                @Override
                public void onPasswordChangedSuccess() {
                    finish();
                }
            });
        }
        addFragmentWithoutBackstack(mobileChangePassword, R.id.login_container, "changePassword");
    }

}