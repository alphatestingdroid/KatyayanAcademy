package com.appsfeature.education.patient.fragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.adapter.FamilyMemberAdapter;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.SupportUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.helper.callback.Response;


public class FamilyMemberSelector implements Response.OnClickListener<PatientModel> {
    private Response.OnClickListener<PatientModel> mListener;
    private BottomSheetDialog mBottomSheetDialog;
    private PatientModel mItem;

    public static FamilyMemberSelector newInstance(PatientModel mItem, Response.OnClickListener<PatientModel> mListener) {
        FamilyMemberSelector fragment = new FamilyMemberSelector();
        fragment.mItem = mItem;
        fragment.mListener = mListener;
        return fragment;
    }

    public void show(Context context) {
        if (context != null) {
            mBottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            mBottomSheetDialog.setContentView(viewHolder(inflater.inflate(R.layout.dialog_member_selection, null)));
            mBottomSheetDialog.show();
        }
    }

    private View viewHolder(View v) {
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
        View llNoData = v.findViewById(R.id.ll_no_data);
        LinearLayoutManager horizontalManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(horizontalManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mItem != null && mItem.getFamilyMember()!=null && mItem.getFamilyMember().size() > 0) {
            mRecyclerView.setAdapter(new FamilyMemberAdapter(mItem.getFamilyMember(), this));
            SupportUtil.showNoData(llNoData, View.GONE);
        }
        (v.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
                mBottomSheetDialog = null;
            }
        });
        return v;
    }

    @Override
    public void onItemClicked(View view, PatientModel item) {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mListener.onItemClicked(view, item);
        mBottomSheetDialog = null;
    }
}
