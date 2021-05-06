package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ResponseLaporan {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("value")
    private List<Laporan> laporanList;
}
