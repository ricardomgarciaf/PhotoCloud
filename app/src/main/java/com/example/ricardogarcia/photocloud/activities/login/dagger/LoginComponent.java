package com.example.ricardogarcia.photocloud.activities.login.dagger;

import com.example.ricardogarcia.application.builder.AppComponent;
import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;

import dagger.Component;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */
@LoginScope
@Component(dependencies = {AppComponent.class}, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
