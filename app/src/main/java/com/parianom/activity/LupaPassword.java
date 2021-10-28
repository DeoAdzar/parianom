package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.parianom.R;

public class LupaPassword extends AppCompatActivity {
    private ProgressBar loading;
    private Button kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        loading = findViewById(R.id.progress_lupa_sandi);
        kirim = findViewById(R.id.btnKirimLpSandi);
    }
}