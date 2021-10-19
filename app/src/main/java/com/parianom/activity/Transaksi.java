package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.adapter.TransaksiRVAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.TransaksiModel;
import com.parianom.model.TransaksiResponseModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaksi extends AppCompatActivity {
    private RecyclerView rv;
    private List<TransaksiModel> listTransaksi;

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

        getData();
        rv = (RecyclerView) findViewById(R.id.transaksiRv);


    }

    private void getData() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<TransaksiResponseModel> get = mApiService.getPesananPenjual(Integer.parseInt(getIntent().getStringExtra("id_penjual")));
        get.enqueue(new Callback<TransaksiResponseModel>() {
            @Override
            public void onResponse(Call<TransaksiResponseModel> call, Response<TransaksiResponseModel> response) {
                listTransaksi = response.body().getData();
                TransaksiRVAdapter adapter = new TransaksiRVAdapter(Transaksi.this, listTransaksi);
                rv.setLayoutManager(new LinearLayoutManager(Transaksi.this));
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TransaksiResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}