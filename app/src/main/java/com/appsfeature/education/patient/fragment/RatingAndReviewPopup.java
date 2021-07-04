package com.appsfeature.education.patient.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appsfeature.education.R;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.network.NetworkManager;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.SupportUtil;
import com.appsfeature.login.util.LoginPrefUtil;
import com.squareup.picasso.Picasso;


public class RatingAndReviewPopup {
    private AlertDialog dialog;
    private AppointmentModel mItem;

    public static RatingAndReviewPopup newInstance(AppointmentModel mItem) {
        RatingAndReviewPopup fragment = new RatingAndReviewPopup();
        fragment.mItem = mItem;
        return fragment;
    }

    public void show(Context context) {
        if (context != null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, com.helper.R.style.DialogTheme);
            LayoutInflater inflater = LayoutInflater.from(context);
            dialogBuilder.setView(viewHolder(inflater.inflate(R.layout.dialog_rating_and_review, null)));
            dialogBuilder.setCancelable(false);
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    private View viewHolder(View view) {
        ImageView imgProfile = view.findViewById(R.id.img_profile);
        TextView name = view.findViewById(R.id.name);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        EditText review = view.findViewById(R.id.review);
        Button negative = view.findViewById(R.id.btn_negative);
        Button positive = view.findViewById(R.id.btn_positive);
        final RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        if (mItem != null) {
            if (mItem.getDoctorDetails().size() > 0) {
                DoctorModel doctorModel = mItem.getDoctorDetails().get(0);
                if (doctorModel != null) {
                    name.setText(doctorModel.getName());
                    if (!TextUtils.isEmpty(doctorModel.getProfilePicture())) {
                        Picasso.get().load(doctorModel.getProfilePicture())
                                .placeholder(R.drawable.ic_user_profile)
                                .error(R.drawable.ic_user_profile)
                                .into(imgProfile);
                    } else {
                        imgProfile.setImageResource(R.drawable.ic_user_profile);
                    }
                }
            }
        }
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating("1", "");
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() > 0) {
                    submitRating(ratingBar.getRating() + "", review.getText().toString());
                    SupportUtil.showToast(view.getContext(), AppConstant.RATING_TOAST_SUBMIT_MESSAGE);
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                } else {
                    SupportUtil.showToast(view.getContext(), AppConstant.RATING_TOAST_ERROR_MESSAGE);
                }
            }
        });
        return view;
    }

    private void submitRating(String rating, String review) {
        if (mItem != null) {
            String userId = LoginPrefUtil.getUserId();
            NetworkManager.submitReview(userId, mItem.getId(), rating, review);
        }
    }
}
