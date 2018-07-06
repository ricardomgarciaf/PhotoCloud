package com.example.ricardogarcia.photocloud.api;

import com.example.ricardogarcia.photocloud.model.AlbumCreationInfo;
import com.example.ricardogarcia.photocloud.model.AlbumsDeletionInfo;
import com.example.ricardogarcia.photocloud.model.Photo;
import com.example.ricardogarcia.photocloud.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public interface PhotoCloudApiInterface {

    @POST("user/create")
    Observable<ServiceResponse> createUser(@Body User user);

    @GET("login")
    Observable<ServiceResponse> login(@Query("email") String email, @Query("password") String password);

    @POST("album/create")
    Observable<ServiceResponse> createAlbum(@Body AlbumCreationInfo albumCreationInfo);

    @POST("album/delete")
    Observable<ServiceResponse> deleteAlbums(@Body AlbumsDeletionInfo albumsDeletionInfo);

    @POST("photo/create")
    Observable<ServiceResponse> createPhoto(@Body Photo photo);

    @POST("photo/delete")
    Observable<ServiceResponse> deletePhotos(@Body List<String> photoID);
}
