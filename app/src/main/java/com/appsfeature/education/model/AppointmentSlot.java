package com.appsfeature.education.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentSlot {

    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("booked_status")
    @Expose
    private String bookedStatus;
    @SerializedName("lapsed_status")
    @Expose
    private String lapsedStatus;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBookedStatus() {
        return bookedStatus;
    }

    public void setBookedStatus(String bookedStatus) {
        this.bookedStatus = bookedStatus;
    }

    public String getLapsedStatus() {
        return lapsedStatus;
    }

    public void setLapsedStatus(String lapsedStatus) {
        this.lapsedStatus = lapsedStatus;
    }

}
