package com.appsfeature.education.doctor.activity;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.util.DataUtil;
import com.appsfeature.education.util.ScreenCapture;
import com.appsfeature.education.util.SupportUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;


public class InvoiceViewActivity extends BaseActivity {

    private DoctorModel doctorModel;
    private AppointmentModel appointmentModel;
    private NestedScrollView invoiceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);

        appointmentModel = getExtraProperty().getAppointmentModel();
        if (appointmentModel.getDoctorDetails().size() > 0) {
            doctorModel = appointmentModel.getDoctorDetails().get(0);
        }
        initUi();
    }

    private void initUi() {
        invoiceView = findViewById(R.id.invoice_view);
        TextView invOrder = findViewById(R.id.inv_order);
        TextView invIssued = findViewById(R.id.inv_issued);
        TextView invFrom = findViewById(R.id.inv_from);
        TextView invTo = findViewById(R.id.inv_to);
        TextView invPatientDetail = findViewById(R.id.inv_patient_detail);
        TextView invDesc = findViewById(R.id.inv_desc);
        TextView invQuantity = findViewById(R.id.inv_quantity);
        TextView invFee = findViewById(R.id.inv_fee);
        TextView invTotal = findViewById(R.id.inv_total);
        TextView invNote = findViewById(R.id.inv_note);
        (findViewById(R.id.btn_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice(false);
            }
        });
        (findViewById(R.id.btn_print)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice(true);
            }
        });

        invOrder.setText(SupportUtil.generateBoldTitle("Order: ", "#INV" + getInvOrder(appointmentModel.getId())));
        invIssued.setText(SupportUtil.generateBoldTitle("Issued: ", appointmentModel.getCreatedAt()));
        invFrom.setText(DataUtil.getDoctorAddress(doctorModel));
        invTo.setText(DataUtil.getPatientAddress(appointmentModel.getPatientName()));
        invPatientDetail.setText(DataUtil.getPatientDetail(appointmentModel));
//        invDesc.setText(appointmentModel);
//        invQuantity.setText(appointmentModel);
        String doctorFees = appointmentModel.getDoctorFee();
        invFee.setText(doctorFees);
        invTotal.setText(doctorFees);
//        invNote.setText(appointmentModel);

    }

    private String getInvOrder(String base64) {
        String mId = SupportUtil.decodeBase64(base64);
        return SupportUtil.decodeBase64(mId);
    }


    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }


    private void printInvoice(boolean isPrint) {
        TedPermission.with(InvoiceViewActivity.this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        ScreenCapture.takeScreenShot(InvoiceViewActivity.this, invoiceView, isPrint);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

}
