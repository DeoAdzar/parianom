package com.parianom.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parianom.R;
import com.parianom.adapter.SubPanganRVAdapter;
import com.parianom.model.SubPanganModel;

import java.util.ArrayList;
import java.util.List;

public class PanganFragment extends Fragment {

    View v;
    private RecyclerView rv;
    private List<SubPanganModel> mSubmenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pangan, container, false);

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

        mSubmenu.add(new SubPanganModel(R.drawable.ic_makanan, "Makanan"));
        mSubmenu.add(new SubPanganModel(R.drawable.ic_minuman, "Minuman"));
        mSubmenu.add(new SubPanganModel(R.drawable.ic_camilan, "Camilan"));
        mSubmenu.add(new SubPanganModel(R.drawable.ic_bahan_pangan, "Bahan Baku"));
    }
}