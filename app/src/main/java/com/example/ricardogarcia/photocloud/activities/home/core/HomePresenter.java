package com.example.ricardogarcia.photocloud.activities.home.core;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;

import io.reactivex.disposables.Disposable;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class HomePresenter implements OnHomeListener{

    private Disposable disposable;
    private HomeActivity view;

    private HomeModel model;

    public HomePresenter(HomeActivity view, HomeModel model) {
        this.view = view;
        this.model = model;
    }

    public void onDestroy(){
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public void createAlbum(String name){
        disposable=model.createAlbum(name,this);
    }

    @Override
    public void onAlbumNameError() {
        if(view!=null) {
            view.showAlbumError();
        }
    }

    @Override
    public void onExistingAlbumName() {
        if(view!=null){
            view.showExistingAlbumName();
        }
    }

    @Override
    public void onSucces() {

    }

    @Override
    public void onFailure() {

    }
}
