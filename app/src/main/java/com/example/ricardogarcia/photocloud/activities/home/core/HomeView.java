package com.example.ricardogarcia.photocloud.activities.home.core;

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */

public interface HomeView {

    void showExistingAlbumName();

    void showAlbumNameError();

    void onAlbumCreated();

    void onFailure();

    void goToAlbumDescription(String albumName);
}
