package com.example.ricardogarcia.photocloud.api;

import com.example.ricardogarcia.model.Photo;
import com.example.ricardogarcia.model.ServiceResponse;
import com.example.ricardogarcia.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public interface PhotoCloudApiInterface {

    @GET("/user/create")
    Call<ServiceResponse> createUser(User user);

    @GET("/login/{email}/{password}")
    Call<ServiceResponse> login(@Path("email") String email, @Path("password") String password);

    @GET("/album/create/{albumTitle}/{userID}")
    Call<ServiceResponse> createAlbum(@Path("albumTitle") String albumTitle, @Path("userID") String userID);

    @GET("/photo/create")
    Call<ServiceResponse> createPhoto(Photo photo);

    @GET("/photo/{photoID}/delete")
    Call<ServiceResponse> deletePhoto(@Path("photoID") String photoID);
}
