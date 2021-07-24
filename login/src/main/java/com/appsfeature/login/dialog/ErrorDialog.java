package com.appsfeature.login.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsfeature.login.R;
import com.appsfeature.login.util.LoginConstant;


/**
 * @author Created by Abhijit Rao on 04/12/18.
 */
public class ErrorDialog {
    private TextView tvDescription;
    private Dialog dialog;
    private String mTitle;
    private String mMessage;
    private DialogListener mListener;
    private int task;
    private String errorMessage;
    private Activity activity;

    public interface DialogListener {
        void onPositiveClick();
    }

    public static ErrorDialog newInstance(Activity activity, String errorMessage) {
        ErrorDialog mInstance = new ErrorDialog();
        mInstance.activity = activity;
        mInstance.errorMessage = errorMessage;
        return mInstance;
    }

    public static ErrorDialog newInstance(Activity activity,String title, String message, DialogListener listener) {
        ErrorDialog mInstance = new ErrorDialog();
        mInstance.activity = activity;
        mInstance.mTitle = title;
        mInstance.mMessage = message;
        mInstance.mListener = listener;
        return mInstance;
    }

    public void show() {
        if ( activity != null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = LayoutInflater.from(activity);
            dialogBuilder.setView(viewHolder(inflater.inflate(R.layout.dialog_error, null)));
            dialogBuilder.setCancelable(true);
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    private View viewHolder(View view) {
        Button button1 =  view.findViewById(R.id.btn_alert_dialog_button1);
        TextView tvTitle =  view.findViewById(R.id.tv_alert_dialog_title);
        tvDescription =  view.findViewById(R.id.tv_alert_dialog_detail);

        if (mMessage == null) {
            mTitle = LoginConstant.SERVER_TIME_OUT_TAG;
            mMessage = LoginConstant.SERVER_TIME_OUT_MSG;
        }
        button1.setText("Close");

        tvTitle.setText(mTitle);
        tvDescription.setText(mMessage);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (errorMessage != null) {
                    tvDescription.setText(errorMessage);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                if (mListener != null) {
                    mListener.onPositiveClick();
                }
            }
        });
        return view;
    }
}
