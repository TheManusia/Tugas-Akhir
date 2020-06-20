package com.ian.tugasakhir;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ian.tugasakhir.activity.HomeActivity;
import com.ian.tugasakhir.activity.LoginActivity;
import com.ian.tugasakhir.model.Profile;
import com.ian.tugasakhir.preference.ProfilePreference;

public class MainActivity extends AppCompatActivity {
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProfilePreference preference = new ProfilePreference(getApplicationContext());

        profile = preference.getProfile();

        new Handler().postDelayed(() -> {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.fragment_fade_enter, R.anim.fragment_close_exit);
            if (profile.isSession()) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class), options.toBundle());
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class), options.toBundle());
            }
            finish();
        }, 3000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}