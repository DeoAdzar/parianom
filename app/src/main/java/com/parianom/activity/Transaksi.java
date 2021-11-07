package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private LinearLayout empty;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        getPenjual();
        rv = (RecyclerView) findViewById(R.id.transaksiRv);
        empty = findViewById(R.id.empty);


    }
    public void getPenjual(){
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getPenjual(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult =new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("exist")){
                            String id = jsonResult.getJSONObject("data").getString("id");
                            getData(Integer.parseInt(id));
                        }else{
                            Toast.makeText(Transaksi.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e ){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(Transaksi.this, "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void getData(Integer s) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<TransaksiResponseModel> get = mApiService.getPesananPenjual(s);
        get.enqueue(new Callback<TransaksiResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<TransaksiResponseModel> call, Response<TransaksiResponseModel> response) {
                transaksiModels=response.body().getData();
                if (transaksiModels.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                } else {
                    lmTr = new LinearLayoutManager(getApplicationContext());
                    rv.setLayoutManager(lmTr);
                    adTr = new TransaksiRVAdapter(getApplicationContext(), transaksiModels);
                    rv.setAdapter(adTr);
                    rv.setVisibility(View.VISIBLE);
                    adTr.notifyDataSetChanged();
                }
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