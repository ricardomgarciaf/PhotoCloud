package com.example.ricardogarcia.photocloud.activities.home.dagger;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;

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
}
