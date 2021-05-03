package com.ian.tugasakhir.ui.main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ian.tugasakhir.R;
import com.ian.tugasakhir.ui.home.HomeActivity;
import com.ian.tugasakhir.ui.login.LoginActivity;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.tools.ProfilePreference;

public class MainActivity extends AppCompatActivity {
    Profile profile;
    private static final String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProfilePreference preference = new ProfilePreference(this);

        profile = preference.getProfile();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        new Handler().postDelayed(() -> {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.fragment_fade_enter, R.anim.fragment_close_exit);
            if (profile.isSession()) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra(KEY_ID, profile.getId());
                startActivity(intent, options.toBundle());
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class), options.toBundle());
            }
            finish();
        }, 3000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}