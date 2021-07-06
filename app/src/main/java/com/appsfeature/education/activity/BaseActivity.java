package com.appsfeature.education.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.viewmodel.AppViewModel;

public abstract class BaseActivity extends AppCompatActivity implements AppCallback.View {

    protected AppViewModel appPresenter;

    public ExtraProperty getExtraProperty() {
        return appPresenter.getExtraProperty();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPresenter = new ViewModelProvider(getViewModelStore(), AppApplication.getInstance().getViewModelFactory()).get(AppViewModel.class);

        appPresenter.initialize(this, getIntent());

        if(getExtraProperty()!=null && !TextUtils.isEmpty(getExtraProperty().getTitle())){
            setUpToolBar(getExtraProperty().getTitle());
        }
    }

    protected void setUpToolBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(title)) {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == android.R.id.home ){
            onBackPressed();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
