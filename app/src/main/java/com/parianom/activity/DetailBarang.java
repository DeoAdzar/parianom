package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.parianom.R;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 1;
    ImageView imgDetailPr;
    TextView namaProduk, namaPenjual, hargaProduk, jumlah, alamatPrBeranda, stok, hargaTotalDetail;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setStatusBarColor(ContextCompat.getColor(DetailBarang.this, R.color.white));
        setContentView(R.layout.activity_detail_barang);
        sessionManager = new SessionManager(getApplicationContext());
//        StatusBarUtil.setTransparent(DetailBarang.this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

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

        chat = (Button) findViewById(R.id.btnChat);
        imgDetailPr = (ImageView) findViewById(R.id.imgDetailPr);
        namaProduk = (TextView) findViewById(R.id.namaProduk);
        namaPenjual = (TextView) findViewById(R.id.namaPenjual);
        alamatPrBeranda = (TextView) findViewById(R.id.alamatPrBeranda);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        jumlah = (TextView) findViewById(R.id.jumlah);
        stok = findViewById(R.id.stokPrBeranda);
        hargaTotalDetail = findViewById(R.id.hargaTotalDetail);
        namaProduk.setText(getIntent().getStringExtra("nama_produk"));
        namaPenjual.setText(getIntent().getStringExtra("nama"));
        alamatPrBeranda.setText(getIntent().getStringExtra("alamat"));
        hargaProduk.setText(getIntent().getStringExtra("harga_produk"));
        stok.setText("stok : "+getIntent().getStringExtra("stok"));
        Picasso.get().load(UtilsApi.IMAGES_PRODUK + getIntent().getStringExtra("foto_profil"))
                .placeholder(R.drawable.ic_person)
                .into(imgDetailPr);
        String harga = hargaProduk.getText().toString();
        String resultRupiah = formatRupiah(Double.parseDouble(harga));
        hargaProduk.setText(resultRupiah);

        String hargaTotalDetailRp = formatRupiah(Double.parseDouble(harga));
        hargaTotalDetail.setText(hargaTotalDetailRp);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.checkLogin()==1){
                    Intent intent = new Intent(DetailBarang.this, Chat.class);
                    intent.putExtra("id_penjual",getIntent().getStringExtra("id_penjual"));
                    intent.putExtra("id_produk",getIntent().getStringExtra("id"));
                    intent.putExtra("penjual", namaPenjual.getText());
                    intent.putExtra("nama_produk", namaProduk.getText());
                    intent.putExtra("jumlah", jumlah.getText());
                    intent.putExtra("harga", harga);
                    intent.putExtra("alamat", alamatPrBeranda.getText());
                    intent.putExtra("gambar", getIntent().getStringExtra("foto_profil"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(DetailBarang.this, Masuk.class);
                        intent.putExtra("site","2");
                    startActivity(intent);
                }
            }
        });

    }

    public void increment(View view){//perintah tombol tambah
        if(quantity==Integer.parseInt(getIntent().getStringExtra("stok"))){
            Toast.makeText(this,"pesanan maximal "+getIntent().getStringExtra("stok"),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1 ;
        display(quantity);
    }
    public void decrement(View view){//perintah tombol tambah
        if (quantity==1){
            Toast.makeText(this,"Minimal 1 pesanan",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }

    private void display(int number) {
        jumlah.setText("" + number);
        int jumlahHarga = Integer.parseInt(jumlah.getText().toString())*Integer.parseInt(getIntent().getStringExtra("harga_produk"));
        String total = formatRupiah(Double.parseDouble(String.valueOf(jumlahHarga)));
        hargaTotalDetail.setText(String.valueOf(total));
    }

    //  Currency
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}