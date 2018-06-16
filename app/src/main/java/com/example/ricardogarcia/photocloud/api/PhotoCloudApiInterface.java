package com.example.ricardogarcia.photocloud.api;

import com.example.ricardogarcia.photocloud.model.Photo;
import com.example.ricardogarcia.photocloud.model.ServiceResponse;
import com.example.ricardogarcia.photocloud.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public interface PhotoCloudApiInterface {

    @POST("user/create")
    Observable<ServiceResponse> createUser(@Body User user);

    @GET("login/{email}/{password}")
    Observable<ServiceResponse> login(@Path("email") String email, @Path("password") String password);

    @POST("album/create")
    Observable<ServiceResponse> createAlbum(@Body String albumTitle, @Body String userID);

    @POST("/album/delete")
    Observable<ServiceResponse> deleteAlbums(@Body List<String> albumID, @Body String userID);

    @POST("photo/create")
    Observable<ServiceResponse> createPhoto(@Body Photo photo);

    @GET("photo/delete")
    Observable<ServiceResponse> deletePhotos(@Body List<String> photoID);
}
