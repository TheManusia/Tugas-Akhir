package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Laporan {
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("type")
    String type;
}
