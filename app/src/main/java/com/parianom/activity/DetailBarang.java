package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jaeger.library.StatusBarUtil;
import com.parianom.R;
import com.parianom.adapter.SliderAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 1;
    ImageView imgDetailPr, terverif;
    TextView namaProduk, namaPenjual, hargaProduk, jumlah, alamatPrBeranda, stok, hargaTotalDetail,deskripsi;
    String status, foto_toko, foto_p, foto_p2, foto_p3, foto_p4, foto_p5,harga,id_produk,id_penjual,stok_barang;
    SessionManager sessionManager;
    ViewPager image;
    CircleIndicator indikator;
    ShimmerFrameLayout shimmer;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(DetailBarang.this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        toolbar.setNavigationIcon(R.drawable.ic_back_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shimmer = findViewById(R.id.shimmerDetailBarang);
        layout = findViewById(R.id.layout_detail_barang);
        chat = (Button) findViewById(R.id.btnChat);
//        imgDetailPr = (ImageView) findViewById(R.id.imgDetailPr);
        image = findViewById(R.id.imgDetailPr);
        indikator = findViewById(R.id.indikatorSlide);
        namaProduk = (TextView) findViewById(R.id.namaProduk);
        namaPenjual = (TextView) findViewById(R.id.namaPenjual);
        alamatPrBeranda = (TextView) findViewById(R.id.alamatPrBeranda);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        jumlah = (TextView) findViewById(R.id.jumlah);
        stok = findViewById(R.id.stokPrBeranda);
        deskripsi = findViewById(R.id.deskDetailBarang);
        hargaTotalDetail = findViewById(R.id.hargaTotalDetail);
        terverif = findViewById(R.id.terverif);

        getDetail();

        HashMap<String,String> user = sessionManager.getUserDetails();
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.checkLogin()==1){
                    BaseApiService mApiService = UtilsApi.getApiService();
                    Call<ResponseBody> cek = mApiService.cekRoom(Integer.parseInt(id_penjual), Integer.parseInt(user.get(SessionManager.kunci_id_user)));
                    cek.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                try {
                                    JSONObject jsonResult =new JSONObject(response.body().string());
                                    if (jsonResult.getString("message").equals("exist")){
                                        String id_room = jsonResult.getJSONObject("data").getString("id");
                                        Intent intent = new Intent(DetailBarang.this, Chat.class);
                                        intent.putExtra("id_penjual",id_penjual);
                                        intent.putExtra("id_room",id_room);
                                        intent.putExtra("id_produk",id_produk);
                                        intent.putExtra("penjual", namaPenjual.getText());
                                        intent.putExtra("nama_produk", namaProduk.getText());
                                        intent.putExtra("jumlah", jumlah.getText());
                                        intent.putExtra("harga", harga);
                                        intent.putExtra("alamat", alamatPrBeranda.getText());
                                        intent.putExtra("gambar", foto_p);
                                        intent.putExtra("gambar_toko", foto_toko);
                                        intent.putExtra("status_chat","0");

                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        createRoom();
                                    }
                                }catch (JSONException e ){
                                    e.printStackTrace();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Intent intent = new Intent(DetailBarang.this, Masuk.class);
                    intent.putExtra("site","2");
                    startActivity(intent);
                }
            }
        });



    }

    private void getDetail() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getProdukById(Integer.parseInt(getIntent().getStringExtra("id")));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")) {
                            String id_penjuals=jsonResult.getJSONObject("data").getString("id_penjual");
                            String id_produks=jsonResult.getJSONObject("data").getString("id");
                            String foto_tokos=jsonResult.getJSONObject("data").getString("foto_toko");
                            String namas = jsonResult.getJSONObject("data").getString("nama");
                            String nama_toko = jsonResult.getJSONObject("data").getString("nama_toko");
                            String alamat_toko = jsonResult.getJSONObject("data").getString("alamat");
                            String hargas = jsonResult.getJSONObject("data").getString("harga");
                            String stoks = jsonResult.getJSONObject("data").getString("stok");
                            String images = jsonResult.getJSONObject("data").getString("foto_produk");
                            String images2 = jsonResult.getJSONObject("data").getString("foto_produk2");
                            String images3 = jsonResult.getJSONObject("data").getString("foto_produk3");
                            String images4 = jsonResult.getJSONObject("data").getString("foto_produk4");
                            String images5 = jsonResult.getJSONObject("data").getString("foto_produk5");
                            String deskrip = jsonResult.getJSONObject("data").getString("deskripsi");
                            String status_toko = jsonResult.getJSONObject("data").getString("status_toko");
                            status = status_toko;
                            id_produk = id_produks;
                            id_penjual = id_penjuals;
                            foto_toko = foto_tokos;
                            namaProduk.setText(namas);
                            namaPenjual.setText(nama_toko);
                            alamatPrBeranda.setText(alamat_toko);
                            hargaProduk.setText(hargas);
                            deskripsi.setText(deskrip);
                            stok.setText("stok : "+stoks);
                            stok_barang = stoks;
                            foto_p = images;
                            foto_p2 = images2;
                            foto_p3 = images3;
                            foto_p4 = images4;
                            foto_p5 = images5;

                            List<String> list = new ArrayList<>();
                            if (!foto_p.equals("null")) {
                                list.add(foto_p);
                                if (!foto_p2.equals("null")) {
                                    list.add(foto_p2);
                                }
                                if (!foto_p3.equals("null")) {
                                    list.add(foto_p3);
                                }
                                if (!foto_p4.equals("null")) {
                                    list.add(foto_p4);
                                }
                                if (!foto_p5.equals("null")) {
                                    list.add(foto_p5);
                                }
                            }

                            SliderAdapter adapter = new SliderAdapter(list);
                            image.setAdapter(adapter);

                            if (foto_p2 == "null") {
                                indikator.setVisibility(View.GONE);
                            } else {
                                indikator.setViewPager(image);
                            }


                            harga = hargaProduk.getText().toString();
                            String resultRupiah = formatRupiah(Double.parseDouble(harga));
                            hargaProduk.setText(resultRupiah);
                            if (status.equals("1")){
                                terverif.setVisibility(View.GONE);
                            }else if (status.equals("2")){
                                terverif.setVisibility(View.VISIBLE);
                            }
                            hargaTotalDetail.setText(formatRupiah(Double.parseDouble(hargas)));
                            shimmer.stopShimmer();
                            shimmer.hideShimmer();
                            shimmer.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //tesssss
    private void createRoom() {
        HashMap<String,String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> cek = mApiService.createRoom(Integer.parseInt(id_penjual), Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        cek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult =new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")){
                            String id_room = jsonResult.getString("id");
                            Intent intent = new Intent(DetailBarang.this, Chat.class);
                            intent.putExtra("id_penjual",id_penjual);
                            intent.putExtra("id_room",id_room);
                            intent.putExtra("id_produk",id_produk);
                            intent.putExtra("penjual", namaPenjual.getText());
                            intent.putExtra("nama_produk", namaProduk.getText());
                            intent.putExtra("jumlah", jumlah.getText());
                            intent.putExtra("harga", hargaProduk.getText().toString());
                            intent.putExtra("alamat", alamatPrBeranda.getText());
                            intent.putExtra("gambar", foto_p);
                            intent.putExtra("gambar_toko", foto_toko);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else{
                            createRoom();
                        }
                    }catch (JSONException e ){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void increment(View view){//perintah tombol tambah
        if(quantity==Integer.parseInt(stok_barang)){
            Toast.makeText(this,"pesanan maximal "+stok.getText().toString(),Toast.LENGTH_SHORT).show();
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
        int jumlahHarga = Integer.parseInt(jumlah.getText().toString())*Integer.parseInt(harga);
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