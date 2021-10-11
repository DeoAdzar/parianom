package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.parianom.R;

public class Toko extends AppCompatActivity {
    LinearLayout tambah, dfJualan, transaksi, qr, profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

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

        tambah();
        daftarJualan();
        transaksi();
        qR();
        profilToko();
    }

    public void tambah(){
        tambah = (LinearLayout) findViewById(R.id.btnTambahPr);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, TambahProduk.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void daftarJualan(){
        dfJualan = (LinearLayout) findViewById(R.id.btnDftrJual);
        dfJualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, DaftarJualan.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void transaksi(){
        transaksi = (LinearLayout) findViewById(R.id.btnTransaksi);
        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, Transaksi.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void qR(){
        qr = (LinearLayout) findViewById(R.id.btnQr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, ScanQr.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void profilToko(){
        profil = (LinearLayout) findViewById(R.id.btnProfilTk);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, ProfilToko.class);
                startActivity(intent);
                finish();
            }
        });
    }
}