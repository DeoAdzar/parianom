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

import com.parianom.R;

public class BerandaFragment extends Fragment {

    View v;
    private FrameLayout fragment;
    private LinearLayout pangan, kriya;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_beranda, container, false);

        fragment = (FrameLayout) v.findViewById(R.id.fragment_container);
        pangan = (LinearLayout) v.findViewById(R.id.btnPangan);
        kriya = (LinearLayout) v.findViewById(R.id.btnKriya);
//
        pangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panganFragment();
            }
        });

        kriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kriyaFragment();
            }
        });

        return v;
    }

    public void panganFragment() {
        PanganFragment fr = new PanganFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fr);
        transaction.addToBackStack(fr.getClass().getName());
        transaction.commit();
    }

    public void kriyaFragment() {
        KriyaFragment fr = new KriyaFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fr);
        transaction.addToBackStack(fr.getClass().getName());
        transaction.commit();
    }
}