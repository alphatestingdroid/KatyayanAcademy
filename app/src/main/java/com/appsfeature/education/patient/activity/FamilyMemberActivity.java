package com.appsfeature.education.patient.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.FamilyMemberManagerAdapter;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.entity.PresenterModel;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.SupportUtil;
import com.appsfeature.login.dialog.ErrorDialog;
import com.helper.callback.Response;

import java.util.ArrayList;
import java.util.List;


public class FamilyMemberActivity extends BaseActivity {

    private View llNoData;
    private FamilyMemberManagerAdapter adapter;
    private List<PatientModel> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_list);

        onInitializeUI();
        setUpToolBar("Family Members");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getExtraProperty() != null) {
//            appPresenter.getPatientProfile(AppApplication.getInstance().getLoginSDK().getUserId());
        }
    }

    public void onInitializeUI() {
        llNoData = findViewById(R.id.ll_no_data);
        RecyclerView rvList = findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FamilyMemberManagerAdapter(mList, new AppCallback.OnClickListener<PatientModel>() {
            @Override
            public void onItemClicked(View view, PatientModel item) {
//                ClassUtil.openPatientBookingHistoryActivity(FamilyMemberActivity.this, item.getName());
            }

            @Override
            public void onItemDeleteClicked(View view, PatientModel item) {
                deleteFamilyMemberDialog(item);
            }
        });
        rvList.setAdapter(adapter);

        findViewById(R.id.add_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ClassUtil.openAddFamilyMemberActivity(FamilyMemberActivity.this);
            }
        });
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        mList.clear();
        if (response.getPatientModel() != null && response.getPatientModel().getFamilyMember() != null
                && response.getPatientModel().getFamilyMember().size() > 0) {
            mList.addAll(response.getPatientModel().getFamilyMember());
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

    private void deleteFamilyMemberDialog(PatientModel item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this Member?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                deleteFamilyMember(item);
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

    private void deleteFamilyMember(PatientModel item) {
        SupportUtil.showDialog(this, getString(R.string.helper_popup_progress_message), true);
//        appPresenter.deleteFamilyMember(AppApplication.getInstance().getLoginSDK().getUserId(), item.getName(), item.getGender(), item.getRelation(), item.getDateOfBirth(), new Response.Callback<PatientModel>() {
//            @Override
//            public void onSuccess(PatientModel response) {
//                onStart();
//                SupportUtil.hideDialog();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                SupportUtil.hideDialog();
//                SupportUtil.showToast(FamilyMemberActivity.this, e.getMessage());
//            }
//        });
    }
}
