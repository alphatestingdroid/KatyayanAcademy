package com.appsfeature.education.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginPrefUtil;
import com.helper.util.DayNightPreference;


public class SplashScreen extends Activity {
	final Context context = this;
	
	// Splash screen timer
	private static final int SPLASH_TIME_OUT = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DayNightPreference.setNightMode(this, false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		callConfig();
	}

	private void callConfig() {
		if (AppApplication.getInstance() != null &&
				(AppApplication.getInstance().getConfigManager() == null
						|| !AppApplication.getInstance().getConfigManager().isConfigLoaded())) {
			AppApplication.getInstance().loadConfig();
		}
		onLoginOpen();
	}

	public void onLoginOpen() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(LoginPrefUtil.isLoginComplete()){
					startMainActivity();
				}else {
					AppApplication.getInstance().getLoginSDK().addListener(new LoginSDK.Listener() {
						@Override
						public void onSuccess(Profile profile) {
							startMainActivity();
						}

						@Override
						public void onFailure(Exception e) {
							finish();
						}
					}).openLoginPage(SplashScreen.this, false);
				}
			}
		}, SPLASH_TIME_OUT);
	}

	private void startMainActivity() {
		Intent intent = getIntent();
		intent.setClass(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
		startActivity(intent);
		new Handler(Looper.myLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					finishAffinity();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 500);
	}
}
