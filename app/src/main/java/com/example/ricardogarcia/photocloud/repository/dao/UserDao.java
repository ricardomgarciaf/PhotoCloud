package com.example.ricardogarcia.photocloud.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ricardogarcia.photocloud.repository.entity.User;

import java.util.List;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where username=:username")
    User findByUsername(String username);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
