package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransaksi extends AppCompatActivity {
    ImageView img;
    TextView namaPr,namaPn,alamatPr,hargaPr,jumlahBeli,tglPesanan,kategoriSub,kode_pesanan;
    String kode;
    SimpleDateFormat sdf;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

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
        sessionManager = new SessionManager(getApplicationContext());
        kode = getIntent().getStringExtra("kode_pesanan");
        sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        img = findViewById(R.id.imgDetailTr);
        namaPr = findViewById(R.id.namaProdukTr);
        namaPn = findViewById(R.id.namaPenjualProduk);
        alamatPr = findViewById(R.id.almProdukTr);
        hargaPr = findViewById(R.id.hargaProdukTr);
        jumlahBeli = findViewById(R.id.jumlahBeli);
        tglPesanan = findViewById(R.id.tanggalTr);
        kategoriSub = findViewById(R.id.kategoriTr);
        kode_pesanan = findViewById(R.id.noPesanan);
        kode_pesanan.setText("#"+kode);
        getData();
    }

    private void getData() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getDetailPesananUser(kode,Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")){
                            String namaProduk= jsonResult.getJSONObject("data").getString("nama");
                            String namaPenjual= jsonResult.getJSONObject("data").getString("nama_toko");
                            String alamatPenjual= jsonResult.getJSONObject("data").getString("alamat");
                            String kecPenjual= jsonResult.getJSONObject("data").getString("kec");
                            String hargaTotal= jsonResult.getJSONObject("data").getString("total");
                            String jumlah= jsonResult.getJSONObject("data").getString("jumlah");
                            String gambar = jsonResult.getJSONObject("data").getString("foto_produk");
                            String tanggal= jsonResult.getJSONObject("data").getString("timestamp");
                            String kategori= jsonResult.getJSONObject("data").getString("kategori_sub");
                            namaPr.setText(namaProduk);
                            namaPn.setText(namaPenjual);
                            alamatPr.setText(alamatPenjual+" Kec. "+kecPenjual);
                            hargaPr.setText(hargaTotal);
                            String harga = hargaPr.getText().toString();
                            String resultRupiah = formatRupiah(Double.parseDouble(harga));
                            hargaPr.setText(resultRupiah);
                            jumlahBeli.setText(jumlah);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = null;
                            String convertedDate = null;
                            try {
                                date = dateFormat.parse(tanggal);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                                convertedDate = simpleDateFormat.format(date);
                                tglPesanan.setText(convertedDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            kategoriSub.setText(kategori);
                            Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK+gambar))
                            .placeholder(R.color.shimmer).into(img);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}