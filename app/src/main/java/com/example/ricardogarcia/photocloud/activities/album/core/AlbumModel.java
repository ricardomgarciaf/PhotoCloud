package com.example.ricardogarcia.photocloud.activities.album.core;

import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.activities.home.list.Function;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;
import com.example.ricardogarcia.photocloud.repository.entity.Photo;
import com.example.ricardogarcia.photocloud.utils.DateUtils;
import com.example.ricardogarcia.photocloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AlbumModel {

    private PhotoCloudApiInterface api;
    private AlbumActivity albumActivity;
    private AlbumDataSource albumDataSource;
    private UserDataSource userDataSource;
    private PhotoDataSource photoDataSource;
    private String userId;
    private String albumId;

    public AlbumModel(PhotoCloudApiInterface api, AlbumActivity albumActivity, AlbumDataSource albumDataSource, UserDataSource userDataSource, PhotoDataSource photoDataSource) {
        this.api = api;
        this.albumActivity = albumActivity;
        this.albumDataSource = albumDataSource;
        this.userDataSource = userDataSource;
        this.photoDataSource = photoDataSource;
    }

    Disposable createPhoto(String source, String albumName, String geolocation, OnAlbumListener listener) {
        return Observable.fromCallable(() -> {
            userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
            albumId = albumDataSource.getAlbumByName(albumName, userId).getId();
            return albumId;
        }).flatMap(id -> api.createPhoto(new com.example.ricardogarcia.photocloud.model.Photo(source, id, geolocation)))
                .map(s -> {
                    Timber.d("Service response->%s", s.getCode());
                    if (s.getCode() == 1) {
                        photoDataSource.addItem(new Photo((String) s.getObject(), source, geolocation, albumId));
                        Timber.d("added to local DB");
                    }
                    return s.getCode();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    switch (s) {
                        case 1:
                            listener.onPhotoCreatedSuccess();
                            break;
                        default:
                            listener.onPhotoCreatedFailure();
                            break;
                    }
                }, throwable -> {
                    listener.onPhotoCreatedFailure();
                    UiUtils.logThrowable(throwable);
                });
    }

    Observable<List<HashMap<String, String>>> providePhotoList(String albumName) {
        List<HashMap<String, String>> photoList = new ArrayList<>();
        return Observable.fromCallable(() -> {
            userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
            albumId = albumDataSource.getAlbumByName(albumName, userId).getId();
            List<Photo> photos = photoDataSource.getPhotosByAlbum(albumId);
            photos.forEach(photo -> {
                photoList.add(Function.mappingPhotoInbox(photo.getId(), photo.getSource(), photo.getDateCreated().format(DateUtils.dateFormatter), photo.getGeolocation()));
            });
            return photoList;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    Disposable deletePhotos(List<String> photoIDs, OnAlbumListener listener) {
        return api.deletePhotos(photoIDs)
                .flatMap(s -> {
                    if (s.getCode() == 1) {
                        photoIDs.forEach(photoDataSource::deletePhotoById);
                    }
                    return Observable.fromArray(s.getCode());
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    switch (code) {
                        case 1:
                            listener.onPhotoDeletedSuccess(photoIDs);
                            break;
                        default:
                            listener.onPhotoDeletedFailure();
                            break;
                    }
                }, throwable -> {
                    listener.onPhotoDeletedFailure();
                    UiUtils.logThrowable(throwable);
                });
    }
}
