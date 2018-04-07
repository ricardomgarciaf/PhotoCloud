package com.example.ricardogarcia.photocloud.persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Environment;

import com.example.ricardogarcia.photocloud.persistence.dao.AlbumDao;
import com.example.ricardogarcia.photocloud.persistence.dao.PhotoDao;
import com.example.ricardogarcia.photocloud.persistence.dao.UserDao;
import com.example.ricardogarcia.photocloud.persistence.entity.Album;
import com.example.ricardogarcia.photocloud.persistence.entity.Photo;
import com.example.ricardogarcia.photocloud.persistence.entity.User;

import java.io.File;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Database(entities = {User.class, Album.class, Photo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract AlbumDao albumDao();

    public abstract PhotoDao photoDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE=Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "photocloud-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
