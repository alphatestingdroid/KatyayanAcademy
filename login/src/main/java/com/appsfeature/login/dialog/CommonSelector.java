package com.appsfeature.login.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.login.R;
import com.appsfeature.login.adapter.CommonSelectorAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class CommonSelector{
    private SelectListener mListener;
    private List<String> mList;
    private String title;
    private BottomSheetDialog mBottomSheetDialog;

    public interface SelectListener {
        void onItemSelect(String item);
    }

    public static CommonSelector newInstance(String title, List<String> mList, SelectListener listener) {
        CommonSelector fragment = new CommonSelector();
        fragment.mListener = listener;
        fragment.title = title;
        fragment.mList = mList;
        return fragment;
    }

    public void show(Context context) {
        if (context != null) {
            mBottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            mBottomSheetDialog.setContentView(viewHolder(inflater.inflate(R.layout.dialog_menu_common, null)));
            mBottomSheetDialog.show();
        }
    }

    private View viewHolder(View v) {
        RecyclerView lvMain = (RecyclerView) v.findViewById(R.id.lv_main);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_search);
        tvTitle.setText(title);
        LinearLayoutManager horizontalManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        lvMain.setLayoutManager(horizontalManager);
        lvMain.setItemAnimator(new DefaultItemAnimator());
        if (mList != null) {
            CommonSelectorAdapter adapter = new CommonSelectorAdapter(v.getContext(), mList, new SelectListener() {
                @Override
                public void onItemSelect(String item) {
                    if (mBottomSheetDialog != null) {
                        mBottomSheetDialog.dismiss();
                    }
                    if (mListener != null) {
                        mListener.onItemSelect(item);
                    }
                    mBottomSheetDialog = null;
                }
            });
            lvMain.setAdapter(adapter);
        }
        return v;
    }
}
