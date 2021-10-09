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
import com.parianom.model.PesanModel;

import java.util.ArrayList;
import java.util.List;

public class PesanFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<PesanModel> listPesan;

    public PesanFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesan, container, false);

        rv = (RecyclerView) v.findViewById(R.id.pesanRv);
        PesanRVAdapter adapter = new PesanRVAdapter(getContext(), listPesan);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listPesan = new ArrayList<>();
        listPesan.add(new PesanModel("Bu Yuni", "Oke", R.drawable.ic_person));
        listPesan.add(new PesanModel("Bu Yono", "Terimakasih", R.drawable.ic_person));
    }
}