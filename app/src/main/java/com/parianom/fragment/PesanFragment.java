package com.parianom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.PesanRVAdapter;
import com.parianom.model.PesanModel;

import java.util.ArrayList;
import java.util.List;

public class PesanFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<PesanModel> listPesan;
    ShimmerFrameLayout shimmer;

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

        shimmer = (ShimmerFrameLayout) v.findViewById(R.id.shimmerPesan);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rv.setVisibility(View.VISIBLE);
                shimmer.stopShimmer();
                shimmer.hideShimmer();
                shimmer.setVisibility(View.GONE);
            }
        }, 5000);

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