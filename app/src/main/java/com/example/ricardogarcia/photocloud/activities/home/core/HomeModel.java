package com.example.ricardogarcia.photocloud.activities.home.core;

import android.os.Handler;
import android.os.Looper;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;
import com.example.ricardogarcia.photocloud.repository.entity.Album;

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
    private String userId;

    public HomeModel(HomeActivity homeActivity, PhotoCloudApiInterface api, AlbumDataSource albumDataSource, UserDataSource userDataSource) {
        this.homeActivity = homeActivity;
        this.api = api;
        this.albumDataSource = albumDataSource;
        this.userDataSource = userDataSource;
    }

    Disposable createAlbum(String albumName, OnHomeListener listener) {
        if (albumName.isEmpty()) {
            listener.onAlbumNameError();
            return null;
        }
        return Observable.fromCallable(() -> albumDataSource.isAlbumNameRepeated(albumName))
                .doOnNext(albumRepeated -> {
                    if (albumRepeated) {
                        Timber.d("Album name repeated");
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onExistingAlbumName();
                            }
                        });
                    }else{
                        Timber.d("Album name no repeated");
                    }
                })
                .filter(albumRepeated -> !albumRepeated)
                .flatMap(aBoolean -> {
                    Timber.d("FlatMap");
                    userId = userDataSource.findByName(PhotoCloudApplication.pref.getString(PhotoCloudApplication.KEY_USER, "")).getId();
                    return api.createAlbum(albumName, userId);
                })
                .map(serviceResponse -> {
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
                    if (throwable != null) {
                        throwable.printStackTrace();
                    }
                    listener.onFailure();
                });
    }


}
