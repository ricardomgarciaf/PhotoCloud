package com.example.ricardogarcia.photocloud.activities.home.dagger;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.home.core.HomeModel;
import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */
@Module
public class HomeModule {
    HomeActivity homeActivity;

    public HomeModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @HomeScope
    @Provides
    HomeActivity provideContext(){
        return homeActivity;
    }

    @HomeScope
    @Provides
    HomeModel provideModel(PhotoCloudApiInterface api, AlbumDataSource albumDataSource, UserDataSource userDataSource, PhotoDataSource photoDataSource){
        return new HomeModel(homeActivity,api,albumDataSource,userDataSource,photoDataSource);
    }
}
