package com.appsfeature.education.task;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.entity.ExtraProperty;
import com.appsfeature.education.util.AppDbHelper;
import com.helper.task.TaskRunner;

import java.util.concurrent.Callable;

public class YTInsertWatchListTask {
    private final ExtraProperty mVideoModel;
    private AppDbHelper dbHelper;

    public YTInsertWatchListTask(ExtraProperty mVideoModel) {
        this.mVideoModel = mVideoModel;
    }

    public void execute() {
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
                            dbHelper.insertVideoInWatchList(mVideoModel);
                            return null;
                        }
                    });
                }
                return null;
            }
        });
    }
}
