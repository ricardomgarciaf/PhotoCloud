package com.example.ricardogarcia.photocloud.activities.home.core;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.home.list.Function;
import com.example.ricardogarcia.photocloud.utils.UiUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class HomePresenter implements OnHomeListener {

    private HomeActivity view;
    private HomeModel model;
    private CompositeDisposable disposables;
    private List<HashMap<String, String>> albumsList;
    private List<Integer> selectedItems;

    public HomePresenter(HomeActivity view, HomeModel model) {
        this.view = view;
        this.model = model;
        disposables = new CompositeDisposable();
    }

    public void onCreate() {
        disposables.add(getAlbumsList());
        disposables.add(getAlbumItemClicked());
        disposables.add(getAlbumLongItemClicked());
    }

    public void onDestroy() {
        disposables.clear();
    }

    public void createAlbum(String name) {
        disposables.add(model.createAlbum(name, this));
    }

    @Override
    public void onAlbumsDeleted(List<String> selectedItems) {
        albumsList.removeIf(album->selectedItems.contains(album.get(Function.KEY_ALBUM)));
        //selectedItems.forEach(i -> albumsList.remove((int) i));
        view.loadAlbums(albumsList);
    }

    @Override
    public void onAlbumNameError() {
        if (view != null) {
            view.showAlbumError();
        }
    }

    @Override
    public void onExistingAlbumName() {
        if (view != null) {
            view.showExistingAlbumName();
        }
    }

    @Override
    public void onSucces() {
        if (view != null) {
            view.onAlbumCreated();
            disposables.clear();
            onCreate();
        }
    }

    @Override
    public void onFailure() {
        if (view != null) {
            view.showAlbumError();
        }
    }

    private Disposable getAlbumsList() {
        return model.provideAlbumList()
                .subscribe(albums -> {
                    albumsList = albums;
                    view.loadAlbums(albums);
                }, throwable -> {
                    onFailure();
                    UiUtils.logThrowable(throwable);
                });
    }

    private Disposable getAlbumItemClicked() {
        return view.albumItemClicks().subscribe(integer -> {
            if (!view.isActionModeVisible()) {
                model.goAlbumDescription(albumsList.get(integer));
            } else {
                view.selectAlbumItem(integer);
            }
        });
    }

    private Disposable getAlbumLongItemClicked() {
        return view.albumLongItemClicks().subscribe(view::selectAlbumLongItem);
    }

    public void deleteSelectedItems(List<Integer> selectedItems) {
        List<String> albumNames = new ArrayList<>();
        selectedItems.forEach(i->albumNames.add(albumsList.get(i).get(Function.KEY_ALBUM)));
        model.deleteAlbum(albumNames, this);
    }
}
