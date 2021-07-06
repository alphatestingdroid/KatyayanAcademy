package com.appsfeature.education.education;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.VideoLectureAdapter;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.entity.PresenterModel;
import com.appsfeature.education.player.util.YTUtility;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.util.ArrayList;
import java.util.List;


public class VideoLectureActivity extends BaseActivity {

    private View llNoData;
    private VideoLectureAdapter adapter;
    private final List<EducationModel> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        onInitializeUI();
        setUpToolBar("Video Lecture");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getExtraProperty() != null) {
            appPresenter.getVideoLecture(getExtraProperty().getCourseId());
        }
    }

    public void onInitializeUI() {
        llNoData = findViewById(R.id.ll_no_data);
        RecyclerView rvList = findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new VideoLectureAdapter(this, mList, new Response.OnClickListener<EducationModel>() {
            @Override
            public void onItemClicked(View view, EducationModel item) {
                YTUtility.playVideo(VideoLectureActivity.this, item);
            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        mList.clear();
        if (response.getVideoList() != null && response.getVideoList().size() > 0) {
            mList.addAll(response.getVideoList());
        } else {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorOccurred(Exception e) {
        SupportUtil.showToast(this, e.getMessage());
        if (mList != null && mList.size() < 1) {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        }else {
            SupportUtil.showNoData(llNoData, View.GONE);
        }
    }

    @Override
    public void onStartProgressBar() {
        SupportUtil.showNoDataProgress(llNoData);
    }

    @Override
    public void onStopProgressBar() {

    }
}
