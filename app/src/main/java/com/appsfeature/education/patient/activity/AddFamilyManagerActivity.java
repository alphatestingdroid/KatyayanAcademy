package com.appsfeature.education.patient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.SupportUtil;
import com.appsfeature.login.dialog.CommonSelector;
import com.appsfeature.login.util.DatePickerDialog;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.appsfeature.login.util.ProfileData;
import com.helper.callback.Response;
import com.progressbutton.ProgressButton;

import java.util.Date;

public class AddFamilyManagerActivity extends BaseActivity implements View.OnClickListener {


    private EditText etName, etGender, etRelation, etDOB;
    private ProgressButton btnAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        initUi();
        setUpToolBar("New Member Detail");
    }

    private void initUi() {
        etName = findViewById(R.id.et_customer_field_1);
        etGender = findViewById(R.id.et_customer_field_2);
        etRelation = findViewById(R.id.et_customer_field_3);
        etDOB = findViewById(R.id.et_customer_field_4);

        etGender.setOnClickListener(this);
        etRelation.setOnClickListener(this);
        etDOB.setOnClickListener(this);
        btnAction = ProgressButton.newInstance(this)
                .setText("Add")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(v.getContext(), etName)) {
                            return;
                        } else if (!FieldValidation.isEmpty(v.getContext(), etGender)) {
                            return;
                        } else if (!FieldValidation.isEmpty(v.getContext(), etRelation)) {
                            return;
                        } else if (!FieldValidation.isEmpty(v.getContext(), etDOB)) {
                            return;
                        }
                        LoginUtil.hideKeybord(AddFamilyManagerActivity.this);
                        updateMemberTask();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.et_customer_field_2) {
            CommonSelector.newInstance("Select", ProfileData.getGenderList(), new CommonSelector.SelectListener() {
                @Override
                public void onItemSelect(String item) {
                    etGender.setText(item);
                }
            }).show(this);
        }else if(v.getId() == R.id.et_customer_field_3) {
            CommonSelector.newInstance("Select", ProfileData.getRelationList(), new CommonSelector.SelectListener() {
                @Override
                public void onItemSelect(String item) {
                    etRelation.setText(item);
                }
            }).show(this);
        }else if(v.getId() == R.id.et_customer_field_4) {
            DatePickerDialog.newInstance(this, false, new DatePickerDialog.DateSelectListener() {
                @Override
                public void onSelectDateClick(Date date, String yyyyMMdd) {
                    String dob = DatePickerDialog.getDOBFormat(yyyyMMdd);
                    etDOB.setText(dob);
                }
            }).show();
        }
    }

    private void updateMemberTask() {
        String userId = AppApplication.getInstance().getLoginSDK().getUserId();
        String name = etName.getText().toString();
        String gender = etGender.getText().toString();
        String relation = etRelation.getText().toString();
        String dob = etDOB.getText().toString();

        if (getExtraProperty() != null) {
            appPresenter.addNewFamilyMember(userId, name, gender, relation
                    , dob, new Response.Callback<PatientModel>() {
                        @Override
                        public void onSuccess(PatientModel response) {
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    addingSuccessful(response);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            onErrorOccurred(e);
                        }
                    });
        }
    }

    private void addingSuccessful(PatientModel response) {
        SupportUtil.showToast(this, "Family member added successfully");
        finish();
    }


    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {
        SupportUtil.showToast(this, e.getMessage());
    }

    @Override
    public void onStartProgressBar() {
        btnAction.startProgress();
    }

    @Override
    public void onStopProgressBar() {
        btnAction.revertProgress();
    }

}
