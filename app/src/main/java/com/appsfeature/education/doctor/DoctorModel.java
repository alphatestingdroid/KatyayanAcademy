package com.appsfeature.education.doctor;

import androidx.annotation.NonNull;

import com.appsfeature.education.entity.Award;
import com.appsfeature.education.entity.Education;
import com.appsfeature.education.entity.WorkExperience;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DoctorModel implements Serializable , Cloneable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("clinic_address")
    @Expose
    private String clinicAddress;
    @SerializedName("clinic_city")
    @Expose
    private String clinicCity;
    @SerializedName("clinic_state")
    @Expose
    private String clinicState;
    @SerializedName("clinic_country")
    @Expose
    private String clinicCountry;
    @SerializedName("clinic_pincode")
    @Expose
    private String clinicPincode;
    @SerializedName("clinic_fee")
    @Expose
    private String clinicFee;
    @SerializedName("old_clinic_fee")
    @Expose
    private String oldClinicFee;
    @SerializedName("clinic_services")
    @Expose
    private String clinicServices;
    @SerializedName("about_us")
    @Expose
    private String aboutUs;
    @SerializedName("clinic_specialist")
    @Expose
    private String clinicSpecialist;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_open_time")
    @Expose
    private String clinicOpenTime;
    @SerializedName("clinic_close_time")
    @Expose
    private String clinicCloseTime;
    @SerializedName("education")
    @Expose
    private List<Education> education = null;
    @SerializedName("award")
    @Expose
    private List<Award> award = null;
    @SerializedName("work_experience")
    @Expose
    private List<WorkExperience> workExperience = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicCity() {
        return clinicCity;
    }

    public void setClinicCity(String clinicCity) {
        this.clinicCity = clinicCity;
    }

    public String getClinicState() {
        return clinicState;
    }

    public void setClinicState(String clinicState) {
        this.clinicState = clinicState;
    }

    public String getClinicCountry() {
        return clinicCountry;
    }

    public void setClinicCountry(String clinicCountry) {
        this.clinicCountry = clinicCountry;
    }

    public String getClinicPincode() {
        return clinicPincode;
    }

    public void setClinicPincode(String clinicPincode) {
        this.clinicPincode = clinicPincode;
    }

    public String getClinicFee() {
        return clinicFee;
    }

    public void setClinicFee(String clinicFee) {
        this.clinicFee = clinicFee;
    }

    public String getClinicServices() {
        return clinicServices;
    }

    public void setClinicServices(String clinicServices) {
        this.clinicServices = clinicServices;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getClinicSpecialist() {
        return clinicSpecialist;
    }

    public void setClinicSpecialist(String clinicSpecialist) {
        this.clinicSpecialist = clinicSpecialist;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicOpenTime() {
        return clinicOpenTime;
    }

    public void setClinicOpenTime(String clinicOpenTime) {
        this.clinicOpenTime = clinicOpenTime;
    }

    public String getClinicCloseTime() {
        return clinicCloseTime;
    }

    public void setClinicCloseTime(String clinicCloseTime) {
        this.clinicCloseTime = clinicCloseTime;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<Award> getAward() {
        return award;
    }

    public void setAward(List<Award> award) {
        this.award = award;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public String getOldClinicFee() {
        return oldClinicFee;
    }

    public void setOldClinicFee(String oldClinicFee) {
        this.oldClinicFee = oldClinicFee;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public DoctorModel getClone() {
        try {
            return (DoctorModel) clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
