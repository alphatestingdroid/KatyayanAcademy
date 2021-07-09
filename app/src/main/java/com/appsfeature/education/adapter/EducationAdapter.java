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
import com.appsfeature.education.listeners.ListItemType;
import com.appsfeature.education.model.EducationModel;
import com.helper.callback.Response;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnClickListener<EducationModel> clickListener;
    private final Activity activity;
    private final int viewType;
    private List<EducationModel> mList;

    public EducationAdapter(Activity activity, int viewType, List<EducationModel> mList, Response.OnClickListener<EducationModel> clickListener) {
        this.activity = activity;
        this.mList = mList;
        this.viewType = viewType;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return viewType;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ListItemType.TYPE_CHAPTER) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_chapter_item, parent, false));
        }else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_subject_item, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        if(viewType == ListItemType.TYPE_CHAPTER) {
            myViewHolder.tvTitle.setText( (position + 1) + " - " + mList.get(position).getChapterName());
            myViewHolder.bottomLine.setBackgroundColor(Color.parseColor(getRandomColor(position)));
        }else {
            if(mList.get(position).getSubjectName().length() > 0) {
                myViewHolder.tvTitleTag.setText(mList.get(position).getSubjectName().substring(0, 1));
            }
            myViewHolder.tvTitle.setText(mList.get(position).getSubjectName());

            myViewHolder.bottomLine.setBackgroundColor(Color.parseColor(getRandomColor(position)));
            if (myViewHolder.tvTitleTag != null) {
                int mColor = Color.parseColor(getRandomColor(position));
                setColorFilter(myViewHolder.tvTitleTag.getBackground(), mColor);
            }
        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View bottomLine;
        private final TextView tvTitle, tvTitleTag;

        private ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvTitleTag = v.findViewById(R.id.tv_title_tag);
            bottomLine = v.findViewById(R.id.bottom_line);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
            }
        }
    }

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
