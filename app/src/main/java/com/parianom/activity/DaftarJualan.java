package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parianom.R;
import com.parianom.adapter.DfJualanRVAdapter;
import com.parianom.model.DaftarJualanModel;

import java.util.ArrayList;
import java.util.List;

public class DaftarJualan extends AppCompatActivity {
    private RecyclerView rv;
    private List<DaftarJualanModel> listDfJualan;

    LinearLayout layoutPangan, layoutKriya;
    ImageView pangan, kriya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_jualan);

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
        rv = (RecyclerView) findViewById(R.id.dfJualanRv);

        DfJualanRVAdapter adapter = new DfJualanRVAdapter(DaftarJualan.this, listDfJualan);
        rv.setLayoutManager(new LinearLayoutManager(DaftarJualan.this));
        rv.setAdapter(adapter);

        filter();
    }

    public void addData() {
        listDfJualan = new ArrayList<>();
        listDfJualan.add(new DaftarJualanModel("Sayur Kol", "16 September 2021", "Pangan", "Makanan", "Rp. 10.000", R.drawable.top));
        listDfJualan.add(new DaftarJualanModel("Sayur Kol", "16 September 2021", "Kriya", "Hasil Kriya", "Rp. 10.000", R.drawable.top));
    }

    public void filter() {
        layoutPangan = (LinearLayout) findViewById(R.id.DfJualPangan);
        pangan = (ImageView) findViewById(R.id.filterPanganDfJual);
        layoutPangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DrawableCompat.setTint(pangan.getDrawable(), ContextCompat.getColor(DaftarJualan.this, R.color.primer));
            }
        });
    }
}