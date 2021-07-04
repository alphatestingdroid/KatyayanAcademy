package com.appsfeature.education.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.education.model.SkillModel;

import java.util.Arrays;
import java.util.List;

public class DoctorSkillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SkillModel> mList;

    public DoctorSkillAdapter(List<SkillModel> mList) {
        this.mList = mList;
    }


    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case ItemType.TYPE_EDUCATION:
            case ItemType.TYPE_AWARD:
            case ItemType.TYPE_WORK_EXPERIENCE:
            case ItemType.TYPE_SERVICES:
                return new ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_skill_list, viewGroup, false));
            case ItemType.TYPE_SPECIALIZATIONS:
                return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_skill_arrow, viewGroup, false));
            case ItemType.TYPE_TITLE:
            default:
                return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_skill_title, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SkillModel item = mList.get(position);
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder viewHolder = (TitleViewHolder) holder;
            viewHolder.tvTitle.setText(item.getTitle());
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder viewHolder = (ListViewHolder) holder;
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            if(item.getItemType() == ItemType.TYPE_EDUCATION) {
                viewHolder.recyclerView.setAdapter(new DoctorSkillEducationAdapter(item.getEducation()));
            }else if(item.getItemType() == ItemType.TYPE_AWARD) {
                viewHolder.recyclerView.setAdapter(new DoctorSkillAwardAdapter(item.getAward()));
            }else if(item.getItemType() == ItemType.TYPE_WORK_EXPERIENCE) {
                viewHolder.recyclerView.setAdapter(new DoctorSkillWorkExperienceAdapter(item.getWorkExperience()));
            }else if(item.getItemType() == ItemType.TYPE_SERVICES) {
                viewHolder.recyclerView.setAdapter(new DoctorSkillServicesAdapter(getServiceList(item.getTitle())));
            }
        }
    }

    private List<String> getServiceList(String title) {
        return Arrays.asList(title.split(","));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        TitleViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView recyclerView;

        ListViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.recycler_view);
        }
    }
}
