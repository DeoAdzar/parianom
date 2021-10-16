package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.utils.SessionManager;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 0;
    ImageView imgDetailPr;
    TextView namaProduk, namaPenjual, hargaProduk, jumlah, alamatPrBeranda;
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
        alamatPrBeranda = (TextView) findViewById(R.id.alamatPrBeranda);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        jumlah = (TextView) findViewById(R.id.jumlah);

        namaProduk.setText(getIntent().getStringExtra("namaPrBeranda"));
        namaPenjual.setText(getIntent().getStringExtra("penjualPr"));
        alamatPrBeranda.setText(getIntent().getStringExtra("alamat"));
        hargaProduk.setText(getIntent().getStringExtra("hargaPrBeranda"));

//        jumlah.setText(getIntent().getStringExtra("namaPrBeranda"));

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.checkLogin()==1){
                    Intent intent = new Intent(DetailBarang.this, Chat.class);
                    intent.putExtra("pembeli", namaPenjual.getText());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DetailBarang.this, Masuk.class);
                    intent.putExtra("site","2");
                    startActivity(intent);
                }
            }
        });

    }

    public void increment(View view){//perintah tombol tambah
        if(quantity==100){
            Toast.makeText(this,"pesanan maximal 100",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1 ;
        display(quantity);
    }
    public void decrement(View view){//perintah tombol tambah
        if (quantity==1){
            Toast.makeText(this,"pesanan minimal 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }
//    private int calculateprice(){//jumlah pesanan * harga
//        String hargaTotal = hargaProduk.getText().toString();
//
//        return quantity * harga;
//    }
    private void display(int number) {
        jumlah.setText("" + number);
    }
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
}