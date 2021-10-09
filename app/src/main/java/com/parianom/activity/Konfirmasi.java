package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;
import com.parianom.fragment.ProfilFragment;

public class Konfirmasi extends AppCompatActivity {
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        selesai = (Button) findViewById(R.id.btnTungguKonfirm);
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Konfirmasi.this, Toko.class);
                startActivity(intent);
                finish();
            }
        });
    }
}