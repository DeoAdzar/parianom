package com.parianom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parianom.R;
import com.parianom.activity.BukaToko;
import com.parianom.activity.EditProfil;
import com.parianom.activity.PusatBantuan;
import com.parianom.activity.TentangKami;
import com.parianom.activity.Toko;
import com.parianom.utils.SessionManager;

public class ProfilFragment extends Fragment {
    View v;
    LinearLayout toko, profil, bantuan, ttg, keluar;
    TextView namaUser, email;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profil, container, false);

        toko = (LinearLayout) v.findViewById(R.id.btnTokoSaya);
        profil = (LinearLayout) v.findViewById(R.id.btnProfil);
        bantuan = (LinearLayout) v.findViewById(R.id.btnBantuan);
        ttg = (LinearLayout) v.findViewById(R.id.btnTtgKami);
        keluar = (LinearLayout) v.findViewById(R.id.btnKeluar);
        sessionManager = new SessionManager(getContext());
        namaUser = (TextView) v.findViewById(R.id.namaUser);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
        toko();
        profil();
        pusatBantuan();
        tentangKami();
        return v;
    }

    public void toko() {
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaUser.getText().toString() == "Yuli Lestari") {
                    Intent intent = new Intent(getContext(), Toko.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), BukaToko.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void profil() {
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfil.class);
                startActivity(intent);
            }
        });
    }

    private void pusatBantuan() {
        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PusatBantuan.class);
                startActivity(intent);
            }
        });
    }

    private void tentangKami() {
        ttg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TentangKami.class);
                startActivity(intent);
            }
        });
    }
}