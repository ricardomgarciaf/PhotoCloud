package com.example.ricardogarcia.photocloud.model;

import java.util.List;

public class AlbumsDeletionInfo {
    private List<String> albumIDs;
    private String userID;

    public AlbumsDeletionInfo(List<String> albumIDs, String userID) {
        this.albumIDs = albumIDs;
        this.userID = userID;
    }

    public List<String> getAlbumIDs() {
        return albumIDs;
    }

    public void setAlbumIDs(List<String> albumIDs) {
        this.albumIDs = albumIDs;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
