package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.parianom.R;
import com.parianom.utils.SessionManagerSplash;

public class Splashscreen extends AppCompatActivity {
    SessionManagerSplash sessionManagerSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sessionManagerSplash = new SessionManagerSplash(Splashscreen.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sessionManagerSplash.checkLogin();
                finish();
            }
        }, 2000);
    }
}