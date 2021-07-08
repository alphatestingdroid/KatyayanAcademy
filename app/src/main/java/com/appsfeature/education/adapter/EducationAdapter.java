package com.appsfeature.education.adapter;


import android.app.Activity;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import java.util.Random;

public class EducationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public EducationAdapter(Activity activity, List<EducationModel> mList, Response.OnClickListener<EducationModel> clickListener) {
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
        myViewHolder.tvLocation.setText(SupportUtil.getDateFormatted(mList.get(i).getLiveClassDate(), mList.get(i).getLiveClassTime()));

        myViewHolder.tvLocation.setBackgroundColor(Color.parseColor(colors[getRandomNum()]));

        int mColor = Color.parseColor(getRandomColor(i));
        setColorFilter(myViewHolder.tvLocation.getBackground(), mColor);

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
        private TextView tvLocation;

        private ViewHolder(View v) {
            super(v);
            ivPic = v.findViewById(R.id.pic);
            tvName = v.findViewById(R.id.name);
            tvDepartment = v.findViewById(R.id.department);
            tvLocation = v.findViewById(R.id.location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
            }
        }
    }

    private int getRandomNum() {
        Random r = new Random();
        return r.nextInt(7);
    }

    private String[] colors = {
            "#13c4a5", "#10a4b8", "#8a63b3", "#3b5295", "#fdbd57", "#f6624e", "#e7486b", "#9c4274"};


    @SuppressWarnings("deprecation")
    public static void setColorFilter(@NonNull Drawable drawable, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private String getRandomColor(int i) {
        if(i % 9 == 0) {
            return "#7E17E0";
        } else if(i == 1 || (i-1) % 9 == 0) {
            return "#FF668F";
        } else if(i == 2 || (i-2) % 9 == 0) {
            return "#FD5E41";
        } else if(i == 3 || (i-3) % 9 == 0) {
            return "#02D676";
        } else if(i == 4 || (i-4) % 9 == 0) {
            return "#FFBA46";
        } else if(i == 5 || (i-5) % 9 == 0) {
            return "#FF5E42";
        } else if(i == 6 || (i-6) % 9 == 0) {
            return "#FEBB46";
        } else if(i == 7 || (i-7) % 9 == 0) {
            return "#097166";
        } else if(i == 8 || (i-8) % 9 == 0) {
            return "#AD40FE";
        } else {
            return "#7E17E0";
        }
    }
}
