package com.parianom.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    private TextView namaPenjual, namaProduk, alamatProduk, jumlahBeli, hargaSatuan, hargaTotal;
    private ImageView imgProduk;
    private CardView beli;
    SessionManager sessionManager;
    Calendar calendar;
    SimpleDateFormat sdf,sdf2;
    String harga,namaP,jumlah,namaPr,alamat,gambar,idPr,idPn;
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
        harga = getIntent().getStringExtra("harga");
        namaP=getIntent().getStringExtra("penjual");
        jumlah = getIntent().getStringExtra("jumlah");
        namaPr=getIntent().getStringExtra("nama_produk");
        alamat = getIntent().getStringExtra("alamat");
        gambar = getIntent().getStringExtra("gambar");
        idPn = getIntent().getStringExtra("id_penjual");
        idPr= getIntent().getStringExtra("id_produk");
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sessionManager.getUserDetails();
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyMMdd");
        sdf2 = new SimpleDateFormat("HHmm");
        namaPenjual = findViewById(R.id.namaPenjualChat);
        namaProduk = findViewById(R.id.namaPrChat);
        alamatProduk = findViewById(R.id.alamatPrChat);
        jumlahBeli = findViewById(R.id.jmlBeliChat);
        hargaSatuan = findViewById(R.id.hargaSatuan);
        hargaTotal = findViewById(R.id.hargaTotalChat);
        imgProduk = findViewById(R.id.imgPrChat);
        beli = findViewById(R.id.btnBeli);
        hargaSatuan.setText(harga);
        namaPenjual.setText(namaP);
        jumlahBeli.setText(jumlah);
        namaProduk.setText(namaPr);
        alamatProduk.setText(alamat);
        Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK+gambar))
        .placeholder(R.color.shimmer).into(imgProduk);
        int jumlah = Integer.parseInt(jumlahBeli.getText().toString())*Integer.parseInt(harga);
        hargaTotal.setText(String.valueOf(jumlah));
        String kode_pesanan = sdf.format(calendar.getTime())+ sdf2.format(calendar.getTime())+idPr+user.get(SessionManager.kunci_id_user);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> input = mApiService.inputPesanan(
                        Integer.parseInt(idPr),
                        Integer.parseInt(user.get(SessionManager.kunci_id_user)),
                        Integer.parseInt(idPn),
                        Integer.parseInt(jumlahBeli.getText().toString()),
                        kode_pesanan,
                        Integer.parseInt(hargaTotal.getText().toString()));
                input.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i = new Intent(Chat.this,GenerateQR.class);
                        i.putExtra("kode_pesanan",kode_pesanan);
                        startActivity(i);
                        Log.d(TAG, "onResponseParianom: "
                                +idPr
                                +idPn
                                +jumlahBeli.getText().toString()+kode_pesanan+hargaTotal.getText().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Chat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}