package com.example.ricardogarcia.photocloud.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ricardogarcia.photocloud.repository.entity.Photo;

import java.util.List;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photo")
    List<Photo> getAll();

    @Query("SELECT * FROM photo where albumId=:albumId ORDER BY dateCreated")
    List<Photo> getPhotosByAlbum(String albumId);

    @Insert
    void insert(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("DELETE FROM photo WHERE id=:photoID")
    void deletePhotoById(String photoID);
}
