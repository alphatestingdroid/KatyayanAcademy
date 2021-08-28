package com.appsfeature.education.player;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.appsfeature.education.R;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.player.util.YTUtility;
import com.appsfeature.education.task.YTInsertWatchListTask;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.DynamicUrlCreator;
import com.appsfeature.education.util.Logger;
import com.appsfeature.education.util.SupportUtil;
import com.appsfeature.login.LoginSDK;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * @author Created by Abhijit on 25/06/2018.
 */
public class YTPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayer youTubePlayer;
    private boolean mOrientationLandScape = false;
    private String mVideoId;
    private EducationModel mEducationModel;
    private String mTitle = "Player";
    private TextView tvLectureName, tvLectureSubject, tvLectureDate, tvLectureDiscription;
    private WebView webView;
    private boolean isLiveClass = false;
    private View layoutDescription;
    private ExtraProperty extraProperty;
    private int videoTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        initView();
        getArguments(getIntent());
    }

    private void getArguments(Intent intent) {
        if (intent.getSerializableExtra(AppConstant.CATEGORY_PROPERTY) instanceof ExtraProperty) {
            extraProperty = (ExtraProperty) intent.getSerializableExtra(AppConstant.CATEGORY_PROPERTY);
            if(extraProperty != null && extraProperty.getVideoId() != null){
                mVideoId = extraProperty.getVideoId();
                videoTime = extraProperty.getVideoTime();
                mEducationModel = extraProperty.getEducationModel();
                isLiveClass = extraProperty.isLiveClass();
                if(mEducationModel != null && !TextUtils.isEmpty(mEducationModel.getLectureName())){
                    mTitle = mEducationModel.getLectureName();
                    loadView();
                }
                initToolBarTheme(mTitle);
                loadVideo(extraProperty.getApiKey());
            }else {
                SupportUtil.showToast(this, AppConstant.INVALID_PROPERTY);
                finish();
            }
        } else {
            SupportUtil.showToast(this, AppConstant.INVALID_PROPERTY);
            finish();
        }
    }

    private void loadView() {
        tvLectureName.setText(mEducationModel.getLectureName());
        tvLectureSubject.setText(mEducationModel.getSubjectName());
        if(!TextUtils.isEmpty(mEducationModel.getLiveClassDate())) {
            tvLectureDate.setText(SupportUtil.getDateFormatted(mEducationModel.getLiveClassDate(), mEducationModel.getLiveClassTime()));
            tvLectureDate.setVisibility(View.VISIBLE);
        }else {
            tvLectureDate.setVisibility(View.INVISIBLE);
        }
        tvLectureDiscription.setText(mEducationModel.getLectureDescription());
        applySettings(webView);
        if(isLiveClass){
            webView.setVisibility(View.VISIBLE);
            layoutDescription.setVisibility(View.GONE);
        }else {
            webView.setVisibility(View.GONE);
            layoutDescription.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        tvLectureName = findViewById(R.id.tv_lecture_name);
        tvLectureSubject = findViewById(R.id.tv_lecture_subject);
        tvLectureDate = findViewById(R.id.tv_lecture_date);
        tvLectureDiscription = findViewById(R.id.tv_lecture_description);
        layoutDescription = findViewById(R.id.layout_description);
        webView = findViewById(R.id.webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void applySettings(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    }

    public void loadChatWindow(String videoId){
        if (webView != null && isLiveClass) {
            webView.loadUrl(AppConstant.URL_LIVE_CLASS_CHAT + videoId + "/" + LoginSDK.getInstance().getAdmissionNo());
        }
    }

    private void loadVideo(String apiKey) {
        if (TextUtils.isEmpty(apiKey)) {
            apiKey = getString(R.string.google_api_key);
            if (TextUtils.isEmpty(apiKey)) {
                if (!TextUtils.isEmpty(mVideoId)) {
                    YTUtility.openYoutubeApp(this, mVideoId);
                }
                finish();
            }
        }
        YouTubePlayerView playerView = findViewById(R.id.youtubePlayerView);
        playerView.initialize(apiKey, this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.youTubePlayer = youTubePlayer;
        Logger.log("onInitializationSuccess");

        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!wasRestored) {
            youTubePlayer.cueVideo(mVideoId, videoTime);
        }

        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean isFullScreen) {
                mOrientationLandScape = isFullScreen;
            }
        });

        loadChatWindow(mVideoId);
    }


    //Toast pop up messaging to show errors.
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();

        } else {
            String errorMessage = String.format("There was an error initializing the YoutubePlayer (&1$s)", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    private final YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Logger.log("onPlaying");
        }

        @Override
        public void onPaused() {
            Logger.log("onPaused");
        }

        @Override
        public void onStopped() {
            Logger.log("onStopped");
        }

        @Override
        public void onBuffering(boolean b) {
            Logger.log("onBuffering");
        }

        @Override
        public void onSeekTo(int i) {
            Logger.log("onSeekTo");
        }
    };

    private final YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            Logger.log("onLoading");
        }

        @Override
        public void onLoaded(String s) {
            Logger.log("onLoaded");
            youTubePlayer.play();
        }

        @Override
        public void onAdStarted() {
            Logger.log("onAdStarted");
        }

        @Override
        public void onVideoStarted() {
            Logger.log("onVideoStarted");
        }

        @Override
        public void onVideoEnded() {
            Logger.log("onVideoEnded");
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Logger.log("onError");
        }
    };

    public void initToolBarTheme(String title) {
        TextView tvTitle = findViewById(R.id.tv_titile);
        tvTitle.setText(title);
        (findViewById(R.id.iv_action_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        (findViewById(R.id.iv_action_full_screen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youTubePlayer != null) {
                    mOrientationLandScape = !mOrientationLandScape;
                    youTubePlayer.setFullscreen(mOrientationLandScape);
                }
            }
        });
        (findViewById(R.id.iv_action_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DynamicUrlCreator(YTPlayerActivity.this)
                        .shareVideo(extraProperty.getVideoId(), extraProperty
                                , extraProperty.getTitle());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (youTubePlayer != null) {
            youTubePlayer.setFullscreen(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (youTubePlayer != null && mOrientationLandScape) {
            mOrientationLandScape = false;
            youTubePlayer.setFullscreen(false);
        } else {
            closeYoutubePlayer();
            super.onBackPressed();
        }
    }

    private void closeYoutubePlayer() {
        try {
            if (youTubePlayer != null) {
                youTubePlayer.pause();
                extraProperty.setVideoTime(youTubePlayer.getCurrentTimeMillis());
            }
            new YTInsertWatchListTask(extraProperty).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (youTubePlayer != null && youTubePlayer.isPlaying()) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mOrientationLandScape = false;
                youTubePlayer.setFullscreen(false);
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mOrientationLandScape = true;
                youTubePlayer.setFullscreen(true);
            }
        }
    }
}
