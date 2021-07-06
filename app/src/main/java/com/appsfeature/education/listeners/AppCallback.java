package com.appsfeature.education.listeners;

import com.appsfeature.education.entity.PresenterModel;
import com.helper.callback.Response;

public class AppCallback {
    public interface View extends Response.Progress {
        void onUpdateUI(PresenterModel response);
        void onErrorOccurred(Exception e);
    }

    public interface OnClickListener<T> {
        void onItemClicked(android.view.View view, T item);
        void onItemDeleteClicked(android.view.View view, T item);
    }

    public interface OnAppointmentClickListener<T> {
        void onItemClicked(android.view.View view, T item);
        void onItemCancelClicked(android.view.View view, T item);
        void onItemReBookingClicked(android.view.View view, T item);
    }
}
