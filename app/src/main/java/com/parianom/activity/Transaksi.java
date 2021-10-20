package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.adapter.DfJualanRVAdapter;
import com.parianom.adapter.TransaksiRVAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.DaftarJualanModel;
import com.parianom.model.PenjualanModel;
import com.parianom.model.TransaksiModel;
import com.parianom.model.TransaksiResponseModel;
import com.parianom.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaksi extends AppCompatActivity {
    private RecyclerView rv;
    private RecyclerView.Adapter adTr;
    private RecyclerView.LayoutManager lmTr;
    private List<TransaksiModel> transaksiModels = new ArrayList<>();
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        sessionManager = new SessionManager(getApplicationContext());
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
        HashMap<String,String> user = sessionManager.getUserDetails();
        getData(Integer.valueOf(user.get(SessionManager.kunci_id_user)));
        rv = (RecyclerView) findViewById(R.id.transaksiRv);


    }

    public void getData(Integer s) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<TransaksiResponseModel> get = mApiService.getPesananPenjual(s);
        get.enqueue(new Callback<TransaksiResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<TransaksiResponseModel> call, Response<TransaksiResponseModel> response) {
                transaksiModels=response.body().getData();
                lmTr = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(lmTr);
                adTr = new TransaksiRVAdapter(getApplicationContext(),transaksiModels);
                rv.setAdapter(adTr);
                rv.setVisibility(View.VISIBLE);
                adTr.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TransaksiResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData(Integer.valueOf(getIntent().getStringExtra("id_penjual")));
    }
}