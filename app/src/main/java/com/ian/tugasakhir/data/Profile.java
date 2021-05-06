package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Profile {
    @SerializedName("username")
    private String username;
    @SerializedName("nama")
    private String name;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("status")
    private boolean absen;
    @SerializedName("hadir")
    private int hadir;
    @SerializedName("izin")
    private int izin;
    @SerializedName("alpa")
    private int alpa;
    private boolean session;
}
