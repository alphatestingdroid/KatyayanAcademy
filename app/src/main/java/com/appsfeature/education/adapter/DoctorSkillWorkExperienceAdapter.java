package com.appsfeature.education.adapter;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.model.WorkExperience;

import java.util.List;

public class DoctorSkillWorkExperienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WorkExperience> mList;

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_skill_education, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public DoctorSkillWorkExperienceAdapter(List<WorkExperience> mList) {
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.tvTitle.setText(mList.get(i).getHospitalName());
        myViewHolder.tvSubTitle.setText(getSubTitle(mList.get(i)));
    }

    private String getSubTitle(WorkExperience education) {
        String subTitle = "";
        if(!TextUtils.isEmpty(education.getDesignation())){
            subTitle += education.getDesignation();
        }
        if(!TextUtils.isEmpty(education.getFrom())){
            if(!TextUtils.isEmpty(education.getTo())){
                subTitle += "\n" + education.getFrom() + " - " + education.getTo();
            }else {
                subTitle += "\n" + education.getFrom();
            }
        }
        return subTitle;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvSubTitle = view.findViewById(R.id.tv_sub_title);
        }
    }

}
