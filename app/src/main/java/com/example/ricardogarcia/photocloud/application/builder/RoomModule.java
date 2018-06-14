package com.example.ricardogarcia.photocloud.application.builder;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Environment;

import com.example.ricardogarcia.photocloud.repository.dao.AlbumDao;
import com.example.ricardogarcia.photocloud.repository.dao.PhotoDao;
import com.example.ricardogarcia.photocloud.repository.dao.UserDao;
import com.example.ricardogarcia.photocloud.repository.database.AppDatabase;
import com.example.ricardogarcia.photocloud.repository.datasource.AlbumDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.PhotoDataSource;
import com.example.ricardogarcia.photocloud.repository.datasource.UserDataSource;

import java.io.File;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ricardo Garcia on 4/8/2018.
 */
@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(Context context){
        appDatabase= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                Environment.getExternalStorageDirectory()+ File.separator+"photocloud-database").build();
    }

    @AppScope
    @Provides
    AppDatabase provideRoomDatabase(){
        return appDatabase;
    }

    @AppScope
    @Provides
    UserDao provideUserDao(AppDatabase appDatabase){
        return appDatabase.userDao();
    }

    @AppScope
    @Provides
    PhotoDao providePhotoDao(AppDatabase appDatabase){
        return appDatabase.photoDao();
    }

    @AppScope
    @Provides
    AlbumDao provideAlbumDao(AppDatabase appDatabase){
        return appDatabase.albumDao();
    }

    @AppScope
    @Provides
    UserDataSource provideUserDataSource(UserDao userDao){
        return new UserDataSource(userDao);
    }

    @AppScope
    @Provides
    PhotoDataSource providePhotoDataSource(PhotoDao photoDao){
        return new PhotoDataSource(photoDao);
    }

    @AppScope
    @Provides
    AlbumDataSource provideAlbumDataSource(AlbumDao albumDao){
        return new AlbumDataSource(albumDao);
    }

}
