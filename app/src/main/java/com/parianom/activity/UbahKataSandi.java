package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parianom.R;

public class UbahKataSandi extends AppCompatActivity {
    EditText lama, baru, konfirmasi;
    TextView lupaSandi;
    Button simpanSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kata_sandi);

        lama = findViewById(R.id.kataSandiLama);
        baru = findViewById(R.id.kataSandiBaru);
        konfirmasi = findViewById(R.id.konfKataSandiBaru);
        lupaSandi = findViewById(R.id.lupaKataSandi);
        simpanSandi = findViewById(R.id.btnSimpanSandi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}