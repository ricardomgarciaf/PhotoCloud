package com.example.ricardogarcia.photocloud.activities.album.core;

import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.utils.UiUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AlbumPresenter {

    private AlbumActivity view;
    private AlbumModel model;
    private CompositeDisposable disposables;
    private List<HashMap<String,String>> photosList;

    public AlbumPresenter(AlbumActivity albumActivity, AlbumModel albumModel) {
        this.view = albumActivity;
        this.model = albumModel;
        this.disposables= new CompositeDisposable();
    }

    public void onCreate(String albumName) {
        disposables.add(getPhotosList(albumName));
    }

    public void onDestroy(){
        disposables.clear();
    }

    public void onFailure(){

    }

    private Disposable getPhotosList(String albumName) {
        return model.providePhotoList(albumName)
                .subscribe(photos->{
                    photosList=photos;
                    view.loadPhotos(photos);
                },throwable -> {
                    onFailure();
                    UiUtils.logThrowable(throwable);
                });
    }
}
