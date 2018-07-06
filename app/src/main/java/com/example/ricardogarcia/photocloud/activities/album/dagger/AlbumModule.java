package com.example.ricardogarcia.photocloud.activities.album.dagger;

import com.example.ricardogarcia.photocloud.activities.album.AlbumActivity;
import com.example.ricardogarcia.photocloud.activities.album.core.AlbumModel;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumModule {
    private AlbumActivity albumActivity;

    public AlbumModule(AlbumActivity albumActivity){
        this.albumActivity=albumActivity;
    }

    @AlbumScope
    @Provides
    AlbumActivity provideContext(){
        return albumActivity;
    }

    @AlbumScope
    @Provides
    AlbumModel provideModel(PhotoCloudApiInterface api, AlbumDataSource albumDataSource, UserDataSource userDataSource, PhotoDataSource photoDataSource){
        return new AlbumModel(api,albumActivity,albumDataSource,userDataSource,photoDataSource);
    }
}
