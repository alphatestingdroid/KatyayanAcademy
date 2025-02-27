package com.appsfeature.education.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.DirectionUtil;
import com.appsfeature.education.R;
import com.appsfeature.education.listeners.ContentType;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.DynamicUrlCreator;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.network.NetworkApiEndPoint;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.browser.BrowserSdk;
import com.config.util.ConfigUtil;
import com.google.android.material.navigation.NavigationView;
import com.helper.util.BaseDynamicUrlCreator;
import com.helper.util.BaseUtil;

public class MainActivity extends BaseInAppUpdateImmediateActivity implements View.OnClickListener
        , NavigationView.OnNavigationItemSelectedListener, BaseDynamicUrlCreator.DynamicUrlResult {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MenuItem miProfile;
    private TextView tvProfile;
    private ImageView ivProfile, imgProfile;
    private TextView tvName, tvAdmissionNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initView();
        onNewIntent(getIntent());
    }

    private Toolbar toolbar;

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_nav_drawer);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Home");
            }
        }
    }

    private void initView() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        imgProfile = findViewById(R.id.img_profile);
        tvName = findViewById(R.id.name);
        tvAdmissionNo = findViewById(R.id.phoneNo);

        navigationView.setNavigationItemSelectedListener(this);
        miProfile = navigationView.getMenu().findItem(R.id.nav_login);

        View header = navigationView.getHeaderView(0).findViewById(R.id.ll_header);
        header.setOnClickListener(this);
        tvProfile = header.findViewById(R.id.tv_profile);
        ivProfile = header.findViewById(R.id.iv_profile);
        (findViewById(R.id.option_1)).setOnClickListener(this);
        (findViewById(R.id.option_2)).setOnClickListener(this);
        (findViewById(R.id.option_3)).setOnClickListener(this);
        (findViewById(R.id.option_4)).setOnClickListener(this);
        (findViewById(R.id.option_5)).setOnClickListener(this);
        (findViewById(R.id.option_6)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        if(RemoteConfig.isAppExpired(this)){
//            SupportUtil.showToast(this, RemoteConfig.getAppExpiredErrorMesāsage(this));
//            return;
//        }
        DirectionUtil.onMainActivityClick(this, v.getId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_share:
                ConfigUtil.share(this, "");
                break;
            case R.id.nav_rate_us:
                ConfigUtil.rateUs(this);
                break;
            case R.id.nav_more_apps:
                ConfigUtil.moreApps(this, AppConstant.DEVELOPER_NAME);
                break;
            case R.id.nav_login:
                openProfile();
                break;
            case R.id.nav_privacy_policy:
                BaseUtil.openLinkInAppBrowser(this, "Privacy Policy", NetworkApiEndPoint.PRIVACY_POLICY);
                break;
            case R.id.nav_logout:
                openLogoutDialog();
                break;
        }
        return true;
    }

    private void openLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                logOut();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void logOut() {
        LoginPrefUtil.clearPreferences();
        startActivity(new Intent(this, SplashScreen.class));
        finish();
    }

    private boolean isUserLogin;

    @Override
    protected void onStart() {
        super.onStart();
        handleLoginData();
    }

    private void handleLoginData() {
        if (AppApplication.getInstance().getLoginSDK() != null) {
            isUserLogin = AppApplication.getInstance().getLoginSDK().isRegComplete();
            if (isUserLogin && miProfile != null) {
                miProfile.setTitle("Profile");
                miProfile.setIcon(R.drawable.ic_nav_profile);
                String name = AppApplication.getInstance().getLoginSDK().getUserName();
                tvAdmissionNo.setText("Admission No : " + AppApplication.getInstance().getLoginSDK().getAdmissionNo());
                if (!TextUtils.isEmpty(name)) {
                    tvName.setText(name);
                    tvProfile.setText(name);
                }
                LoginUtil.loadUserImage(AppApplication.getInstance().getLoginSDK().getUserImage(), ivProfile);
                if (!AppApplication.getInstance().getLoginSDK().getUserImage().contains("default_patient_profile_picture")) {
                    LoginUtil.loadUserImage(AppApplication.getInstance().getLoginSDK().getUserImage(), imgProfile);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openProfile() {
        LoginSDK.getInstance().openLoginPage(this, true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if(DynamicUrlCreator.isValidIntent(this)) {
                new DynamicUrlCreator(this)
                        .register(this);
            }
        }
    }

    @Override
    public void onDynamicUrlResult(Uri uri, String extraData) {
        DynamicUrlCreator.openActivity(this, uri, extraData);
    }

    @Override
    public void onError(Exception e) {
        Log.d("DynamicUrlCreator", "onError:" + e.toString());
    }

}
