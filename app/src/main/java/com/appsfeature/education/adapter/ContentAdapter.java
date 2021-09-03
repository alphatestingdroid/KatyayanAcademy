package com.appsfeature.education.adapter;


import android.app.Activity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.listeners.ContentType;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.player.util.YTUtility;
import com.helper.callback.Response;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnClickListener<EducationModel> clickListener;
    private final Activity activity;
    private final int contentType;
    private final List<EducationModel> mList;

    @Override
    public int getItemViewType(int position) {
        return contentType;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ContentType.TYPE_VIDEO) {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.slot_content_video, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.slot_content_pdf, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public ContentAdapter(Activity activity, int contentType, List<EducationModel> mList, Response.OnClickListener<EducationModel> clickListener) {
        this.activity = activity;
        this.contentType = contentType;
        this.mList = mList;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvName.setText(mList.get(i).getLectureName());
            viewHolder.tvDepartment.setText(mList.get(i).getSubjectName());
            String dateTime = getTimeSpanString(mList.get(i).getLiveClassDate(), mList.get(i).getLiveClassTime());
            if (!TextUtils.isEmpty(dateTime)) {
                viewHolder.tvDate.setText(dateTime);
                viewHolder.tvDate.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvDate.setVisibility(View.GONE);
            }
        } else if (holder instanceof VideoViewHolder) {
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.tvName.setText(mList.get(i).getLectureName());
            viewHolder.tvDepartment.setText(mList.get(i).getSubjectName());
//            if (mList.get(i).isRead() && mList.get(i).getVideoTime() > 0 && !TextUtils.isEmpty(mList.get(i).getVideoTimeFormatted())) {
//                viewHolder.tvWatchTime.setText("Watched: " + mList.get(i).getVideoTimeFormatted());
//                viewHolder.tvWatchTime.setVisibility(View.VISIBLE);
//            }else {
                viewHolder.tvWatchTime.setVisibility(View.GONE);
//            }
            String dateTime = getTimeSpanString(mList.get(i).getLiveClassDate(), mList.get(i).getLiveClassTime());
            if (!TextUtils.isEmpty(dateTime)) {
                viewHolder.tvDate.setText(dateTime);
                viewHolder.tvDate.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvDate.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(mList.get(i).getLectureVideo())) {
                String videoPreviewUrl = getYoutubePlaceholderImage(YTUtility.getVideoIdFromUrl(mList.get(i).getLectureVideo()));
                Picasso.get().load(videoPreviewUrl)
                        .placeholder(R.drawable.ic_yt_placeholder)
                        .error(R.drawable.ic_yt_placeholder)
                        .into(viewHolder.ivPic);
                viewHolder.ivPic.setVisibility(View.VISIBLE);
            }else {
                viewHolder.ivPic.setVisibility(View.GONE);
            }
            viewHolder.cardView.setCardBackgroundColor(ContextCompat.getColor(activity, mList.get(i).isRead() ? R.color.yt_color_video_watched : R.color.themeBackgroundCardColor));

            if(mList.get(i).getVideoDuration() > 0) {
                viewHolder.progressBar.setMax(mList.get(i).getVideoDuration());
                viewHolder.progressBar.setProgress(mList.get(i).getVideoTime());
                viewHolder.progressBar.setVisibility(View.VISIBLE);
            }else {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
        }

    }

    private String getYoutubePlaceholderImage(String videoId) {
        return "https://i.ytimg.com/vi/"+ videoId +"/mqdefault.jpg";
    }


    private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivPic;
        private final TextView tvName;
        private final TextView tvDepartment;
        private final TextView tvDate;
        private final TextView tvWatchTime;
        private final CardView cardView;
        private final ProgressBar progressBar;

        private VideoViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.card_view);
            ivPic = v.findViewById(R.id.pic);
            tvName = v.findViewById(R.id.name);
            tvDepartment = v.findViewById(R.id.department);
            tvDate = v.findViewById(R.id.location);
            tvWatchTime = v.findViewById(R.id.watch_time);
            progressBar = itemView.findViewById(R.id.progress_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
            }
        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvName;
        private final TextView tvDepartment;
        private final TextView tvDate;

        private ViewHolder(View v) {
            super(v);
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

    public String getTimeSpanString(String inputDate, String time){
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_TIME;
        try {
            String serverDateFormat = inputDate + " " + time;
            Date mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(serverDateFormat);
            if(mDate != null) {
                long timeInMilliseconds = mDate.getTime();
                return DateUtils.getRelativeTimeSpanString(timeInMilliseconds, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flags).toString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
