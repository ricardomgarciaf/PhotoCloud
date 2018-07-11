package com.example.ricardogarcia.photocloud.model;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public class Photo {
    private String source;
    private String albumID;
    private String geolocation;

    public Photo(String source, String albumID, String geolocation) {
        this.source = source;
        this.albumID = albumID;
        this.geolocation = geolocation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }
}
