package com.appsfeature.education.doctor.activity;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseToolbarActivity;
import com.appsfeature.education.util.ClassUtil;


public class DoctorSearchActivity extends BaseToolbarActivity implements TextView.OnEditorActionListener{

    private AutoCompleteTextView tv_location, tv_keyword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);

        onInitializeUI();
        setUpToolBar("Doctor Search");
    }

    public void onInitializeUI() {
        tv_location = findViewById(R.id.tv_location);
        tv_keyword = findViewById(R.id.tv_keyword);
        (findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        tv_location.setOnEditorActionListener(this);
        tv_keyword.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch();
            return true;
        }
        return false;
    }

    private void performSearch() {
//        ClassUtil.openVideoLectureActivity(DoctorSearchActivity.this, tv_location.getText().toString(), tv_keyword.getText().toString());
    }

}
