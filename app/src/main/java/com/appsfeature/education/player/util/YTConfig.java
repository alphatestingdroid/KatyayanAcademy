package com.appsfeature.education.player.util;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public enum YTConfig {
    instance;

    private String googleApiKey;
    private String videoId;
    private String playlistId;
    private int maxResultsCount = 40;

    public static void setApiKey(String googleApiKey) {
        instance.googleApiKey = googleApiKey;
    }

    public static int getMaxResultsCount() {
        return instance.maxResultsCount;
    }

    public static void setMaxResultsCount(int maxResultsCount) {
        instance.maxResultsCount = maxResultsCount;
    }

    public static String getApiKey() {
        if ( instance != null ) {
            return instance.googleApiKey;
        }
        return "" ;
    }

    public static void setVideoId(String videoId) {
        instance.videoId =videoId;
    }

    public static String getVideoId() {
        return instance.videoId;
    }

    public static void setPlaylistId(String playlistId) {
        instance.playlistId = playlistId;
    }

    public static String getPlaylistId() {
        return instance.playlistId;
    }
}
