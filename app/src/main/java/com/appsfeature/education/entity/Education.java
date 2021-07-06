package com.appsfeature.education.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Education implements Serializable {

    @SerializedName("institute")
    @Expose
    private String institute;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("completion_year")
    @Expose
    private String completionYear;

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompletionYear() {
        return completionYear;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }

}
