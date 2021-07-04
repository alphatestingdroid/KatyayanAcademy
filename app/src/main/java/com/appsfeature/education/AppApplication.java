package com.appsfeature.education;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.appsfeature.education.util.AppConstant;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.LoginType;
import com.config.config.ConfigManager;
import com.config.util.ConfigUtil;
import com.helper.util.DayNightPreference;

public class AppApplication extends Application {


    private static final String HOST_URL = "http://www.appsfeature.com/MrBizz/index.php/";
    private static AppApplication instance;
    private LoginSDK loginSdk;
    private ConfigManager configManager;

    public static AppApplication getInstance() {
        return instance;
    }
    public ViewModelProvider.Factory viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(this);

    public ViewModelProvider.Factory getViewModelFactory() {
        return viewModelFactory;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DayNightPreference.setNightMode(this, false);
        instance = this;
        getConfigManager();
        getLoginSDK();
    }


    public ConfigManager getConfigManager() {
        if(configManager == null){
            configManager = ConfigManager.getInstance(this, ConfigUtil.getSecurityCode(this), BuildConfig.DEBUG);
            configManager.setConfigHost(instance, AppConstant.BASE_URL)
                    .setEnableConfigManager(false);
//            configManager.setEnableLoadFromCache(true);
        }
        return configManager;
    }

    public void loadConfig() {
        if (configManager != null) {
            configManager.loadConfig();
        }
    }


    public LoginSDK getLoginSDK() {
        if(loginSdk == null) {
            loginSdk = LoginSDK.getInstance(this, getConfigManager())
                    .setLoginType(LoginType.TYPE_MOBILE)
                    .setDebugMode(BuildConfig.DEBUG)
                    .setVersionName(BuildConfig.VERSION_NAME);
        }
        return loginSdk;
    }
}
