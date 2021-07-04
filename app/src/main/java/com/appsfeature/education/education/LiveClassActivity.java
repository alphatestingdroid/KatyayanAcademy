package com.appsfeature.education.education;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.doctor.VideoModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.player.util.YTUtility;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.SupportUtil;
import com.helper.util.BaseUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class LiveClassActivity extends BaseActivity {

    private View llNoData;
    private VideoModel mVideoModel;
    private TextView tvTitle, tvSubject, tvDate;
    private Button btnLiveClass;
    private boolean isActiveLiveClass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_class);
        setUpToolBar("Live Class");
        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
        if (getExtraProperty() != null) {
            appPresenter.getLiveClass(getExtraProperty().getCourseId());
        }
    }

    private void initUi() {
        llNoData = findViewById(R.id.ll_no_data);
        tvTitle = findViewById(R.id.tv_live_title);
        tvSubject = findViewById(R.id.tv_live_subject);
        tvDate = findViewById(R.id.tv_live_date);
        btnLiveClass = findViewById(R.id.btn_open_live_class);
        btnLiveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isActiveLiveClass && mVideoModel != null){
                    YTUtility.playVideo(LiveClassActivity.this, mVideoModel);
                }else {
                    SupportUtil.showToast(LiveClassActivity.this, "Class was not live, Please wait.");
                }
            }
        });
        hideViews();
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        if (response.getVideoList() != null && response.getVideoList().size() > 0) {
            mVideoModel = response.getVideoList().get(0);
            loadData();
        } else {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
            hideViews();
        }
    }

    private void hideViews() {
        tvTitle.setVisibility(View.GONE);
        tvSubject.setVisibility(View.GONE);
        tvDate.setVisibility(View.GONE);
        btnLiveClass.setText(getString(R.string.no_live_class_available));
    }

    private void loadData() {
        tvTitle.setText(mVideoModel.getLectureName());
        tvSubject.setText(mVideoModel.getSubjectName());
        tvDate.setText(SupportUtil.getDateFormatted(mVideoModel.getLiveClassDate(), mVideoModel.getLiveClassTime()));
        btnLiveClass.setText(getString(R.string.open_live_class));
        tvTitle.setVisibility(View.VISIBLE);
        tvSubject.setVisibility(View.VISIBLE);
        tvDate.setVisibility(View.VISIBLE);

        calculateTimeLeft();
        startCountDown();
    }

    private void calculateTimeLeft() {
        Calendar mCalender = getCalendar();
        if(mCalender != null) {
            long seconds = ((mCalender.getTimeInMillis() - System.currentTimeMillis()) / 1000);

            int day = (int) TimeUnit.SECONDS.toDays(seconds);
            long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
            long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
            long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
            String timeLeft = String.format(Locale.US, "%d Days %d hr %d min %d sec", day, (int) hours, (int) minute, (int) second);
            btnLiveClass.setText(timeLeft);
            if(day <= 0 && hours <= 0 && minute <= 0 && second <= 0){
                isActiveLiveClass = true;
                btnLiveClass.setText(getString(R.string.open_live_class));
            }
        }
    }

    public Calendar getCalendar() {
        try {
            Date inputDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US).parse(mVideoModel.getLiveClassDate() +"-"+mVideoModel.getLiveClassTime());
            Calendar date = Calendar.getInstance();
            if (inputDate != null) {
                date.setTime(inputDate);
            }
//            date.add(Calendar.MONTH, 1);
//            date.set(inputDate.getYear(), date.get(Calendar.MONTH), 1, 0, 0, 0);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean active;

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
        countDown.cancel();
    }

    private CountDownTimer countDown;

    private void startCountDown() {
        countDown = new CountDownTimer(24 * 60 * 60, 1000) {

            public void onTick(long millisUntilFinished) {
                if (active) {
                    calculateTimeLeft();
                } else
                    this.cancel();
            }

            public void onFinish() {
                if (active) {
                    calculateTimeLeft();
                } else
                    this.cancel();
            }
        }.start();
    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }

}
