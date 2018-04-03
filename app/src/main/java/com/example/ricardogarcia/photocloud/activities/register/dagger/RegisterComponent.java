package com.example.ricardogarcia.photocloud.activities.register.dagger;

import com.example.ricardogarcia.application.builder.AppComponent;
import com.example.ricardogarcia.photocloud.activities.register.RegisterActivity;

import dagger.Component;

/**
 * Created by Ricardo Garcia on 4/1/2018.
 */
@RegisterScope
@Component(dependencies = {AppComponent.class},modules = {RegisterModule.class})
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
}
