package com.ian.tugasakhir.helper;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Converter {
    public static String bitmapToString(Bitmap gambar) {
        String encodeImage;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            gambar.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            byte[] imageBytes = baos.toByteArray();
            encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            encodeImage = "";
        }
        return encodeImage;
    }
}
