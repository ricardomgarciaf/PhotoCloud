package com.example.ricardogarcia.photocloud.activities.home.dagger;

import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.application.builder.AppComponent;

import dagger.Component;

/**
 * Created by Ricardo Garcia on 4/7/2018.
 */
@HomeScope
@Component(dependencies = {AppComponent.class},modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
