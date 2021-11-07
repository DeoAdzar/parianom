package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahKataSandi extends AppCompatActivity {
    EditText lama, baru, konfirmasi;
    TextView lupaSandi;
    Button simpanSandi;
    SessionManager sessionManager;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_ubah_kata_sandi);
        sessionManager = new SessionManager(getApplicationContext());
        lama = findViewById(R.id.kataSandiLama);
        baru = findViewById(R.id.kataSandiBaru);
        konfirmasi = findViewById(R.id.konfKataSandiBaru);
        lupaSandi = findViewById(R.id.lupaKataSandi);
        simpanSandi = findViewById(R.id.btnSimpanSandi);
        loading = findViewById(R.id.progress_ubah_pass);

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
        action();
        simpanSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanSandi.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                if (lama.getText().toString().isEmpty() || baru.getText().toString().isEmpty() || konfirmasi.getText().toString().isEmpty()){
                    Toast.makeText(UbahKataSandi.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
                    simpanSandi.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                } else if (baru.getText().toString().equals(konfirmasi.getText().toString())){
                    BaseApiService mApiService = UtilsApi.getApiService();
                    Call<ResponseBody> change = mApiService.gantiPass(Integer.parseInt(user.get(SessionManager.kunci_id_user)), lama.getText().toString(), baru.getText().toString());
                    change.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonResult = new JSONObject(response.body().string());
                                    if (jsonResult.getString("message").equals("success")) {
                                        Toast.makeText(UbahKataSandi.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                                        sessionManager.logout();
                                        finish();
                                        Intent i = new Intent(UbahKataSandi.this,Masuk.class);
                                        i.putExtra("site","2");
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    } else {
                                        simpanSandi.setVisibility(View.VISIBLE);
                                        loading.setVisibility(View.GONE);
                                        Toast.makeText(UbahKataSandi.this, "Gagal Update", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            simpanSandi.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            Toast.makeText(UbahKataSandi.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    simpanSandi.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(UbahKataSandi.this, "kata sandi tidak sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lupaSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UbahKataSandi.this, LupaPassword.class);
                startActivity(intent);
            }
        });
    }

    private void action() {
        konfirmasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals(baru.getText().toString())){
                    konfirmasi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                }else{
                    konfirmasi.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}