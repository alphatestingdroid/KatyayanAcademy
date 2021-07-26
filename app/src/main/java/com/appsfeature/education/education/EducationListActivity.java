package com.appsfeature.education.education;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.EducationAdapter;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.entity.PresenterModel;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.SupportUtil;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.dialog.ErrorDialog;
import com.helper.callback.Response;

import java.util.ArrayList;
import java.util.List;


public class EducationListActivity extends BaseActivity implements Response.OnClickListener<EducationModel> {

    private View llNoData;
    private EducationAdapter adapter;
    private final List<EducationModel> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list);

        onInitializeUI();
        if (getExtraProperty() != null) {
            appPresenter.getDynamicData(getExtraProperty());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (getExtraProperty() != null) {
//            appPresenter.getDynamicData(getExtraProperty());
//        }
    }

    public void onInitializeUI() {
        llNoData = findViewById(R.id.ll_no_data);
        RecyclerView rvList = findViewById(R.id.recycler_view);

        if(getExtraProperty().getItemType() == ItemType.CATEGORY_TYPE_SUBJECT){
            rvList.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        adapter = new EducationAdapter(this, getExtraProperty().getItemType(), mList, this);
        rvList.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View view, EducationModel item) {
        ExtraProperty property = getExtraProperty().getClone();
        property.setCourseId(LoginSDK.getInstance().getCourseId());
        property.setSubCourseId(LoginSDK.getInstance().getSubCourseId());
        property.setEducationModel(item);

        if(getExtraProperty().getItemType() == ItemType.CATEGORY_TYPE_SUBJECT){
            property.setSubjectId(item.getId());
            property.setTitle(item.getSubjectName());
            property.setItemType(ItemType.CATEGORY_TYPE_CHAPTER);
        }else if(property.getItemType() == ItemType.CATEGORY_TYPE_CHAPTER){
            property.setChapterId(item.getId());
            property.setTitle(item.getChapterName());
            if(property.isOldVideos()){
                property.setItemType(ItemType.CATEGORY_TYPE_OLD_VIDEOS);
            }else {
                property.setItemType(ItemType.CATEGORY_TYPE_OFFLINE_VIDEOS);
            }
        }else if(item.getCategoryType() > 0) {
            property.setItemType(item.getCategoryType());
        }
        ClassUtil.openActivity(this, property);
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
        ErrorDialog.newInstance(this, e.getMessage()).show();
        if (mList != null && mList.size() < 1) {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        } else {
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
