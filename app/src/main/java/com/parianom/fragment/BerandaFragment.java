package com.parianom.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.PenjualanRvAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PenjualanResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaFragment extends Fragment {

    View v;
    private RecyclerView rvBeranda;
    private RecyclerView.Adapter adBeranda;
    private RecyclerView.LayoutManager lmBeranda;
    private List<PenjualanModel> penjualanModelList = new ArrayList<>();

    private FrameLayout fragment;
    private LinearLayout pangan, kriya, jenisPangan, jenisKriya, empty;
    private ImageView dPangan, dKriya;
    CardView makanan, minuman, camilan, bBPangan, hasilKriya, bBKriya;
    private TextView titlePangan, titleKriya, jnsMakanan, jnsMinuman, jnsCamilan, jnsBBPangan, jnsHasilKriya, jnsBBKriya;
    private ShimmerFrameLayout shimmer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_beranda, container, false);

        fragment = (FrameLayout) v.findViewById(R.id.fragment_container);
        pangan = (LinearLayout) v.findViewById(R.id.btnPangan);
        kriya = (LinearLayout) v.findViewById(R.id.btnKriya);
        jenisPangan = (LinearLayout) v.findViewById(R.id.jenisPangan);
        jenisKriya = (LinearLayout) v.findViewById(R.id.jenisKriya);
        empty = (LinearLayout) v.findViewById(R.id.empty);
        dPangan = (ImageView) v.findViewById(R.id.drawablePangan);
        dKriya = (ImageView) v.findViewById(R.id.drawableKriya);
        titlePangan = (TextView) v.findViewById(R.id.titlePangan);
        titleKriya = (TextView) v.findViewById(R.id.titleKriya);
        makanan = (CardView) v.findViewById(R.id.makanan);
        minuman = (CardView) v.findViewById(R.id.minuman);
        camilan = (CardView) v.findViewById(R.id.camilan);
        bBPangan = (CardView) v.findViewById(R.id.bahanBakuPangan);
        hasilKriya = (CardView) v.findViewById(R.id.hasilKriya);
        bBKriya = (CardView) v.findViewById(R.id.bahanBakuKriya);
        jnsMakanan = (TextView) v.findViewById(R.id.textMakanan);
        jnsMinuman = (TextView) v.findViewById(R.id.textMinuman);
        jnsCamilan = (TextView) v.findViewById(R.id.textCamilan);
        jnsBBPangan = (TextView) v.findViewById(R.id.textBBakuPangan);
        jnsHasilKriya = (TextView) v.findViewById(R.id.textHasilKriya);
        jnsBBKriya = (TextView) v.findViewById(R.id.textBBakuKriya);
        shimmer = (ShimmerFrameLayout) v.findViewById(R.id.shimmerBeranda);
        int nonAktif = ContextCompat.getColor(getContext(), R.color.label_input);
        int aktif = ContextCompat.getColor(getContext(), R.color.primer);
        shimmer.showShimmer(true);
        shimmer.startShimmer();
        shimmer.setVisibility(View.VISIBLE);
        getMakanan();
        // button pangan
        pangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                DrawableCompat.setTint(dPangan.getDrawable(), aktif);
                titlePangan.setTextColor(aktif);
                DrawableCompat.setTint(dKriya.getDrawable(), nonAktif);
                titleKriya.setTextColor(nonAktif);
                jnsMakanan.setTextColor(aktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);

                jenisPangan.setVisibility(View.VISIBLE);
                jenisKriya.setVisibility(View.GONE);
                getMakanan();
            }
        });

        // button kriya
        kriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                DrawableCompat.setTint(dKriya.getDrawable(), aktif);
                titleKriya.setTextColor(aktif);
                DrawableCompat.setTint(dPangan.getDrawable(), nonAktif);
                titlePangan.setTextColor(nonAktif);
                jnsHasilKriya.setTextColor(aktif);
                jnsBBKriya.setTextColor(nonAktif);
                jenisKriya.setVisibility(View.VISIBLE);
                jenisPangan.setVisibility(View.GONE);
                getHasilKriya();
            }
        });

        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsMakanan.setTextColor(aktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
                getMakanan();
            }
        });

        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsMinuman.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
                getMinuman();
            }
        });

        camilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsCamilan.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
                getCamilan();
            }
        });

        jnsBBPangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsBBPangan.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                getBBPangan();
            }
        });

        jnsHasilKriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsHasilKriya.setTextColor(aktif);
                jnsBBKriya.setTextColor(nonAktif);
                getHasilKriya();
            }
        });

        jnsBBKriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBeranda.setVisibility(View.GONE);
                shimmer.startShimmer();
                shimmer.showShimmer(true);
                shimmer.setVisibility(View.VISIBLE);
                jnsBBKriya.setTextColor(aktif);
                jnsHasilKriya.setTextColor(nonAktif);
                getBBKriya();
            }
        });

        rvBeranda = (RecyclerView) v.findViewById(R.id.berandaRv);
        return v;
    }
    public void getMakanan(){

        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Pangan","Makanan");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                        lmBeranda = new GridLayoutManager(getContext(),2);
                        rvBeranda.setLayoutManager(lmBeranda);
                        adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                        rvBeranda.setAdapter(adBeranda);

                        rvBeranda.setVisibility(View.VISIBLE);
                        shimmer.stopShimmer();
                        shimmer.hideShimmer();
                        shimmer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
    public void getMinuman(){
//        rvBeranda.setVisibility(View.GONE);
//        shimmer.startShimmer();
//        shimmer.stopShimmer();
//        shimmer.setVisibility(View.VISIBLE);
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Pangan","Minuman");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getContext(),2);
                    rvBeranda.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                    rvBeranda.setAdapter(adBeranda);
                    rvBeranda.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
    public void getCamilan(){
//        rvBeranda.setVisibility(View.GONE);
//        shimmer.startShimmer();
//        shimmer.stopShimmer();
//        shimmer.setVisibility(View.VISIBLE);
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Pangan","Camilan");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getContext(),2);
                    rvBeranda.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                    rvBeranda.setAdapter(adBeranda);
                    rvBeranda.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
    public void getBBPangan(){
//        rvBeranda.setVisibility(View.GONE);
//        shimmer.startShimmer();
//        shimmer.stopShimmer();
//        shimmer.setVisibility(View.VISIBLE);
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Pangan","Bahan Baku");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getContext(),2);
                    rvBeranda.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                    rvBeranda.setAdapter(adBeranda);
                    rvBeranda.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
    public void getHasilKriya(){
//        rvBeranda.setVisibility(View.GONE);
//        shimmer.startShimmer();
//        shimmer.stopShimmer();
//        shimmer.setVisibility(View.VISIBLE);
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Kriya","Hasil Kriya");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getContext(),2);
                    rvBeranda.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                    rvBeranda.setAdapter(adBeranda);
                    rvBeranda.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
    public void getBBKriya(){
//        rvBeranda.setVisibility(View.GONE);
//        shimmer.startShimmer();
//        shimmer.stopShimmer();
//        shimmer.setVisibility(View.VISIBLE);
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.getProduk("Kriya","Bahan Baku");
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rvBeranda.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    rvBeranda.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getContext(),2);
                    rvBeranda.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getContext(),penjualanModelList);
                    rvBeranda.setAdapter(adBeranda);
                    rvBeranda.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
}