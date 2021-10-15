package com.parianom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity {
    Button btnDaftar;
    TextView masuk;
    EditText nama_lengkap, username,email,no_hp,alamat,kata_sandi;
    boolean cekEmail,cekUsername,cekPhone;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        setInit();
        setupEditText();
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDaftar.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                if (nama_lengkap.getText().toString().isEmpty()
                        ||username.getText().toString().isEmpty()
                        ||email.getText().toString().isEmpty()
                        ||no_hp.getText().toString().isEmpty()
                        ||alamat.getText().toString().isEmpty()
                        ||kata_sandi.getText().toString().isEmpty()){
                    Toast.makeText(Daftar.this, "Mohon isi data", Toast.LENGTH_SHORT).show();
                    btnDaftar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }else if (cekPhone==false){
                    Toast.makeText(Daftar.this, "Nomer HP Sudah digunakan", Toast.LENGTH_SHORT).show();
                    btnDaftar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }else if (cekUsername==false){
                    btnDaftar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(Daftar.this, "Username Sudah digunakan", Toast.LENGTH_SHORT).show();
                }else if (cekEmail==false){
                    btnDaftar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(Daftar.this, "email Sudah digunakan", Toast.LENGTH_SHORT).show();
                }
                else {

                    auth();
                }
                setupEditText();
            }
        });

        masuk = (TextView) findViewById(R.id.masuk);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Daftar.this, Masuk.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void auth(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+62" + no_hp.getText().toString(), 60,
                TimeUnit.SECONDS, Daftar.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        btnDaftar.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        btnDaftar.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(Daftar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        btnDaftar.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Intent i = new Intent(Daftar.this,Otp.class);
                        i.putExtra("verificationId",verificationId);
                        i.putExtra("username",username.getText().toString());
                        i.putExtra("nama_lengkap",nama_lengkap.getText().toString());
                        i.putExtra("email",email.getText().toString());
                        i.putExtra("alamat",alamat.getText().toString());
                        i.putExtra("no_hp",no_hp.getText().toString());
                        i.putExtra("kata_sandi",kata_sandi.getText().toString());
                        startActivity(i);
                        finish();
                    }
                }
        );
    }

    private void setupEditText() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cek = mApiService.cekUsername(charSequence.toString());
                cek.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonResult =new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")){
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekUsername = false;
                                }else if (charSequence.toString().isEmpty()){
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekUsername = false;
                                }
                                else{
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                                    cekUsername = true;
                                }
                            }catch (JSONException e ){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cek = mApiService.cekEmail(charSequence.toString());
                cek.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonResult =new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")){
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekEmail=false;
                                }else if (charSequence.toString().isEmpty()){
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekEmail=false;
                                }else{
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                                    cekEmail=true;
                                }
                            }catch (JSONException e ){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Daftar.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        no_hp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cek = mApiService.cekPhone(charSequence.toString());
                cek.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonResult =new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")){
                                    no_hp.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekPhone = false;
                                }else if (charSequence.toString().isEmpty()){
                                    no_hp.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekPhone=false;
                                }else{
                                    no_hp.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                                    cekPhone = true;
                                }
                            }catch (JSONException e ){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Daftar.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setInit() {
        nama_lengkap = findViewById(R.id.etNamaLengkap);
        username = findViewById(R.id.etUsername);
        email = findViewById(R.id.etEmail);
        alamat = findViewById(R.id.etAlamat);
        no_hp = findViewById(R.id.etHp);
        kata_sandi = findViewById(R.id.etPassword);
        loading = findViewById(R.id.progress_daftar);
    }
}