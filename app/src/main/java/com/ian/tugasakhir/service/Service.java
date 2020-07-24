package com.ian.tugasakhir.service;

import com.ian.tugasakhir.model.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {

    @FormUrlEncoded
    @POST("proses_absen.php")
    Call<Response> absen(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("proses_edit.php")
    Call<Response> editProfile(
            @Field("nama") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("newpassword") String newPassword,
            @Field("gambar") String picture
    );

    @FormUrlEncoded
    @POST("proses_login.php")
    Call<Response> login(
            @Field("username") String username,
            @Field("password") String password
    );

}
