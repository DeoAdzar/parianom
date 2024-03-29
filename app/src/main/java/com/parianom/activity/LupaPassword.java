package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.parianom.R;

public class LupaPassword extends AppCompatActivity {
    private ProgressBar loading;
    private Button kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_lupa_password);

        loading = findViewById(R.id.progress_lupa_sandi);
        kirim = findViewById(R.id.btnKirimLpSandi);
    }
}