package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Laporan {
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("type")
    private String type;
}
