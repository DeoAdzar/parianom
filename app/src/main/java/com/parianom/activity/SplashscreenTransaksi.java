package com.parianom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.parianom.R;
import com.parianom.utils.SessionManagerSplash;

public class SplashscreenTransaksi extends AppCompatActivity {
    SessionManagerSplash sessionManagerSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen_transaksi);
        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sessionManagerSplash = new SessionManagerSplash(SplashscreenTransaksi.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashscreenTransaksi.this, Transaksi.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}