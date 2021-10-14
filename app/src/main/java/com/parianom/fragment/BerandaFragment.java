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

public class BerandaFragment extends Fragment implements View.OnClickListener {

    View v;
    private RecyclerView rv;
    private List<PenjualanModel> listData;

    private FrameLayout fragment;
    private LinearLayout pangan, kriya;
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
        dPangan = (ImageView) v.findViewById(R.id.drawablePangan);
        dKriya = (ImageView) v.findViewById(R.id.drawableKriya);
        titlePangan = (TextView) v.findViewById(R.id.titlePangan);
        titleKriya = (TextView) v.findViewById(R.id.titleKriya);
        jnsMakanan = (TextView) v.findViewById(R.id.textMakanan);
        jnsMinuman = (TextView) v.findViewById(R.id.textMinuman);
        jnsCamilan = (TextView) v.findViewById(R.id.textCamilan);
        jnsBBPangan = (TextView) v.findViewById(R.id.textBBakuPangan);
        jnsHasilKriya = (TextView) v.findViewById(R.id.textHasilKriya);
        jnsBBKriya = (TextView) v.findViewById(R.id.textBBakuKriya);


//        pangan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DrawableCompat.setTint(dPangan.getDrawable(), ContextCompat.getColor(getContext(), R.color.primer));
//                titlePangan.setTextColor(ContextCompat.getColor(getContext(), R.color.primer));
//            }
//        });
//        kriya.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DrawableCompat.setTint(dKriya.getDrawable(), ContextCompat.getColor(getContext(), R.color.primer));
//                titleKriya.setTextColor(ContextCompat.getColor(getContext(), R.color.primer));
//            }
//        });
        pangan.setOnClickListener(this);
        kriya.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPangan:
                DrawableCompat.setTint(dPangan.getDrawable(), ContextCompat.getColor(getContext(), R.color.primer));
                titlePangan.setTextColor(ContextCompat.getColor(getContext(), R.color.primer));
                DrawableCompat.setTint(dKriya.getDrawable(), ContextCompat.getColor(getContext(), R.color.label_input));
                titleKriya.setTextColor(ContextCompat.getColor(getContext(), R.color.label_input));
                break;
            case R.id.btnKriya:
                DrawableCompat.setTint(dKriya.getDrawable(), ContextCompat.getColor(getContext(), R.color.primer));
                titleKriya.setTextColor(ContextCompat.getColor(getContext(), R.color.primer));
                DrawableCompat.setTint(dPangan.getDrawable(), ContextCompat.getColor(getContext(), R.color.label_input));
                titlePangan.setTextColor(ContextCompat.getColor(getContext(), R.color.label_input));
                break;
        }
    }
}