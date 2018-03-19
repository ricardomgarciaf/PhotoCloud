package com.example.ricardogarcia.application.builder;

import android.content.Context;

import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;

import dagger.Component;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@AppScope
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {

    Context provideContext();

    PhotoCloudApiInterface apiServiceModule();
}
