package com.appsfeature.education.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.login.LoginSDK;

import java.util.List;

public class FamilyMemberManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final AppCallback.OnClickListener<PatientModel> clickListener;
    private List<PatientModel> mList;
    private String username;

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_family_member, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public FamilyMemberManagerAdapter(List<PatientModel> mList, AppCallback.OnClickListener<PatientModel> clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
        this.username = LoginSDK.getInstance().getFirstName();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.tvTitle.setText(mList.get(i).getName());
        myViewHolder.tv_relation.setText("Relation : " + mList.get(i).getRelation());
        myViewHolder.tv_dob.setText("DOB : " + DateTimeUtil.getDateInMobileViewFormatFromDOB(mList.get(i).getDateOfBirth()));

        if (mList.get(i).getGender().equalsIgnoreCase("male")) {
            myViewHolder.ic_gender.setImageResource(R.drawable.ic_male);
        } else if (mList.get(i).getGender().equalsIgnoreCase("female")) {
            myViewHolder.ic_gender.setImageResource(R.drawable.ic_female);
        } else {
            myViewHolder.ic_gender.setImageResource(R.drawable.ic_trans);
        }

        if(mList.get(i).getName().equalsIgnoreCase(username) && mList.get(i).getRelation().equalsIgnoreCase("self")){
            myViewHolder.iv_delete.setVisibility(View.GONE);
        }else {
            myViewHolder.iv_delete.setVisibility(View.VISIBLE);
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ic_gender;
        private final View iv_delete;
        private TextView tvTitle, tv_relation, tv_dob;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            ic_gender = view.findViewById(R.id.ic_gender);
            tv_relation = view.findViewById(R.id.tv_relation);
            tv_dob = view.findViewById(R.id.tv_dob);
            iv_delete = view.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(this);
            iv_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_delete) {
                clickListener.onItemDeleteClicked(v, mList.get(getAdapterPosition()));
            } else {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
            }
        }
    }

}
