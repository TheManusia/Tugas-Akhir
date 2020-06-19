package com.ian.tugasakhir;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ian.tugasakhir.activity.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.fragment_fade_enter, R.anim.fragment_close_exit);
                startActivity(new Intent(MainActivity.this, HomeActivity.class), options.toBundle());
                finish();
            }
        }, 3000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}