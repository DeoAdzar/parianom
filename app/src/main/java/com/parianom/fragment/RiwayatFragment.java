package com.parianom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.adapter.RiwayatRVAdapter;
import com.parianom.model.PenjualanModel;

import java.util.ArrayList;
import java.util.List;

public class RiwayatFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<PenjualanModel> listRiwayat;
    TextView namaPr, namaPr2, waktuTransaksi, alamat, totalHarga;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_riwayat, container, false);

        rv = (RecyclerView) v.findViewById(R.id.riwayatRv);
        RiwayatRVAdapter adapter = new RiwayatRVAdapter(getContext(), listRiwayat);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRiwayat = new ArrayList<>();
        listRiwayat.add(new PenjualanModel("Sayur Kol", "16 September 2021",
                "Wungu", "Sidorejo Jl. Lawu No.30 Wungu","Rp. 10.000", "Deo Adzar", "Bu Yuli",
                "Pangan", "Makanan", R.drawable.top, 2));
    }
}