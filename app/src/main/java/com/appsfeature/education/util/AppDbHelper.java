package com.appsfeature.education.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.entity.ExtraProperty;
import com.helper.util.BaseDatabaseHelper;

import java.util.HashMap;
import java.util.concurrent.Callable;


public class AppDbHelper extends BaseDatabaseHelper {

    public static final String DB_NAME = "katyayan.sqlite";
    public static final int DATABASE_VERSION = 1;

    static SQLiteDatabase db;
    Context context;

    private static final String TABLE_WATCH_LIST = "watch_list";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VIDEO_ID = "video_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VIDEO_TIME = "video_time";
    private static final String COLUMN_IS_READ = "is_read";
    private static final String COLUMN_IS_FAV = "is_fav";
    private static final String COLUMN_IS_ACTIVE = "is_active";
    private static final String COLUMN_JSON_DATA = "json_data";
    public static final int ACTIVE = 1;
    public static final int IN_ACTIVE = 0;

    private static final String CREATE_TABLE_WATCH_LIST = "CREATE TABLE IF NOT EXISTS watch_list (" +
            "    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    video_id    VARCHAR," +
            "    title       VARCHAR," +
            "    video_time  INTEGER DEFAULT (0)," +
            "    is_read     INTEGER DEFAULT (0)," +
            "    is_fav      INTEGER DEFAULT (0)," +
            "    is_active   INTEGER DEFAULT (0)," +
            "    json_data   VARCHAR" +
            ");";


    private AppDbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static AppDbHelper instance;

    public static AppDbHelper getInstance(Context context) {
        if (instance == null)
            instance = new AppDbHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WATCH_LIST);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
        }
    }

    public void callDBFunction(Callable<Void> function) {
        try {
            db = getDB();
            if (db != null) {
                if (!db.inTransaction()) {
                    db.beginTransaction();
                }
                function.call();
                if (db.inTransaction()) {
                    db.setTransactionSuccessful();
                    if (db.inTransaction()) {
                        db.endTransaction();
                    }
                }

            }
        } catch (Exception e) {
            try {
                db = getDB();
                if (db != null) {
                    if (db.inTransaction()) {
                        db.setTransactionSuccessful();
                        if (db.inTransaction()) {
                            db.endTransaction();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (db != null && db.inTransaction())
                    db.endTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private SQLiteDatabase getDB(){
        SQLiteDatabase database = getWritableDatabase();
        if (database == null) {
            instance = new AppDbHelper(AppApplication.getInstance());
        }
        return database;
    }


    public void insertVideoInWatchList(ExtraProperty model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VIDEO_ID, model.getVideoId());
        contentValues.put(COLUMN_TITLE, model.getTitle());
        contentValues.put(COLUMN_VIDEO_TIME, model.getVideoTime());
        contentValues.put(COLUMN_IS_READ, ACTIVE);
        contentValues.put(COLUMN_JSON_DATA, model.toJson());

        String where = COLUMN_VIDEO_ID + "='" + model.getVideoId() + "'";
        Cursor cursor = db.query(TABLE_WATCH_LIST, null, where, null, null, null, null);

        try {
            if (!(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())) {
                if (cursor != null) {
                    cursor.close();
                }
                try {
                    db.insertOrThrow(TABLE_WATCH_LIST, null, contentValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    db.update(TABLE_WATCH_LIST, contentValues, where, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVideoFromWatchList(int videoId) {
        try {
            String where = COLUMN_VIDEO_ID + "='" + videoId + "'";
            db.delete(TABLE_WATCH_LIST, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateWatchListRead(String videoId, boolean isRead) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IS_READ, isRead ? ACTIVE : IN_ACTIVE);
        String where = COLUMN_VIDEO_ID + "='" + videoId + "'";
        try {
            int i = db.update(TABLE_WATCH_LIST, contentValues, where, null);
            Logger.log("updateNotification Read i -- " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateWatchListFavourite(String videoId, boolean isFavourite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IS_FAV, isFavourite ? ACTIVE : IN_ACTIVE);
        String where = COLUMN_VIDEO_ID + "='" + videoId + "'";
        try {
            int i = db.update(TABLE_WATCH_LIST, contentValues, where, null);
            Logger.log("updateNotification Read i -- " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, ExtraProperty> fetchWatchList() {
        HashMap<String, ExtraProperty> watchList = new HashMap<>();
        Cursor cursor = db.query(TABLE_WATCH_LIST, null, null, null, null, null, COLUMN_ID + " DESC");
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                ExtraProperty bean = new ExtraProperty();
                bean.setVideoId(cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO_ID)));
                bean.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                bean.setVideoTime(cursor.getInt(cursor.getColumnIndex(COLUMN_VIDEO_TIME)));
                bean.setIsRead(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_READ)));
                bean.setIsFav(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAV)));
                bean.setJsonData(cursor.getString(cursor.getColumnIndex(COLUMN_JSON_DATA)));
                watchList.put(bean.getVideoId(), bean);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return watchList;
    }
}
