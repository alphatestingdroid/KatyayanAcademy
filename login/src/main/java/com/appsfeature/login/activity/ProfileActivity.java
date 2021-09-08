package com.appsfeature.login.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.dialog.CommonSelector;
import com.appsfeature.login.dialog.StateSelector;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.model.StateModel;
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.DatePickerDialog;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginDataUtil;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.appsfeature.login.util.ProfileData;
import com.progressbutton.ProgressButton;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Date;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {

    private EditText etAdmissionNo, etClassSelected, etDisplayName, etEmail, etMobile, etFatherName, etFatherMobile, etMotherName, etDOB,
            etGender, etAddress, etState, etCity, etCountry;
    private EditText etMotherMobile;
    private List<String> mGenderList;
    private ProgressButton btnAction;
    private ImageView ivProfilePic;
    private String imagePath;
    private boolean isFinishWhenCompleteUpdate = false;
    private TextView appVersion;
    private String mDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolBar("Profile");
        setArguments(getIntent());
        initUi();
        loadData();
    }

    private void setArguments(Intent intent) {
        isFinishWhenCompleteUpdate = intent.getBooleanExtra(LoginConstant.CATEGORY_PROPERTY, false);
    }

    private void loadData() {
        mGenderList = ProfileData.getGenderList();
        Profile profile = LoginPrefUtil.getProfileDetail();

        etAdmissionNo.setText(profile.getAdmissionNo());
        if(LoginDataUtil.getInstance(this) != null) {
            if(LoginSDK.getInstance().isAcademyApp()) {
                if(LoginDataUtil.getInstance(this).getSubCategories() != null) {
                    etClassSelected.setText(LoginDataUtil.getInstance(this).getSubCategories().get(profile.getSubCourseId()));
                }
            }else {
                etClassSelected.setText( "Class " + LoginSDK.getInstance().getCourseId());
            }
            etClassSelected.setVisibility(View.VISIBLE);
        }else {
            etClassSelected.setVisibility(View.GONE);
        }
        etDisplayName.setText(profile.getName());
        etEmail.setText(profile.getEmail());
        etMobile.setText(profile.getMobile());
        etFatherName.setText(profile.getFatherName());
        etFatherMobile.setText(profile.getFatherMobile());
        etMotherName.setText(profile.getMotherName());
        etMotherMobile.setText(profile.getMotherMobile());
        this.mDOB = profile.getDateOfBirth();
        etDOB.setText(DatePickerDialog.getViewFormat(profile.getDateOfBirth()));
        etGender.setText(profile.getGender());
        etAddress.setText(profile.getAddress());
        etState.setText(profile.getState());
        etCity.setText(profile.getCity());

        setImage(profile.getProfilePicture());

        appVersion.setText("App Version : " + LoginSDK.getInstance().getVersionName());

        updateEditFields(false, etAdmissionNo, etClassSelected, etDisplayName, etMobile, etCountry);
    }

    private void updateEditFields(boolean isEditable, EditText... editTexts) {
        for (EditText editText : editTexts){
            editText.setEnabled(isEditable);
            editText.setClickable(isEditable);
            editText.setFocusable(isEditable);
            editText.setFocusableInTouchMode(isEditable);
        }
    }

    private void setImage(String imageUri) {
        LoginUtil.loadUserImage(imageUri, ivProfilePic);
    }

    private void initUi() {
        etAdmissionNo = findViewById(R.id.et_customer_field_0);
        etClassSelected = findViewById(R.id.et_customer_field_00);
        etDisplayName = findViewById(R.id.et_customer_field_1);
        etEmail = findViewById(R.id.et_customer_field_2);
        etMobile = findViewById(R.id.et_customer_field_3);
        etFatherName = findViewById(R.id.et_customer_field_4);
        etFatherMobile = findViewById(R.id.et_customer_field_5);
        etMotherName = findViewById(R.id.et_customer_field_5_1);
        etMotherMobile = findViewById(R.id.et_customer_field_5_2);
        etGender = findViewById(R.id.et_customer_field_7);
        etDOB = findViewById(R.id.et_customer_field_8);
        etAddress = findViewById(R.id.et_customer_field_9);
        etState = findViewById(R.id.et_customer_field_10);
        etCity = findViewById(R.id.et_customer_field_11);
        etCountry = findViewById(R.id.et_customer_field_13);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        appVersion = findViewById(R.id.app_version);

        etState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateSelector.newInstance(new StateSelector.SelectListener() {
                    @Override
                    public void onStateSelect(StateModel selectedState) {
                        etState.setText(selectedState.getStateName());
                    }
                }).show(view.getContext());
            }
        });
        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonSelector.newInstance("Select", mGenderList, new CommonSelector.SelectListener() {
                    @Override
                    public void onItemSelect(String item) {
                        etGender.setText(item);
                    }
                }).show(view.getContext());
            }
        });
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.newInstance(ProfileActivity.this, false, new DatePickerDialog.DateSelectListener() {
                    @Override
                    public void onSelectDateClick(Date date, String yyyyMMdd) {
                        mDOB = yyyyMMdd;
                        String dob = DatePickerDialog.getViewFormat(yyyyMMdd);
                        etDOB.setText(dob);
                    }
                }).show();
            }
        });

        btnAction = ProgressButton.newInstance(this)
                .setText("Update")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(LoginSDK.getInstance().isAcademyApp()) {
                            if (!FieldValidation.isEmpty(v.getContext(), etDisplayName)) {
                                return;
                            } else if (!FieldValidation.isEmpty(v.getContext(), etMobile)) {
                                return;
                            }
                        }
