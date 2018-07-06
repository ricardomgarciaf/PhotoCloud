package com.example.ricardogarcia.photocloud.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.ricardogarcia.photocloud.repository.converter.Converters;
import com.example.ricardogarcia.photocloud.repository.dao.AlbumDao;
import com.example.ricardogarcia.photocloud.repository.dao.PhotoDao;
import com.example.ricardogarcia.photocloud.repository.dao.UserDao;
import com.example.ricardogarcia.photocloud.repository.entity.Album;
import com.example.ricardogarcia.photocloud.repository.entity.Photo;
import com.example.ricardogarcia.photocloud.repository.entity.User;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Database(entities = {User.class, Album.class, Photo.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract AlbumDao albumDao();

    public abstract PhotoDao photoDao();

}
