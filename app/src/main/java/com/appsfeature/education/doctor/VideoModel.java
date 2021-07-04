package com.appsfeature.education.doctor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lecture_name")
    @Expose
    private String lectureName;
    @SerializedName("lecture_description")
    @Expose
    private String lectureDescription;
    @SerializedName("lecture_by")
    @Expose
    private String lectureBy;
    @SerializedName("lecture_video")
    @Expose
    private String lectureVideo;
    @SerializedName("lecture_notes")
    @Expose
    private String lectureNotes;
    @SerializedName("lecture_worksheet")
    @Expose
    private String lectureWorksheet;
    @SerializedName("lecture_type")
    @Expose
    private String lectureType;
    @SerializedName("live_class_date")
    @Expose
    private String liveClassDate;
    @SerializedName("live_class_time")
    @Expose
    private String liveClassTime;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureDescription() {
        return lectureDescription;
    }

    public void setLectureDescription(String lectureDescription) {
        this.lectureDescription = lectureDescription;
    }

    public String getLectureBy() {
        return lectureBy;
    }

    public void setLectureBy(String lectureBy) {
        this.lectureBy = lectureBy;
    }

    public String getLectureVideo() {
        return lectureVideo;
    }

    public void setLectureVideo(String lectureVideo) {
        this.lectureVideo = lectureVideo;
    }

    public String getLectureNotes() {
        return lectureNotes;
    }

    public void setLectureNotes(String lectureNotes) {
        this.lectureNotes = lectureNotes;
    }

    public String getLectureWorksheet() {
        return lectureWorksheet;
    }

    public void setLectureWorksheet(String lectureWorksheet) {
        this.lectureWorksheet = lectureWorksheet;
    }

    public String getLectureType() {
        return lectureType;
    }

    public void setLectureType(String lectureType) {
        this.lectureType = lectureType;
    }

    public String getLiveClassDate() {
        return liveClassDate;
    }

    public void setLiveClassDate(String liveClassDate) {
        this.liveClassDate = liveClassDate;
    }

    public String getLiveClassTime() {
        return liveClassTime;
    }

    public void setLiveClassTime(String liveClassTime) {
        this.liveClassTime = liveClassTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
