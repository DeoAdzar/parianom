package com.parianom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.adapter.PesanRVAdapter;
import com.parianom.adapter.RiwayatRVAdapter;
import com.parianom.model.PesanModel;
import com.parianom.model.RiwayatModel;

import java.util.ArrayList;
import java.util.List;

public class RiwayatFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<RiwayatModel> listRiwayat;

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
        listRiwayat.add(new RiwayatModel("Sayur Kol", "16 September 2021", "Sayur Kol", "Wungu", "Rp. 10.000", R.drawable.ic_person, 1));
        listRiwayat.add(new RiwayatModel("Sayur Kol", "16 September 2021", "Sayur Kol", "Wungu", "Rp. 10.000", R.drawable.ic_person, 1));
    }
}