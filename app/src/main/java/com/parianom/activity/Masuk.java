package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Masuk extends AppCompatActivity {
    Button btnMasuk;
    TextView daftar, lupaPass;
    SessionManager sessionManager;
    EditText etUsername,etPassword;
    String site;
    private ProgressBar loading;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_masuk);
        sessionManager=new SessionManager(getApplicationContext());
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        site = getIntent().getStringExtra("site");
        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        loading = findViewById(R.id.progress_masuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMasuk.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                cekLogin(etUsername.getText().toString(),etPassword.getText().toString());
            }
        });

        daftar = (TextView) findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Masuk.this, Daftar.class);
                startActivity(intent);
            }
        });

        lupaPass = (TextView) findViewById(R.id.lupaSandi);
        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Masuk.this, LupaPassword.class);
                startActivity(intent);
            }
        });
    }

    private void cekLogin(String username, String password) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> cek = mApiService.login(username, password);
        btnMasuk.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        cek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult =new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")){
                            String id_user = jsonResult.getString("id_user");
                            sessionManager.createSession(id_user);
                             if (site=="2"){
                                finish();
                            }else if (site=="1"){
                                Intent i = new Intent(Masuk.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                 Intent i = new Intent(Masuk.this,MainActivity.class);
                                 startActivity(i);
                                 finish();
                             }
//                            switch (site){
//                                case "1":
//                                    Intent i = new Intent(Masuk.this,MainActivity.class);
//                                    startActivity(i);
//                                    finish();
//                                    break;
//                                case "2":
//                                    finish();
//                                    break;
//                                default:
//                                    Intent intent = new Intent(Masuk.this,MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                            }
                        }else{
                            btnMasuk.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            Toast.makeText(Masuk.this, "Username Atau Password Salah", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e ){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(Masuk.this, "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Masuk.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}