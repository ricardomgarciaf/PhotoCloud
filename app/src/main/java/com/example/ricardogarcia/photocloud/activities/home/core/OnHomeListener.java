package com.example.ricardogarcia.photocloud.activities.home.core;

import java.util.List;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */

public interface OnHomeListener {

    void onAlbumsDeleted(List<String> albumsName);

    void onAlbumNameError();

    void onExistingAlbumName();

    void onSucces();

    void onFailure();
}
