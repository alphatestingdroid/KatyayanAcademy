package com.appsfeature.education.network;

public interface ApiEndPoint {
    String SEARCH_DOCTOR = "search_doctor";
    String DOCTOR_PROFILE_VIEW = "doctor_profile_view";
    String DOCTOR_SLOT_VIEW = "doctor_slot_view";
    String PATIENT_PROFILE_VIEW = "patient_profile_view";
    String PATIENT_APPOINTMENT_SUBMIT = "patient_appointment_submit";
    String PATIENT_FAMILY_UPDATE = "patient_family_update";
    String PATIENT_FAMILY_REMOVE = "patient_family_remove";
    String PATIENT_BOOKING_HISTORY = "patient_booking_history";
    String PATIENT_APPOINTMENT_DETAILS = "patient_appointment_details";
    String PATIENT_APPOINTMENT_CANCEL = "patient_appointment_cancel";
    String PATIENT_APPOINTMENT_RATING = "patient_appointment_rating";
    String GET_LIVE_CLASS = "getLiveclass";
    String GET_OFFLINE_CLASS = "getOfflineclass";
}
