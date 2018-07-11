package com.example.ricardogarcia.photocloud.repository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.OffsetDateTime;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */
@Entity(tableName = "photo",foreignKeys = @ForeignKey(entity = Album.class,parentColumns = "id",childColumns = "albumId",onDelete = CASCADE))
public class Photo {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "source")
    private String source;

    @ColumnInfo(name = "geolocation")
    private String geolocation;

    @ColumnInfo(name = "albumId")
    private String albumId;

    @ColumnInfo(name="dateCreated")
    private OffsetDateTime dateCreated;

    public Photo(@NonNull String id, String source, String geolocation, String albumId) {
        this.id = id;
        this.source = source;
        this.geolocation = geolocation;
        this.albumId = albumId;
        this.dateCreated = OffsetDateTime.now();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }
}
