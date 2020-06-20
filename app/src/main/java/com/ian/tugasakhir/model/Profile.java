package com.ian.tugasakhir.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Profile {
    String id;
    String username;
    String gambar;
    boolean absen;
    int hadir;
    int izin;
    int alpa;
}
