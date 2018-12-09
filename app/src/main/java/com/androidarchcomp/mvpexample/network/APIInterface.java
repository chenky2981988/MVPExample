package com.androidarchcomp.mvpexample.network;

import com.androidarchcomp.mvpexample.model.LoginResponseSuccess;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

//    @GET("/api/unknown")
//    Call<MultipleResource> doGetListResources();
//
//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    @FormUrlEncoded
    @POST("/api/login?")
    Call<LoginResponseSuccess> doLoginUserWithField(@Field("email")String email, @Field("password")String password);

}
