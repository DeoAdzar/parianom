package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.parianom.R;
import com.parianom.adapter.TransaksiRVAdapter;
import com.parianom.model.PenjualanModel;

import java.util.ArrayList;
import java.util.List;

public class Transaksi extends AppCompatActivity {
    private RecyclerView rv;
    private List<PenjualanModel> listTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addData();
        rv = (RecyclerView) findViewById(R.id.transaksiRv);

        TransaksiRVAdapter adapter = new TransaksiRVAdapter(Transaksi.this, listTransaksi);
        rv.setLayoutManager(new LinearLayoutManager(Transaksi.this));
        rv.setAdapter(adapter);
    }

    public void addData() {
        listTransaksi = new ArrayList<>();
        listTransaksi.add(new PenjualanModel("Sayur Kol", "16 September 2021",
                "Wungu", "Sidorejo Jl. Lawu No.30 Wungu","Rp. 10.000", "Deo Adzar", "Bu Yuli",
                "Pangan", "Makanan", R.drawable.top, 2));
    }
}