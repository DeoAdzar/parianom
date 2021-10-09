package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parianom.R;

public class Daftar extends AppCompatActivity {
    Button btnDaftar;
    TextView masuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Daftar.this, Otp.class);
                startActivity(intent);
            }
        });

        masuk = (TextView) findViewById(R.id.masuk);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Daftar.this, Masuk.class);
                startActivity(intent);
            }
        });
    }
}