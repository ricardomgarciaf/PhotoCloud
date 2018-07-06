package com.example.ricardogarcia.photocloud.activities.home.core;

import android.os.Handler;
import android.os.Looper;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.home.list.Function;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.model.AlbumCreationInfo;
import com.example.ricardogarcia.photocloud.model.AlbumsDeletionInfo;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;
import com.example.ricardogarcia.photocloud.repository.entity.Album;
import com.example.ricardogarcia.photocloud.repository.entity.Photo;
import com.example.ricardogarcia.photocloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class HomeModel {
    private HomeActivity homeActivity;
    private PhotoCloudApiInterface api;
    private AlbumDataSource albumDataSource;
    private UserDataSource userDataSource;
    private PhotoDataSource photoDataSource;
    private String userId;

    public HomeModel(HomeActivity homeActivity, PhotoCloudApiInterface api, AlbumDataSource albumDataSource, UserDataSource userDataSource, PhotoDataSource photoDataSource) {
        this.homeActivity = homeActivity;
        this.api = api;
        this.albumDataSource = albumDataSource;
        this.userDataSource = userDataSource;
        this.photoDataSource = photoDataSource;
    }

    Disposable createAlbum(String albumName, OnHomeListener listener) {
        if (albumName.isEmpty()) {
            listener.onAlbumNameError();
            return null;
        }
        return Observable.fromCallable(() -> {
            userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
            return albumDataSource.isAlbumNameRepeated(albumName, userId);
        })
                .doOnNext(albumRepeated -> {
                    if (albumRepeated) {
                        Timber.d("Album name repeated");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onExistingAlbumName();
                            }
                        });
                    } else {
                        Timber.d("Album name no repeated");
                    }
                })
                .filter(albumRepeated -> !albumRepeated)
                .flatMap(aBoolean -> {
                    Timber.d("FlatMap");
                    return api.createAlbum(new AlbumCreationInfo(albumName, userId));
                })
                .map(serviceResponse -> {
                    Timber.d("Service response->" + serviceResponse.getCode());
                    if (serviceResponse.getCode() == 1) {
                        albumDataSource.addItem(new Album(serviceResponse.getObject().toString(), albumName, userId));
                        Timber.d("added to local DB");
                    }
                    return serviceResponse.getCode();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    switch (code) {
                        case 1:
                            Timber.d("onSuccess");
                            listener.onSucces();
                            break;
                        default:
                            listener.onFailure();
                            break;
                    }
                }, throwable -> {
                    UiUtils.logThrowable(throwable);
                    listener.onFailure();
                });
    }

    Observable<List<HashMap<String, String>>> provideAlbumList() {
        List<HashMap<String, String>> albumMap = new ArrayList<>();
        return Observable.fromCallable(() -> {
            userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
            List<Album> albums = albumDataSource.getAlbumsByUser(userId);
            albums.forEach(a -> {
                List<Photo> photosByAlbum = photoDataSource.getPhotosByAlbum(a.getId());
                albumMap.add(Function.mappingAlbumInbox(a.getName(), photosByAlbum.size() > 0 ? photosByAlbum.get(0).getSource() : null, String.valueOf(photosByAlbum.size())));
            });
            return albumMap;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Disposable deleteAlbum(List<String> albumNames, OnHomeListener listener) {
        return Observable.fromCallable(() -> {
            userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
            return albumDataSource.getAlbumsByUser(userId).stream().filter(a -> albumNames.contains(a.getName())).map(Album::getId).collect(Collectors.toList());
        }).flatMap(albums -> api.deleteAlbums(new AlbumsDeletionInfo(albums, userId)))
                .map(serviceResponse -> {
                    if (serviceResponse.getCode() == 1) {
                        albumNames.forEach(albumName -> albumDataSource.deleteItemByName(albumName, userId));
                    }
                    return serviceResponse.getCode();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    switch (code) {
                        case 1:
                            listener.onAlbumsDeleted(albumNames);
                            break;
                        default:
                            listener.onFailure();
                            break;
                    }
                }, throwable -> {
                    UiUtils.logThrowable(throwable);
                    listener.onFailure();
                });
    }

    public void goAlbumDescription(HashMap<String, String> albumSelected) {
        homeActivity.goToAlbumDescription(albumSelected.get(Function.KEY_ALBUM));
    }


}
