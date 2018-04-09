package com.example.ricardogarcia.photocloud.application.builder;

import android.content.Context;

import com.example.ricardogarcia.photocloud.api.PhotoCloudApiInterface;
import com.example.ricardogarcia.photocloud.repository.dao.AlbumDao;
import com.example.ricardogarcia.photocloud.repository.dao.PhotoDao;
import com.example.ricardogarcia.photocloud.repository.dao.UserDao;
import com.example.ricardogarcia.photocloud.repository.database.AppDatabase;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;

import dagger.Component;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@AppScope
@Component(modules = {AppModule.class, ApiServiceModule.class, RoomModule.class})
public interface AppComponent {

    Context provideContext();

    PhotoCloudApiInterface apiServiceModule();

    AppDatabase roomModule();

    UserDao userDao();

    UserDataSource userDataSource();

    PhotoDao photoDao();

    PhotoDataSource photoDataSource();

    AlbumDao albumDao();

    AlbumDataSource albumDataSource();
}
