package com.parianom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private LinearLayout pangan, kriya;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_beranda, container, false);

        fragment = (FrameLayout) v.findViewById(R.id.fragment_container);
        pangan = (LinearLayout) v.findViewById(R.id.btnPangan);
        kriya = (LinearLayout) v.findViewById(R.id.btnKriya);

        add();
        rv = (RecyclerView) v.findViewById(R.id.berandaRv);
        BerandaRvAdapter adapter = new BerandaRvAdapter(getContext(), listData);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.setAdapter(adapter);

//
//        pangan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                panganFragment();
//            }
//        });
//
//        kriya.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                kriyaFragment();
//            }
//        });

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

    //    public void panganFragment() {
//        PanganFragment fr = new PanganFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment, fr);
//        transaction.addToBackStack(fr.getClass().getName());
//        transaction.commit();
//    }

//    public void kriyaFragment() {
//        KriyaFragment fr = new KriyaFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment, fr);
//        transaction.addToBackStack(fr.getClass().getName());
//        transaction.commit();
//    }
}