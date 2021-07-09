package com.appsfeature.education.entity;

import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.patient.PatientModel;

import java.util.List;


public class PresenterModel {

    private List<EducationModel> educationList = null;
    private List<SlotModel> slotModel = null;
    private List<AppointmentModel> appointmentModel = null;
    private PatientModel patientModel = null;

    public List<EducationModel> getEducationList() {
        return educationList;
    }

    public PresenterModel setEducationList(List<EducationModel> modelList) {
        this.educationList = modelList;
        return this;
    }

    public List<SlotModel> getSlotModel() {
        return slotModel;
    }

    public PresenterModel setSlotModel(List<SlotModel> slotModel) {
        this.slotModel = slotModel;
        return this;
    }

    public PatientModel getPatientModel() {
        return patientModel;
    }

    public PresenterModel setPatientModel(PatientModel patientModel) {
        this.patientModel = patientModel;
        return this;
    }

    public List<AppointmentModel> getAppointmentModel() {
        return appointmentModel;
    }

    public PresenterModel setAppointmentModel(List<AppointmentModel> appointmentModel) {
        this.appointmentModel = appointmentModel;
        return this;
    }
}
