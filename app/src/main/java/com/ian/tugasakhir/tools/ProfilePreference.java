package com.ian.tugasakhir.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.ian.tugasakhir.data.Profile;

public class ProfilePreference {
    private static final String KEY_PREF_NAME = "PREFNAME";

    private static final String KEY_ID = "ID";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_GAMBAR = "GAMBAR";
    private static final String KEY_ABSEN = "ABSEN";
    private static final String KEY_HADIR = "HADIR";
    private static final String KEY_IZIN = "IZIN";
    private static final String KEY_ALPA = "ALPA";
    private static final String KEY_SESSION = "SESSION";

    private final SharedPreferences sharedPreferences;

    public ProfilePreference(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setProfile(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, profile.getUsername());
        editor.putString(KEY_USERNAME, profile.getName());
        editor.putString(KEY_GAMBAR, profile.getGambar());
        editor.putBoolean(KEY_ABSEN, profile.isAbsen());
        editor.putInt(KEY_HADIR, profile.getHadir());
        editor.putInt(KEY_IZIN, profile.getIzin());
        editor.putInt(KEY_ALPA, profile.getAlpa());
        editor.putBoolean(KEY_SESSION, profile.isSession());
        editor.apply();
    }

    public Profile getProfile() {
        Profile profile = new Profile();
        profile.setUsername(sharedPreferences.getString(KEY_ID, ""));
        profile.setName(sharedPreferences.getString(KEY_USERNAME, ""));
        profile.setGambar(sharedPreferences.getString(KEY_GAMBAR, ""));
        profile.setAbsen(sharedPreferences.getBoolean(KEY_ABSEN, false));
        profile.setHadir(sharedPreferences.getInt(KEY_HADIR, 0));
        profile.setIzin(sharedPreferences.getInt(KEY_IZIN, 0));
        profile.setAlpa(sharedPreferences.getInt(KEY_ALPA, 0));
        profile.setSession(sharedPreferences.getBoolean(KEY_SESSION, false));

        return profile;
    }
}
