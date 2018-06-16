package com.example.ricardogarcia.photocloud.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ricardogarcia.photocloud.repository.entity.Album;

import java.util.List;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album")
    List<Album> getAll();

    @Query("SELECT * FROM album where userId=:userId")
    List<Album> getAlbumsByUser(String userId);

    @Query("SELECT COUNT(*) FROM album where name=:albumName AND userId=:userId")
    int isAlbumNameRepeated(String albumName, String userId);

    @Insert
    void insert(Album album);

    @Delete
    void delete(Album album);

    @Query("DELETE FROM album WHERE name=:albumName AND userId=:userId")
    void deleteAlbumByName(String albumName, String userId);
}
