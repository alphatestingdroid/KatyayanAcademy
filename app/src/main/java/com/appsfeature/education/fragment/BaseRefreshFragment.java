package com.appsfeature.education.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.viewmodel.AppViewModel;

public abstract class BaseRefreshFragment extends Fragment implements AppCallback.View {

    abstract public void onRefreshFragment();

    protected AppViewModel appPresenter;

    public ExtraProperty getExtraProperty() {
        return appPresenter.getExtraProperty();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPresenter = new ViewModelProvider(getViewModelStore(), AppApplication.getInstance().getViewModelFactory()).get(AppViewModel.class);

        appPresenter.initialize(this, getArguments());
    }
}

