package com.appsfeature.login.fragment.mobile;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.appsfeature.login.R;
import com.appsfeature.login.activity.ProfileActivity;
import com.appsfeature.login.fragment.BaseFragment;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.network.NetworkApiEndPoint;
import com.appsfeature.login.util.AppData;
import com.appsfeature.login.util.DatePickerDialog;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.helper.util.BaseUtil;
import com.progressbutton.ProgressButton;

import java.util.Date;

/**
 * Created by Admin on 5/22/2017.
 */
public class MobileSignUp extends BaseFragment {

    private EditText etName, etMobile, etDOB, etPinCode, etCollegeCode;
    private LinearLayout llTearms, llLogin;


    private Listener mListener;
    private ProgressButton btnAction;
    private Spinner spGender, spCourseId, spSubCourseId;
    private String mGender = "";
    private int mCourseId, mSubCourseId;
    private Activity activity;

    public interface Listener {
        void addLoginOption();

        void onSignUpCompleted(Profile profile);

        void onVerifyAndSignUp(String name, String mobile, String password);
    }

    public static MobileSignUp newInstance(Listener mListener) {
        MobileSignUp fragment = new MobileSignUp();
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_signup_mobile, container, false);
        activity = getActivity();
        initToolBarTheme(getActivity(), v, "Registration");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etName = (EditText) v.findViewById(R.id.et_company_name);
        etMobile = (EditText) v.findViewById(R.id.et_company_mobile);
        etDOB = (EditText) v.findViewById(R.id.et_company_dob);
        etPinCode = (EditText) v.findViewById(R.id.et_company_pin_code);
        etCollegeCode = (EditText) v.findViewById(R.id.et_company_college_code);

        spGender = (Spinner) v.findViewById(R.id.sp_company_gender);
        spCourseId = (Spinner) v.findViewById(R.id.sp_company_course_id);
        spSubCourseId = (Spinner) v.findViewById(R.id.sp_company_sub_course_id);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        spCourseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCourseId = AppData.catIds[position];
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        spSubCourseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSubCourseId = AppData.subCatIds[position];
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        llTearms = (LinearLayout) v.findViewById(R.id.ll_tearm_user);
        llLogin = (LinearLayout) v.findViewById(R.id.ll_login);

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.newInstance(activity, false, new DatePickerDialog.DateSelectListener() {
                    @Override
                    public void onSelectDateClick(Date date, String yyyyMMdd) {
                        String dob = DatePickerDialog.getViewFormat(yyyyMMdd);
                        etDOB.setText(dob);
                    }
                }).show();
            }
        });

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("SignUp")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etName)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etMobile)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etDOB)) {
                            return;
                        } else if (mGender.equalsIgnoreCase("select gender")) {
                            LoginUtil.showToast(getContext(), "Please select gender");
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etPinCode)) {
                            return;
                        } else if (mCourseId == 0) {
                            LoginUtil.showToast(getContext(), "Please select course");
                            return;
                        } else if (mSubCourseId == 0) {
                            LoginUtil.showToast(getContext(), "Please select sub course");
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });


        llTearms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.openLinkInAppBrowser(getActivity(), "Term and Conditions", NetworkApiEndPoint.TERM_CONDITION);
            }
        });

        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addLoginOption();
            }
        });

        btnAction.setOnEditorActionListener(etDOB, "Signup");
    }


    private void executeTask() {
        final String name = etName.getText().toString();
        final String mobile = etMobile.getText().toString();
        final String dob = etDOB.getText().toString();
        final String pinCode = etPinCode.getText().toString();
        final String collegeCode = etCollegeCode.getText().toString();
        LoginNetwork.getInstance(getContext())
                .signUp(name, mobile, dob, mGender, pinCode, mCourseId, mSubCourseId, collegeCode, new LoginListener<Profile>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Profile response) {
                        LoginPrefUtil.setProfile(response);
                        btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                            @Override
                            public void onAnimationCompleted() {
                                mListener.onSignUpCompleted(response);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        btnAction.revertProgress();
                        LoginUtil.showToast(getActivity(), e.getMessage());
                    }
                });
//        LoginNetwork.getInstance(getContext())
//                .generateOTP(mobile, new LoginListener<Boolean>() {
//                    @Override
//                    public void onPreExecute() {
//                        btnAction.startProgress();
//                    }
//
//                    @Override
//                    public void onSuccess(Boolean response) {
//                        if(response) {
//                            LoginUtil.showToast(getActivity(), "otp send successfully");
//                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
//                                @Override
//                                public void onAnimationCompleted() {
//                                    mListener.onVerifyAndSignUp(name, mobile, password);
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        btnAction.revertProgress();
//                        LoginUtil.showToast(getActivity(), e.getMessage());
//                    }
//                });

    }


}