//                        else if (!FieldValidation.isEmpty(v.getContext(), etFatherMobile)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etDOB)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etGender)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etAddress)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etState)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etCity)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etZip)) {
//                            return;
//                        }else if (!FieldValidation.isEmpty(v.getContext(), etCountry)) {
//                            return;
//                        }
                        LoginUtil.hideKeybord(ProfileActivity.this);
                        submitTask();
                    }
                });

//        ivProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.startPickImageActivity(ProfileActivity.this);
//            }
//        });
    }


    private void submitTask() {
        String userId = LoginSDK.getInstance().getUserId();
        String name = etDisplayName.getText().toString();
        String mobile = etMobile.getText().toString();
        String fatherName = etFatherName.getText().toString();
        String fatherMobile = etFatherMobile.getText().toString();
        String motherName = etMotherName.getText().toString();
        String motherMobile = etMotherMobile.getText().toString();
        String dob = mDOB;
        String gender = etGender.getText().toString();
        String address = etAddress.getText().toString();
//        String state = etState.getText().toString();
        String city = etCity.getText().toString();
        String country = etCountry.getText().toString();
        String userImage = LoginSDK.getInstance().getUserImage();
        String profilePictureOld = LoginUtil.getFileNameFromUrl(userImage);

        LoginNetwork.getInstance(this).updateUserProfile(name, mobile, fatherName, fatherMobile, motherName, motherMobile
                , dob, gender, address, city, new LoginListener<Profile>() {
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
                        LoginUtil.showToast(ProfileActivity.this, "Profile update successful.");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(isFinishWhenCompleteUpdate){
                                    setResult(RESULT_OK);
                                    ProfileActivity.this.finish();
                                }else {
                                    btnAction.revertProgress();
                                }
                            }
                        }, isFinishWhenCompleteUpdate ? 400 : 2000);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                btnAction.revertProgress();
                LoginUtil.showToast(ProfileActivity.this, e.getMessage());
            }
        });
    }

    private Handler handler = new Handler();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mCropImageUri = result.getUri();
                imagePath = mCropImageUri.getPath();
                setImage(mCropImageUri.toString());
            }
        }
    }

    private Uri mCropImageUri;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                LoginUtil.showToast(this, "Cancelling, required permissions are not granted");
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                LoginUtil.showToast(this, "Cancelling, required permissions are not granted");
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAspectRatio(1,1)
                .start(this);
    }

    private void setUpToolBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(title)) {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
