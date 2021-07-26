package com.appsfeature.education.adapter;


import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.util.List;

public class VideoLectureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnClickListener<EducationModel> clickListener;
    private final Activity activity;
    private List<EducationModel> mList;

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_video_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public VideoLectureAdapter(Activity activity, List<EducationModel> mList, Response.OnClickListener<EducationModel> clickListener) {
        this.activity = activity;
        this.mList = mList;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.tvName.setText(mList.get(i).getLectureName());
//
        myViewHolder.tvDepartment.setText(mList.get(i).getSubjectName());
        String dateTime = SupportUtil.getDateFormatted(mList.get(i).getLiveClassDate(), mList.get(i).getLiveClassTime());
        if(!TextUtils.isEmpty(dateTime)) {
            myViewHolder.tvDate.setText(dateTime);
            myViewHolder.tvDate.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.tvDate.setVisibility(View.GONE);
        }

//        if(mList.get(i).getProfilePicture() != null) {
//            Picasso.get().load(mList.get(i).getProfilePicture())
//                    .placeholder(R.drawable.ic_user_profile)
//                    .error(R.drawable.ic_user_profile)
//                    .into(myViewHolder.ivPic);
//        } else {
//            myViewHolder.ivPic.setImageResource(R.drawable.ic_user_profile);
//        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivPic;
        private TextView tvName;
        private TextView tvDepartment;
        private TextView tvDate;

        private ViewHolder(View v) {
            super(v);
            ivPic = v.findViewById(R.id.pic);
            tvName = v.findViewById(R.id.name);
            tvDepartment = v.findViewById(R.id.department);
            tvDate = v.findViewById(R.id.location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
            }
        }
    }

}
