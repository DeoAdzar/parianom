package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parianom.R;
import com.parianom.utils.SessionManager;

public class Masuk extends AppCompatActivity {
    Button btnMasuk;
    TextView daftar, lupaPass;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        sessionManager=new SessionManager(getApplicationContext());
        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Masuk.this, MainActivity.class);
                startActivity(intent);
                finish();
                sessionManager.createSession("id_user");
            }
        });

        daftar = (TextView) findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Masuk.this, Daftar.class);
                startActivity(intent);
            }
        });

        lupaPass = (TextView) findViewById(R.id.lupaSandi);
        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Masuk.this, LupaPassword.class);
                startActivity(intent);
            }
        });
    }
}