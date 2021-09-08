package com.appsfeature.education.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.appsfeature.education.R;
import com.appsfeature.education.util.AppConstant;
import com.browser.BrowserSdk;
import com.browser.activity.BaseToolbarActivity;
import com.browser.browser.BrowserWebView;
import com.browser.interfaces.BrowserListener;
import com.browser.interfaces.UrlOverloadingListener;
import com.browser.util.BrowserConstant;
import com.helper.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

public class AppBrowserActivity extends BaseToolbarActivity {

    private ProgressBar progressBar;
    private Toolbar toolbar;
    private BrowserWebView webView;
    private String url, title;
    private String mCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);
        setupToolbar();
        initDataFromIntent();
        addUrlOverrideLodingListener();

        progressBar = findViewById(com.browser.R.id.progressBar);

        webView = new BrowserWebView(this);
        webView.init(this);
        webView.addBrowserListener(new BrowserListener() {
            @Override
            public void onToolbarVisibilityUpdate(int isVisible) {
                if (toolbar != null) {
                    toolbar.setVisibility(isVisible);
                }
            }

            @Override
            public void onProgressBarUpdate(int isVisible) {
                if (progressBar != null) {
                    progressBar.setVisibility(isVisible);
                }
            }
        });

        if (TextUtils.isEmpty(url)) {
            BrowserSdk.showToast(this, "Invalid Url");
            finish();
            return;
        }
        webView.loadUrl(url);
    }

    private void initDataFromIntent() {
        Intent intent = getIntent();

        if (intent.hasExtra(BrowserConstant.WEB_VIEW_URL)) {
            url = intent.getStringExtra(BrowserConstant.WEB_VIEW_URL);
        }
        if (intent.hasExtra(BrowserConstant.TITLE)) {
            title = intent.getStringExtra(BrowserConstant.TITLE);
        }
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (title != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onBackPressed() {
        if(mCurrentUrl != null && mCurrentUrl.startsWith(AppConstant.URL_TEST_OPENED)){
            BaseUtil.showToast(this, "Back pressed not allowed here.");
            return;
        }
        if(webView.isWebViewClosedAllPages()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        webView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static void open(Context context, String title, String webUrl) {
        try {
            Intent intent = new Intent(context, AppBrowserActivity.class);
            intent.putExtra(BrowserConstant.WEB_VIEW_URL, webUrl);
            intent.putExtra(BrowserConstant.TITLE, title);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            BrowserSdk.showToast(context, "No option available for take action.");
        }
    }

    private void addUrlOverrideLodingListener() {
        List<String> urlList = new ArrayList<>();
        urlList.add(AppConstant.URL_TEST_GENERATING_RESULT);
        urlList.add(AppConstant.URL_TEST_CLOSE_BUTTON);
        BrowserSdk.getInstance().addUrlOverloadingListener(this.hashCode(), urlList, new UrlOverloadingListener() {
            @Override
            public void onOverrideUrlLoading(WebView view, String url) {
                if(url.equalsIgnoreCase(AppConstant.URL_TEST_GENERATING_RESULT)){
                    BrowserSdk.open(AppBrowserActivity.this, "Test Completed", url);
                    finish();
                }else if(url.equalsIgnoreCase(AppConstant.URL_TEST_CLOSE_BUTTON)){
                    finish();
                }
            }

            @Override
            public void onLoadUrl(String url) {
                mCurrentUrl = url;
            }
        });
    }
}