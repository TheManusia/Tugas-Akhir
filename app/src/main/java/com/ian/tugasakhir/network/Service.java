package com.ian.tugasakhir.network;

import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.ResponseLaporan;
import com.ian.tugasakhir.data.ResponseProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("absen")
    Call<Response> absen(
            @Query("username") String username
    );

    @FormUrlEncoded
    @POST("user/edit")
    Call<Response> editProfile(
            @Field("nama") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("newpassword") String newPassword,
            @Field("gambar") String picture
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<Response> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("laporan")
    Call<ResponseLaporan> getLaporan(
            @Query("username") String username
    );

    @GET("uservw")
    Call<ResponseProfile> getProfile(
            @Query("username") String username
    );

}
