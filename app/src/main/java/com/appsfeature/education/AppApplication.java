package com.appsfeature.education;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.DataUtil;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.LoginType;
import com.browser.BrowserSdk;
import com.browser.interfaces.BrowserCallback;
import com.config.config.ConfigManager;
import com.config.util.ConfigUtil;
import com.helper.util.BaseUtil;
import com.helper.util.DayNightPreference;
import com.pdfviewer.PDFViewer;
import com.pdfviewer.util.PDFFileUtil;

public class AppApplication extends Application {


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
        BrowserSdk.getInstance().setCallback(new BrowserCallback() {
            @Override
            public void onOpenPdf(Activity activity, String url) {
                int id = (int) System.currentTimeMillis();
                String fileName = PDFFileUtil.getFileNameFromUrl(url);
                openPdf(activity, id, "Doc" + BaseUtil.getTimeStamp(), fileName, url, null);
            }
        });
        DataUtil.getInstance(this);
        PDFViewer.getInstance()
                .setBaseUrl(AppConstant.BASE_URL_PDF_DOWNLOAD)
                .setDisablePrint(false)
                .setDisableShare(false)
                .init(this);
        PDFViewer.setDownloadDirectory(this,AppConstant.PDF_FOLDER_NAME);
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

    public void openPdf(Activity activity, int id, String pdfTitle, String pdfFileName) {
        openPdf(activity, id, pdfTitle, pdfFileName, null);
    }

    public void openPdf(Activity activity, int id, String pdfTitle, String pdfFileName, String subTitle) {
        String pdfFileUrl = AppConstant.BASE_URL_PDF_DOWNLOAD + pdfFileName;
        openPdf(activity, id, pdfTitle, pdfFileName, pdfFileUrl, subTitle);
    }

    public void openPdf(Activity activity, int id, String pdfTitle, String pdfFileName, String pdfFileUrl, String subTitle) {
        PDFViewer.openPdfDownloadActivity(activity, id, pdfTitle, pdfFileName, AppConstant.BASE_URL_PDF_DOWNLOAD , pdfFileUrl, subTitle);
    }
}
