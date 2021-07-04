package com.appsfeature.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.login.R;
import com.appsfeature.login.dialog.CommonSelector;

import java.util.List;

public class CommonSelectorAdapter extends RecyclerView.Adapter<CommonSelectorAdapter.ReyclerViewHolder> {

    private final CommonSelector.SelectListener callback;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> items;

    public CommonSelectorAdapter(Context context, List<String> items, CommonSelector.SelectListener callback) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.callback = callback;
        this.items = items;
    }

    @NonNull
    @Override
    public ReyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReyclerViewHolder(layoutInflater.inflate(R.layout.slot_empty, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReyclerViewHolder holder, final int position) {
        holder.tvName.setText(items.get(position));
        holder.llSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemSelect(items.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llSlot;
        private TextView tvName;

        private ReyclerViewHolder(final View v) {
            super(v);
            llSlot = (LinearLayout) v.findViewById(R.id.layoutSlot);
            tvName = (TextView) v.findViewById(R.id.tv_message);
        }
    }
}

