package com.parianom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Otp extends AppCompatActivity {
    EditText otp1, otp2,otp3,otp4,otp5,otp6;
    Button verif;
    TextView resend,countdown;
    String verificationId,username,nama_lengkap,email,no_hp,alamat,kata_sandi;
    SessionManager sessionManager;
    private ProgressBar loading, loading_resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_otp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOtp);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sessionManager = new SessionManager(getApplicationContext());
        otp1 = (EditText) findViewById(R.id.otp1);
        otp2 = (EditText) findViewById(R.id.otp2);
        otp3 = (EditText) findViewById(R.id.otp3);
        otp4 = (EditText) findViewById(R.id.otp4);
        otp5 = (EditText) findViewById(R.id.otp5);
        otp6 = (EditText) findViewById(R.id.otp6);
        resend = findViewById(R.id.resend);
        countdown = findViewById(R.id.countdown);
        loading = findViewById(R.id.progress_otp);
        loading_resend = findViewById(R.id.progress_resend);

        verificationId = getIntent().getStringExtra("verificationId");
        nama_lengkap = getIntent().getStringExtra("nama_lengkap");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        no_hp = getIntent().getStringExtra("no_hp");
        alamat = getIntent().getStringExtra("alamat");
        kata_sandi = getIntent().getStringExtra("kata_sandi");
        setupInput();
        countDown();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend.setVisibility(View.GONE);
                loading_resend.setVisibility(View.VISIBLE);
                resend();
            }
        });
        verif = (Button) findViewById(R.id.btnVerif);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verif.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                if (otp1.getText().toString().trim().isEmpty()
                        || otp2.getText().toString().trim().isEmpty()
                        || otp3.getText().toString().trim().isEmpty()
                        || otp4.getText().toString().trim().isEmpty()
                        || otp5.getText().toString().trim().isEmpty()
                        || otp6.getText().toString().trim().isEmpty()) {
                    verif.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(Otp.this, "Masukkan kode verifikasi", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()
                        +otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();
                if (verificationId != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        verif.setVisibility(View.VISIBLE);
                                        loading.setVisibility(View.GONE);
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.putExtra("site","3");
                                        startActivity(i);
                                        addToDatabase(username,nama_lengkap,email,no_hp,alamat,kata_sandi);
                                    }else{
                                        verif.setVisibility(View.VISIBLE);
                                        loading.setVisibility(View.GONE);
                                        Toast.makeText(Otp.this, "Kode verifikasi yang anda masukkan tidak valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void addToDatabase(String username,String nama_lengkap,String email,String no_hp,String alamat,String kata_sandi) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> add = mApiService.registerUser(username,nama_lengkap,email,no_hp,alamat,kata_sandi);
        add.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent i = new Intent(Otp.this,Masuk.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void countDown() {
        new CountDownTimer(65000, 1000) {

            @Override
            public void onTick(long l) {
                countdown.setText("" + String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
            }

            @Override
            public void onFinish() {
                countdown.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
    public void resend(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+62"+getIntent().getStringExtra("no_hp"),60,
                TimeUnit.SECONDS,Otp.this,new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(Otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId = newVerificationId;
                        countdown.setVisibility(View.VISIBLE);
                        resend.setVisibility(View.GONE);
                        loading_resend.setVisibility(View.GONE);
                        countDown();
                        Toast.makeText(Otp  .this, "OTP sent", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void setupInput() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    otp2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}