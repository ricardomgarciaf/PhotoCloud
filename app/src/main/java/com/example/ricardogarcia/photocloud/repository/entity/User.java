package com.example.ricardogarcia.photocloud.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Entity(tableName = "user", indices = {@Index("id")})
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name="dateCreated")
    private OffsetDateTime dateCreated;

    public User(@NonNull String id, String username) {
        this.id = id;
        this.username = username;
        this.dateCreated=OffsetDateTime.now();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
