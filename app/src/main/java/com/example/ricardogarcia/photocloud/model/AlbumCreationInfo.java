package com.example.ricardogarcia.photocloud.model;

public class AlbumCreationInfo {
    private String albumTitle;
    private String userID;

    public AlbumCreationInfo(String albumTitle, String userID) {
        this.albumTitle = albumTitle;
        this.userID = userID;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
