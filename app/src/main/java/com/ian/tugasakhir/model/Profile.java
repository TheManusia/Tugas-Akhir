package com.ian.tugasakhir.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Profile {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("absen")
    private boolean absen;
    @SerializedName("hadir")
    private int hadir;
    @SerializedName("izin")
    private int izin;
    @SerializedName("alpa")
    private int alpa;
    private boolean session;
}
