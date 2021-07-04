package com.appsfeature.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 7/28/2017.
 */

public class StateModel {

    @Expose
    @SerializedName(value="id")
    private String id;
    @Expose
    @SerializedName(value="StateName")
    private String stateName;
    @Expose
    @SerializedName(value="StateInitial")
    private String stateInitial;
    @Expose
    @SerializedName(value="StateCode")
    private String stateCode;
    @Expose
    @SerializedName(value="StateType")
    private String stateType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateInitial() {
        return stateInitial;
    }

    public void setStateInitial(String stateInitial) {
        this.stateInitial = stateInitial;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }
}
