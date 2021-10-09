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
import com.parianom.adapter.SubPanganRVAdapter;
import com.parianom.model.SubPanganModel;

import java.util.ArrayList;
import java.util.List;

public class KriyaFragment extends Fragment {

    View v;
    private RecyclerView rv;
    private List<SubPanganModel> mSubmenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kriya, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rv = (RecyclerView) v.findViewById(R.id.subMenuRV);
        SubPanganRVAdapter adapter = new SubPanganRVAdapter(getContext(), mSubmenu);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSubmenu = new ArrayList<>();

        mSubmenu.add(new SubPanganModel(R.drawable.ic_hasil_kriya, "Hasil Kriya"));
        mSubmenu.add(new SubPanganModel(R.drawable.ic_bahan_kriya, "Bahan Baku"));
    }
}