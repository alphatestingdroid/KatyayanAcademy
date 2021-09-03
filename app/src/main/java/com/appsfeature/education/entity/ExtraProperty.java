package com.appsfeature.education.entity;

import androidx.annotation.NonNull;

import com.appsfeature.education.model.EducationModel;
import com.helper.model.BaseCategoryProperty;
import com.helper.util.GsonParser;

import java.io.Serializable;

public class ExtraProperty extends BaseCategoryProperty implements Cloneable, Serializable {

    private int courseId;
    private int subCourseId;
    private int contentType;
    private String subjectId;
    private String chapterId;
    private String videoId;
    private String apiKey;
    private boolean isLiveClass;
    private boolean isOldVideos;
    private EducationModel educationModel;

    private int videoTime = 0;
    private int videoDuration = 0;
    private int isRead = 0;
    private int isFav = 0;
    private String jsonData;
    private String channelId;

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(int subCourseId) {
        this.subCourseId = subCourseId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public EducationModel getEducationModel() {
        return educationModel;
    }

    public void setEducationModel(EducationModel educationModel) {
        this.educationModel = educationModel;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isLiveClass() {
        return isLiveClass;
    }

    public void setLiveClass(boolean liveClass) {
        isLiveClass = liveClass;
    }

    public boolean isOldVideos() {
        return isOldVideos;
    }

    public void setOldVideos(boolean oldVideos) {
        isOldVideos = oldVideos;
    }

    public int getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(int videoTime) {
        this.videoTime = videoTime;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String toJson() {
        return GsonParser.getGson().toJson(this, ExtraProperty.class);
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ExtraProperty getClone() {
        try {
            return (ExtraProperty) clone();
        } catch (CloneNotSupportedException e) {
            return new ExtraProperty();
        }
    }
}
