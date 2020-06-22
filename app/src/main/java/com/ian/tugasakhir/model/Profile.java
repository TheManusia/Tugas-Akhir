package com.ian.tugasakhir.model;

import lombok.Data;

@Data
public class Profile {
    private String id;
    private String username;
    private String gambar;
    private boolean absen;
    private int hadir;
    private int izin;
    private int alpa;
    private boolean session;
}
