package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Toko extends AppCompatActivity {
    LinearLayout tambah, dfJualan, transaksi, qr, profil;
    TextView namaToko;
    SessionManager sessionManager;
    String id_penjual,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        sessionManager = new SessionManager(getApplicationContext());
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
        namaToko = findViewById(R.id.namaToko);
        id_penjual = getIntent().getStringExtra("id_penjual");
        nama = getIntent().getStringExtra("nama_toko");
        namaToko.setText(nama);
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
                intent.putExtra("id_penjual",id_penjual);
                startActivity(intent);
            }
        });
    }

    public void daftarJualan(){
        dfJualan = (LinearLayout) findViewById(R.id.btnDftrJual);
        dfJualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, DaftarJualan.class);
                intent.putExtra("id_penjual",id_penjual);
                startActivity(intent);
            }
        });
    }

    public void transaksi(){
        transaksi = (LinearLayout) findViewById(R.id.btnTransaksi);
        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, Transaksi.class);
                intent.putExtra("id_penjual",id_penjual);
                startActivity(intent);
            }
        });
    }

    public void qR(){
        qr = (LinearLayout) findViewById(R.id.btnQr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, ScanQr.class);
                intent.putExtra("id_penjual",id_penjual);
                startActivity(intent);
            }
        });
    }

    public void profilToko(){
        profil = (LinearLayout) findViewById(R.id.btnProfilTk);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Toko.this, ProfilToko.class);
                intent.putExtra("id_penjual",id_penjual);
                startActivity(intent);
            }
        });
    }
}