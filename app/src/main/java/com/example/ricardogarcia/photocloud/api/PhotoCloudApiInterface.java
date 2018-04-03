package com.example.ricardogarcia.photocloud.api;

import com.example.ricardogarcia.model.Photo;
import com.example.ricardogarcia.model.ServiceResponse;
import com.example.ricardogarcia.model.User;

import io.reactivex.Observable;
import retrofit2.Call;
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

    @GET("album/create/{albumTitle}/{userID}")
    Observable<ServiceResponse> createAlbum(@Path("albumTitle") String albumTitle, @Path("userID") String userID);

    @POST("photo/create")
    Observable<ServiceResponse> createPhoto(@Body Photo photo);

    @GET("photo/{photoID}/delete")
    Observable<ServiceResponse> deletePhoto(@Path("photoID") String photoID);
}
