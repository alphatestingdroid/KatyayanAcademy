package com.appsfeature.education.entity;

import java.util.List;

public class SkillModel {

    private String title;

    private int itemType;

    private List<Education> education = null;

    private List<Award> award = null;

    private List<WorkExperience> workExperience = null;

    public SkillModel() {
    }

    public SkillModel(int itemType) {
        this.itemType = itemType;
    }

    public SkillModel(String title, int itemType) {
        this.title = title;
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<Education> getEducation() {
        return education;
    }

    public SkillModel setEducation(List<Education> education) {
        this.education = education;
        return this;
    }

    public List<Award> getAward() {
        return award;
    }

    public SkillModel setAward(List<Award> award) {
        this.award = award;
        return this;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public SkillModel setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
        return this;
    }
}
