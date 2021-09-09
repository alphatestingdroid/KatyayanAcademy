package com.appsfeature.login.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName(value="course_id", alternate={"class_id"})
    @Expose
    private int courseId;
    @SerializedName(value="sub_course_id", alternate={"section_id"})
    @Expose
    private int subCourseId;
    @SerializedName("is_active")
    @Expose
    private int isActive;
    @SerializedName("is_premium")
    @Expose
    private int isPremium;
    @SerializedName("admission_no")
    @Expose
    private String admissionNo;
    @SerializedName("student_mobile_no")
    @Expose
    private String mobile;
    @SerializedName("student_name")
    @Expose
    private String name;
    @SerializedName("student_firstname")
    @Expose
    private String firstName;
    @SerializedName("student_middlename")
    @Expose
    private String middleName;
    @SerializedName("student_lastname")
    @Expose
    private String lastName;
    @SerializedName("student_gender")
    @Expose
    private String gender;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("student_email")
    @Expose
    private String email;
    @SerializedName("student_city")
    @Expose
    private String city;
    @SerializedName("student_state")
    @Expose
    private String state;
    @SerializedName("father_mobile")
    @Expose
    private String fatherMobile;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
     @SerializedName("mother_mobile")
    @Expose
    private String motherMobile;
    @SerializedName("student_photo")
    @Expose
    private String student_photo;
    @SerializedName("admission_date")
    @Expose
    private String admission_date;
    @Expose
    @SerializedName(value="date_of_birth", alternate={"student_dob"})
    private String dateOfBirth;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("student_address1")
    @Expose
    private String address;
    @SerializedName("password")
    @Expose
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(int subCourseId) {
        this.subCourseId = subCourseId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        if(!TextUtils.isEmpty(firstName)) {
            return firstName.trim() + " " + lastName;
        }else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFatherMobile() {
        return fatherMobile;
    }

    public void setFatherMobile(String fatherMobile) {
        this.fatherMobile = fatherMobile;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getStudent_photo() {
        return student_photo;
    }

    public void setStudent_photo(String student_photo) {
        this.student_photo = student_photo;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherMobile() {
        return motherMobile;
    }

    public void setMotherMobile(String motherMobile) {
        this.motherMobile = motherMobile;
    }
}
