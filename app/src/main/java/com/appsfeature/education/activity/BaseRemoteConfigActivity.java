package com.appsfeature.education.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebaseconfig.RemoteConfig;

public abstract class BaseRemoteConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteConfig.newInstance(this)
                .fetch(new RemoteConfig.RemoteCallback() {
                    @Override
                    public void onValidUser() {
                    }

                    @Override
                    public void onInvalidUser(String message) {
                        showErrorMessage(message);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
