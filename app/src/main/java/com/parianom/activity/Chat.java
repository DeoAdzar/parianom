package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parianom.R;

public class Chat extends AppCompatActivity {
    private TextView namaUser, namaProduk, alamatProduk, jumlahBeli, hargaSatuan, hargaTotal;
    private ImageView imgProduk;
    private CardView beli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

        namaUser = findViewById(R.id.namaUserChat);
        namaProduk = findViewById(R.id.namaPrChat);
        alamatProduk = findViewById(R.id.alamatPrChat);
        jumlahBeli = findViewById(R.id.jmlBeliChat);
        hargaSatuan = findViewById(R.id.hargaSatuan);
        hargaTotal = findViewById(R.id.hargaTotalChat);
        imgProduk = findViewById(R.id.imgPrChat);
        beli = findViewById(R.id.btnBeli);

        namaUser.setText(getIntent().getStringExtra("penjual"));
        jumlahBeli.setText(getIntent().getStringExtra("jumlah"));

        int jumlah = Integer.parseInt(jumlahBeli.getText().toString())*Integer.parseInt(getIntent().getStringExtra("harga"));
        hargaTotal.setText(String.valueOf(jumlah));
    }
}