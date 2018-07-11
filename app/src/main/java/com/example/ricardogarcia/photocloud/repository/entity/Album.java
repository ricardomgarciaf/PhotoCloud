package com.example.ricardogarcia.photocloud.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.OffsetDateTime;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Entity(tableName = "album",indices = {@Index("id")},foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId",onDelete = CASCADE))
public class Album {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(name = "dateCreated")
    private OffsetDateTime dateCreated;

    public Album(@NonNull String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.dateCreated=OffsetDateTime.now();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }
}
