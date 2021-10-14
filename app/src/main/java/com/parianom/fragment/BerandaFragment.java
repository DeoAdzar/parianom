package com.parianom.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.BerandaRvAdapter;
import com.parianom.adapter.RiwayatRVAdapter;
import com.parianom.model.PenjualanModel;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment {

    View v;
    private RecyclerView rv;
    private List<PenjualanModel> listData;

    private FrameLayout fragment;
    private LinearLayout pangan, kriya, jenisPangan, jenisKriya;
    private ImageView dPangan, dKriya;
    CardView makanan, minuman, camilan, bBPangan, hasilKriya, bBKriya;
    private TextView titlePangan, titleKriya, jnsMakanan, jnsMinuman, jnsCamilan, jnsBBPangan, jnsHasilKriya, jnsBBKriya;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_beranda, container, false);

        fragment = (FrameLayout) v.findViewById(R.id.fragment_container);
        pangan = (LinearLayout) v.findViewById(R.id.btnPangan);
        kriya = (LinearLayout) v.findViewById(R.id.btnKriya);
        jenisPangan = (LinearLayout) v.findViewById(R.id.jenisPangan);
        jenisKriya = (LinearLayout) v.findViewById(R.id.jenisKriya);
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

        int nonAktif = ContextCompat.getColor(getContext(), R.color.label_input);
        int aktif = ContextCompat.getColor(getContext(), R.color.primer);

        // button pangan
        pangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        // button kriya
        kriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawableCompat.setTint(dKriya.getDrawable(), aktif);
                titleKriya.setTextColor(aktif);
                DrawableCompat.setTint(dPangan.getDrawable(), nonAktif);
                titlePangan.setTextColor(nonAktif);
                jnsHasilKriya.setTextColor(aktif);
                jnsBBKriya.setTextColor(nonAktif);

                jenisKriya.setVisibility(View.VISIBLE);
                jenisPangan.setVisibility(View.GONE);
            }
        });

        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsMakanan.setTextColor(aktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
            }
        });

        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsMinuman.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
            }
        });

        camilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsCamilan.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsBBPangan.setTextColor(nonAktif);
            }
        });

        jnsBBPangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsBBPangan.setTextColor(aktif);
                jnsMakanan.setTextColor(nonAktif);
                jnsMinuman.setTextColor(nonAktif);
                jnsCamilan.setTextColor(nonAktif);
            }
        });

        jnsHasilKriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsHasilKriya.setTextColor(aktif);
                jnsBBKriya.setTextColor(nonAktif);
            }
        });

        jnsBBKriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jnsBBKriya.setTextColor(aktif);
                jnsHasilKriya.setTextColor(nonAktif);
            }
        });

        add();
        rv = (RecyclerView) v.findViewById(R.id.berandaRv);
        BerandaRvAdapter adapter = new BerandaRvAdapter(getContext(), listData);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.setAdapter(adapter);

        return v;
    }

    public void add() {
        listData = new ArrayList<>();
        listData.add(new PenjualanModel("Sayur Kol", "16 September 2021",
                "Wungu", "Sidorejo Jl. Lawu No.30 Wungu","Rp. 10.000", "Deo Adzar", "Bu Yuli",
                "Pangan", "Makanan", R.drawable.gb_board, 2));
        listData.add(new PenjualanModel("Sayur Kol", "16 September 2021",
                "Wungu", "Sidorejo Jl. Lawu No.30 Wungu","Rp. 10.000", "Deo Adzar", "Bu Yuli",
                "Pangan", "Makanan", R.drawable.top, 2));
    }
}