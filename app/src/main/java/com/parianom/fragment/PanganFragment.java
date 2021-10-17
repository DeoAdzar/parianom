package com.parianom.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parianom.R;
import com.parianom.adapter.PenjualanRvAdapter;
import com.parianom.model.PenjualanModel;

import java.util.List;

public class PanganFragment extends Fragment {
    View v;
    RecyclerView rv;
    List<PenjualanModel> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pangan, container, false);

        rv = (RecyclerView) v.findViewById(R.id.panganRv);
        PenjualanRvAdapter adapter = new PenjualanRvAdapter(getContext(), mData);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return v;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mData = new ArrayList<>();
//
//        mData.add(new PenjualanModel(R.drawable.top, "Jamur Blothong", "Wungu", "Rp. 10.000"));
//    }

}