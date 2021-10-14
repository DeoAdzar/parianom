package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parianom.R;
import com.parianom.utils.SessionManager;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 1;
    ImageView imgDetailPr;
    TextView namaProduk, namaPenjual, hargaProduk, jumlah, alamat;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        sessionManager = new SessionManager(getApplicationContext());

        chat = (Button) findViewById(R.id.btnChat);
        imgDetailPr = (ImageView) findViewById(R.id.imgDetailPr);
        namaProduk = (TextView) findViewById(R.id.namaProduk);
        namaPenjual = (TextView) findViewById(R.id.namaPenjual);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        jumlah = (TextView) findViewById(R.id.jumlah);

        namaProduk.setText(getIntent().getStringExtra("namaProduk"));
        namaPenjual.setText(getIntent().getStringExtra("namaPrBeranda"));
        alamat.setText(getIntent().getStringExtra("alamat"));
        hargaProduk.setText(getIntent().getStringExtra("hargaPrBeranda"));
//        jumlah.setText(getIntent().getStringExtra("namaPrBeranda"));

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.checkLogin()==1){
                    Intent intent = new Intent(DetailBarang.this, Chat.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DetailBarang.this, Masuk.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void increment(View view) {
    }

    private void decrement() {
    }


}