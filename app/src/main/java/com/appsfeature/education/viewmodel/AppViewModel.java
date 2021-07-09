package com.appsfeature.education.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.entity.PresenterModel;
import com.appsfeature.education.network.NetworkManager;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void getDynamicData(ExtraProperty extraProperty) {
        viewCallback.onStartProgressBar();
        networkHandler.getDynamicData(extraProperty, new Response.Callback<List<EducationModel>>() {
            @Override
            public void onSuccess(List<EducationModel> response) {
                viewCallback.onStopProgressBar();
                viewCallback.onUpdateUI(new PresenterModel().setEducationList(response));
            }

            @Override
            public void onFailure(Exception e) {
                viewCallback.onStopProgressBar();
                viewCallback.onErrorOccurred(e);
            }
        });
    }
}
