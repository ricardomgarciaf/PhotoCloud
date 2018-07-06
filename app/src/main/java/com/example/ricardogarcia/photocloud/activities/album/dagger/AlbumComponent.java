package com.example.ricardogarcia.photocloud.activities.album.dagger;

import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.application.builder.AppComponent;

import dagger.Component;

@AlbumScope
@Component(dependencies = {AppComponent.class}, modules = {AlbumModule.class})
public interface AlbumComponent {
    void inject(AlbumActivity albumActivity);
}
