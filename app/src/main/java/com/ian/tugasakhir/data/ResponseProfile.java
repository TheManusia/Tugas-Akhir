package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseProfile {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("value")
    private Profile profile;
}
