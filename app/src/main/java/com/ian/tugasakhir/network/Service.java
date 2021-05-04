package com.ian.tugasakhir.network;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.data.Response;

import java.util.List;

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

    @FormUrlEncoded
    @POST("get_laporan.php")
    Call<List<Laporan>> getLaporan(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("get_profile.php")
    Call<Profile> getProfile(
            @Field("username") String username
    );

}
