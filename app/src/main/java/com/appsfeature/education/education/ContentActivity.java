package com.appsfeature.education.education;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.ContentAdapter;
import com.appsfeature.education.entity.PresenterModel;
import com.appsfeature.education.listeners.ContentType;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.player.util.YTUtility;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;
import com.helper.util.BaseConstants;

import java.util.ArrayList;
import java.util.List;


public class ContentActivity extends BaseActivity {

    private View llNoData;
    private ContentAdapter adapter;
    private final List<EducationModel> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_list);

        onInitializeUI();
//        setUpToolBar("Video Lecture");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getExtraProperty() != null) {
            appPresenter.getDynamicData(getExtraProperty());
        }
    }

    public void onInitializeUI() {
        llNoData = findViewById(R.id.ll_no_data);
        RecyclerView rvList = findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ContentAdapter(this, getExtraProperty().getContentType(), mList, new Response.OnClickListener<EducationModel>() {
            @Override
            public void onItemClicked(View view, EducationModel item) {
                if(SupportUtil.parseInt(item.getLectureType()) == ContentType.TYPE_VIDEO) {
                    YTUtility.playVideo(ContentActivity.this, item, false);
                }else if(SupportUtil.parseInt(item.getLectureType()) == ContentType.TYPE_PDF) {
                    AppApplication.getInstance().openPdf(ContentActivity.this, SupportUtil.parseInt(item.getId()), item.getLectureName(), item.getLectureNotes(), item.getSubjectName());
                }else{
                    SupportUtil.showToast(ContentActivity.this, BaseConstants.Error.MSG_ERROR);
                }
            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        mList.clear();
        if (response.getEducationList() != null && response.getEducationList().size() > 0) {
            mList.addAll(response.getEducationList());
        } else {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorOccurred(Exception e) {
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
