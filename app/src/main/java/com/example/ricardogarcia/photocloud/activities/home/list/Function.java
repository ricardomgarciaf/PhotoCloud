package com.example.ricardogarcia.photocloud.activities.home.list;

import java.util.HashMap;

public class Function {
    public static final String KEY_ALBUM = "album_name";
    public static final String KEY_PATH = "path";
    public static final String KEY_COUNT = "count";
    public static final String KEY_DATE = "date";
    public static final String KEY_GEO = "geo";

    public static HashMap<String, String> mappingAlbumInbox(String album, String path, String count) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ALBUM, album);
        map.put(KEY_PATH, path);
        map.put(KEY_COUNT, count);
        return map;
    }

    public static HashMap<String, String> mappingPhotoInbox(String path, String date, String geo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_PATH, path);
        map.put(KEY_DATE, date);
        map.put(KEY_GEO, geo);
        return map;
    }
}
