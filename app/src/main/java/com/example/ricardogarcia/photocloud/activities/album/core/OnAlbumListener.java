package com.example.ricardogarcia.photocloud.activities.album.core;

import java.util.List;

public interface OnAlbumListener {

    void onPhotoCreatedSuccess();

    void onPhotoCreatedFailure();

    void onPhotosDisplayedFailure();

    void onPhotoDeletedSuccess(List<String> photoIDs);

    void onPhotoDeletedFailure();
}
