package com.ian.tugasakhir.data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Response {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private int success;
}
