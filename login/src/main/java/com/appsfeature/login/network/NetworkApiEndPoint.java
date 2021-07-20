package com.appsfeature.login.network;

public interface NetworkApiEndPoint {
    String TERM_CONDITION = "https://aasanilaz.com/term_condition";
    String PRIVACY_POLICY = "https://aasanilaz.com/privacy_policy";

    String EMAIL_CHANGE_PASSWORD = "email-change-password";
    String EMAIL_FORGOT_PASS = "email-forgot-pass";
    String EMAIL_VALIDATE_OTP = "email-validate-otp";
    String EMAIL_LOGIN_USER = "email-login";
    String EMAIL_SIGN_UP = "email-sign-up";

    String MOBILE_CHANGE_PASSWORD = "patient_change_password";
    String MOBILE_FORGOT_PASS = "patient_forget_password";
    String MOBILE_OTP_GENERATE = "patient_otp_generate";
    String MOBILE_VALIDATE_OTP = "patient_otp_verify";
    String PATIENT_PROFILE_UPDATE = "patient_profile_update";

    String MOBILE_LOGIN_USER = "login_ap";
    String MOBILE_SIGN_UP = "saveUserData";
    String EDIT_PROFILE_DATA = "editProfileData";
}
