package com.parianom.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.PenjualanRvAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PenjualanResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    private RecyclerView rv;
    private RecyclerView.Adapter adBeranda;
    private RecyclerView.LayoutManager lmBeranda;

    private SearchView search;
    private Spinner spinner;
    private ShimmerFrameLayout shimmer;
    private List<PenjualanModel> penjualanModelList = new ArrayList<>();
    LinearLayout empty;
    private void setInit() {
        rv = findViewById(R.id.searchRv);
        search = findViewById(R.id.searchS);
        spinner = findViewById(R.id.filterKec);
        shimmer = findViewById(R.id.shimmerSearch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        empty = (LinearLayout) findViewById(R.id.empty);
        setInit();

        search.setQuery(getIntent().getStringExtra("query"), true);
        getData(getIntent().getStringExtra("query"),"");
//        Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        search.setFocusable(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String kec = null;
                kec = spinner.getSelectedItem().toString();
                if (kec.equals("Pilih Kecamatan")||kec.equals(null)||kec.isEmpty()){
                    getData(query,null);
                }else if (query==null){
                    getData(null,kec);
                }else{
                    getData(query,kec);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Pilih Kecamatan")){
                    getData(search.getQuery().toString(),null);
                } else {
                    getData(search.getQuery().toString(), adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getData(getIntent().getStringExtra("query"),null);
            }
        });

    }


    private void getData(String query, String kec) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.searchProduk(query,kec);
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList==null){
                    rv.setVisibility(View.GONE);
                    getDataByPenjual(query,kec);
                }else{
                    empty.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getApplicationContext(),2);
                    rv.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getApplicationContext(),penjualanModelList);
                    rv.setAdapter(adBeranda);

                    rv.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }

    private void getDataByPenjual(String query, String kec) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PenjualanResponseModel> get = mApiService.searchProdukByPenjual(query,kec);
        get.enqueue(new Callback<PenjualanResponseModel>() {
            @Override
            public void onResponse(Call<PenjualanResponseModel> call, Response<PenjualanResponseModel> response) {
                penjualanModelList = response.body().getData();
                if (penjualanModelList==null){
                    rv.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }else{
                    empty.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    lmBeranda = new GridLayoutManager(getApplicationContext(),2);
                    rv.setLayoutManager(lmBeranda);
                    adBeranda = new PenjualanRvAdapter(getApplicationContext(),penjualanModelList);
                    rv.setAdapter(adBeranda);
                    rv.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PenjualanResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deo: "+t.getMessage());
            }
        });
    }
}