package com.appsfeature.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.appsfeature.login.activity.ChangePasswordActivity;
import com.appsfeature.login.activity.MobileLoginActivity;
import com.appsfeature.login.activity.ProfileActivity;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginDataUtil;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.config.config.ConfigManager;
import com.squareup.picasso.Picasso;


public class LoginSDK {
    private static LoginSDK instance;
    private final Context context;
    private Activity activity;
    private String versionName;
    private final ConfigManager configManager;
    private Listener listener;
    private boolean isHeaderTitle = true;
    private LoginType loginType = LoginType.TYPE_EMAIL;
    private boolean isDebugMode = false;

    public static Profile getLoginCredentials() {
        return new Profile();
    }

    public String getVersionName() {
        return versionName;
    }

    public LoginSDK setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public LoginSDK setDebugMode(boolean isDebugMode) {
        this.isDebugMode = isDebugMode;
        return this;
    }

    public boolean isDebugMode() {
        return isDebugMode;
    }

    public interface Listener {
        void onSuccess(Profile response);

        void onFailure(Exception e);
    }

    public Listener getListener() {
        return listener;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    private LoginSDK(Context context, ConfigManager configManager) {
        this.context = context;
        this.configManager = configManager;
        LoginDataUtil.getInstance(context);
    }

    public Context getContext() {
        return context;
    }

    public static LoginSDK getInstance() {
        return instance;
    }

    public static LoginSDK getInstance(Context context, ConfigManager configManager) {
        if (instance == null) {
            instance = new LoginSDK(context, configManager);
        }
        return instance;
    }

    public LoginSDK setLoginType(LoginType loginType) {
        this.loginType = loginType;
        return this;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public LoginSDK addListener(Listener listener) {
        this.listener = listener;
        return this;
    }


    public static void openChangePasswordActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ChangePasswordActivity.class));
    }

    public void openLoginPage(final Context context, final boolean isOpenProfile) {
        openLoginPage(context, isOpenProfile, false);
    }

    public void openLoginPage(final Context context, final boolean isOpenProfile, final boolean isFinishWhenCompleteUpdate) {
        openLoginPage(context, isOpenProfile, isFinishWhenCompleteUpdate, 0);
    }

    public void openLoginPage(final Context context, final boolean isOpenProfile, final boolean isFinishWhenCompleteUpdate, int requestCode) {
        if (isOpenProfile && LoginPrefUtil.isRegComplete()) {
            Intent intent = new Intent(context, ProfileActivity.class)
                    .putExtra(LoginConstant.EXTRA_PROPERTY, isFinishWhenCompleteUpdate);
            if (requestCode == 0) {
                context.startActivity(intent);
            } else if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
        } else if (!LoginPrefUtil.isLoginComplete()) {
            if (loginType == LoginType.TYPE_MOBILE) {
                context.startActivity(new Intent(context, MobileLoginActivity.class));
            } else {
                LoginUtil.showToast(context, LoginConstant.ERROR_INVALID_LOGIN_TYPE);
            }
        }
    }

    public String getUserName() {
        return LoginPrefUtil.getUserName();
    }

    public String getFirstName() {
        return LoginPrefUtil.getFirstName();
    }

    public boolean isRegComplete() {
        return LoginPrefUtil.isRegComplete();
    }

    public boolean isProfileCompleted() {
        return LoginPrefUtil.isProfileCompleted();
    }

    public String getUserImage() {
        return LoginPrefUtil.getUserImage();
    }

    public String getUserId() {
        return LoginPrefUtil.getUserId();
    }

    public String getAdmissionNo() {
        return LoginPrefUtil.getAdmissionNo();
    }

    public int getCourseId() {
        return LoginPrefUtil.getCourseId();
    }

    public int getSubCourseId() {
        return LoginPrefUtil.getSubCourseId();
    }

    public String getUserMobile() {
        return LoginPrefUtil.getUserMobile();
    }

    public String getEmailId() {
        return LoginPrefUtil.getEmailId();
    }

    private Picasso picasso;

    public Picasso getPicasso() {
        if (picasso == null) {
            this.picasso = Picasso.get();
        }
        return picasso;
    }
}
