package com.parianom.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.DfJualanRVAdapter;
import com.parianom.adapter.PenjualanRvAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.DaftarJualanModel;
import com.parianom.model.DaftarJualanResponseModel;
import com.parianom.model.PenjualanModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarJualan extends AppCompatActivity {
    private RecyclerView rvDaftarJualan;
    private RecyclerView.Adapter adDaftarJualan;
    private RecyclerView.LayoutManager lmDaftarJualan;
    private List<DaftarJualanModel> daftarJualanModels = new ArrayList<>();

    LinearLayout layoutPangan, layoutKriya;
    private ImageView pangan, kriya;
    private TextView textPangan, textKriya;
    private ShimmerFrameLayout shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_jualan);

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
        rvDaftarJualan = (RecyclerView) findViewById(R.id.dfJualanRv);
        shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmerDfJual);

        filter();
    }

    public void filter() {
        layoutPangan = (LinearLayout) findViewById(R.id.DfJualPangan);
        layoutKriya = (LinearLayout) findViewById(R.id.DfJualKriya);
        pangan = (ImageView) findViewById(R.id.filterPanganDfJual);
        kriya = (ImageView) findViewById(R.id.filterKriyaDfJual);
        textPangan = findViewById(R.id.textPanganDfJual);
        textKriya = findViewById(R.id.textKriyaDfJual);
        int nonAktif = ContextCompat.getColor(DaftarJualan.this, R.color.label_input);
        int aktif = ContextCompat.getColor(DaftarJualan.this, R.color.primer);
        getPangan();
        DrawableCompat.setTint(pangan.getDrawable(), aktif);
        DrawableCompat.setTint(kriya.getDrawable(), nonAktif);
        textPangan.setTextColor(aktif);
        textKriya.setTextColor(nonAktif);

        layoutPangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawableCompat.setTint(pangan.getDrawable(), aktif);
                textPangan.setTextColor(aktif);
                DrawableCompat.setTint(kriya.getDrawable(), nonAktif);
                textKriya.setTextColor(nonAktif);
                getPangan();
            }
        });
        layoutKriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawableCompat.setTint(kriya.getDrawable(), aktif);
                textKriya.setTextColor(aktif);
                DrawableCompat.setTint(pangan.getDrawable(), nonAktif);
                textPangan.setTextColor(nonAktif);
                getKriya();
            }
        });
    }
    public void getPangan(){
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<DaftarJualanResponseModel> get = mApiService.getProdukPenjual(Integer.parseInt(getIntent().getStringExtra("id_penjual")),"Pangan");
        get.enqueue(new Callback<DaftarJualanResponseModel>() {
            @Override
            public void onResponse(Call<DaftarJualanResponseModel> call, Response<DaftarJualanResponseModel> response) {
                daftarJualanModels=response.body().getData();
                lmDaftarJualan = new LinearLayoutManager(getApplicationContext());
                rvDaftarJualan.setLayoutManager(lmDaftarJualan);
                adDaftarJualan = new DfJualanRVAdapter(getApplicationContext(),daftarJualanModels);
                rvDaftarJualan.setAdapter(adDaftarJualan);
                rvDaftarJualan.setVisibility(View.VISIBLE);
                shimmer.stopShimmer();
                shimmer.hideShimmer();
                shimmer.setVisibility(View.GONE);
                adDaftarJualan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DaftarJualanResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getKriya(){
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<DaftarJualanResponseModel> get = mApiService.getProdukPenjual(Integer.parseInt(getIntent().getStringExtra("id_penjual")),"Kriya");
        get.enqueue(new Callback<DaftarJualanResponseModel>() {
            @Override
            public void onResponse(Call<DaftarJualanResponseModel> call, Response<DaftarJualanResponseModel> response) {
                daftarJualanModels=response.body().getData();
                lmDaftarJualan = new LinearLayoutManager(getApplicationContext());
                rvDaftarJualan.setLayoutManager(lmDaftarJualan);
                adDaftarJualan = new DfJualanRVAdapter(getApplicationContext(),daftarJualanModels);
                rvDaftarJualan.setAdapter(adDaftarJualan);
                adDaftarJualan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DaftarJualanResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        filter();
    }
}