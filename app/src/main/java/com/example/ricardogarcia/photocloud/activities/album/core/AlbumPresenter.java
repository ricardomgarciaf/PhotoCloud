package com.example.ricardogarcia.photocloud.activities.album.core;

import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.activities.home.list.Function;
import com.example.ricardogarcia.photocloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AlbumPresenter implements OnAlbumListener {

    private AlbumActivity view;
    private AlbumModel model;
    private CompositeDisposable disposables;
    private List<HashMap<String, String>> photosList;
    private String albumName;

    public AlbumPresenter(AlbumActivity albumActivity, AlbumModel albumModel) {
        this.view = albumActivity;
        this.model = albumModel;
        this.disposables = new CompositeDisposable();
    }

    public void onCreate(String albumName) {
        this.albumName = albumName;
        disposables.add(getPhotosList(albumName));
        disposables.add(getPhotoItemClicked());
        disposables.add(getPhotoLongItemClicked());
    }

    public void onDestroy() {
        disposables.clear();
    }

    public void createPhoto(String source, String albumName, String geolocation) {
        disposables.add(model.createPhoto(source, albumName, geolocation, this));
    }

    private Disposable getPhotosList(String albumName) {
        return model.providePhotoList(albumName)
                .subscribe(photos -> {
                    photosList = photos;
                    view.loadPhotos(photos);
                }, throwable -> {
                    onPhotosDisplayedFailure();
                    UiUtils.logThrowable(throwable);
                });
    }

    private Disposable getPhotoItemClicked() {
        return view.photoItemClicks().subscribe(i -> {
            if (!view.isActionModeVisible()) {

            } else {
                view.selectItem(i);
            }
        });
    }

    private Disposable getPhotoLongItemClicked() {
        return view.photoLongItemClicks().subscribe(view::selectLongItem);
    }

    public void deleteSelectedItems(ArrayList<Integer> selectedItems) {
        List<String> photoIDs = new ArrayList<>();
        selectedItems.forEach(i -> photoIDs.add(photosList.get(i).get(Function.KEY_ID)));
        disposables.add(model.deletePhotos(photoIDs, this));
    }


    @Override
    public void onPhotoCreatedSuccess() {
        disposables.clear();
        onCreate(albumName);
    }

    @Override
    public void onPhotoCreatedFailure() {
        if (view != null) {
            view.onFailure();
        }
    }

    @Override
    public void onPhotosDisplayedFailure() {
        if (view != null) {
            view.onFailure();
        }
    }

    @Override
    public void onPhotoDeletedSuccess(List<String> photoIDs) {
        photosList.removeIf(photo -> photoIDs.contains(photo.get(Function.KEY_ID)));
        if (view != null) {
            view.loadPhotos(photosList);
        }
    }

    @Override
    public void onPhotoDeletedFailure() {
        if (view != null) {
            view.onFailure();
        }
    }
}
