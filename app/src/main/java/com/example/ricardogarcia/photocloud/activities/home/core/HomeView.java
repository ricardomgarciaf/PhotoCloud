package com.example.ricardogarcia.photocloud.activities.home.core;

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */

public interface HomeView {

    void showExistingAlbumName();

    void showAlbumError();

    void onAlbumCreated();

    void goToAlbumDescription(String albumName);
}
