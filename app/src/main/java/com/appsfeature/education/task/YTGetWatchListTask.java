package com.appsfeature.education.task;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.model.EducationModel;
import com.appsfeature.education.util.AppDbHelper;
import com.helper.callback.Response;
import com.helper.task.TaskRunner;
import com.helper.util.BaseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class YTGetWatchListTask {
    private final List<EducationModel>  mVideoList;
    private AppDbHelper dbHelper;

    public YTGetWatchListTask(List<EducationModel> mVideoList) {
        this.mVideoList = mVideoList;
    }

    public void execute(Response.Status<Boolean> callback) {
        try {
            dbHelper = AppApplication.getInstance().getDBObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TaskRunner.getInstance().executeAsync(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (dbHelper != null) {
                    dbHelper.callDBFunction(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            HashMap<String, ExtraProperty> watchList = dbHelper.fetchWatchList();
                            if (mVideoList != null && watchList.size() > 0) {
                                for (EducationModel item : mVideoList){
                                    ExtraProperty watchedItem = watchList.get(getVideoIdFromUrl(item.getLectureVideo()));
                                    if (watchedItem != null) {
                                        item.setVideoTime(watchedItem.getVideoTime());
                                        item.setVideoDuration(watchedItem.getVideoDuration());
//                                        item.setVideoTimeFormatted(convertTimeStamp(watchedItem.getVideoTime()));
                                        item.setIsRead(watchedItem.getIsRead());
                                    }
                                }
                            }
                            return null;
                        }
                    });
                }
                return null;
            }
        }, new TaskRunner.Callback<Void>() {
            @Override
            public void onComplete(Void result) {
                callback.onSuccess(true);
            }
        });
    }
    public String convertTimeStamp(int millis){
        String hms = String.format("%02d min:%02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }

    public String getVideoIdFromUrl(String lectureVideo) {
        try {
            if (!TextUtils.isEmpty(lectureVideo) && BaseUtil.isValidUrl(lectureVideo)) {
                return lectureVideo.substring(lectureVideo.lastIndexOf("/") + 1);
            }else {
                return lectureVideo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return lectureVideo;
        }
    }
}
