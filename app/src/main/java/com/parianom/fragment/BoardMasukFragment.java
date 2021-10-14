package com.parianom.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parianom.R;
import com.parianom.activity.MainActivity;
import com.parianom.activity.Masuk;

public class BoardMasukFragment extends Fragment {
    View v;
    Button masuk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_board_masuk, container, false);

        masuk = (Button) v.findViewById(R.id.btnBoardMasuk);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Masuk.class);
                startActivity(intent);
            }
        });

        return v;
    }
}