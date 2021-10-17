package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.KecamatanModel;
import com.parianom.model.KecamatanResponseModel;
import com.parianom.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormBukaToko extends AppCompatActivity {
    Button bkToko, ktp;
    EditText namaToko, nik, alamatToko;
    AutoCompleteTextView kec;
//    Bitmap bitmap;
//    Uri selectedImage;
    String mediaPath,postPath;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    Context context;
    ImageView imgKtp;
    BaseApiService mApiService;
    boolean cekNik;
    SessionManager sessionManager;
    List<KecamatanModel> kecamatanModelList = new ArrayList<>();
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            inputItem();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_buka_toko);
        sessionManager = new SessionManager(getApplicationContext());
        context = this;
        mApiService = UtilsApi.getApiService();

        namaToko = findViewById(R.id.namaBukaToko);
        nik = (EditText) findViewById(R.id.nik);
        alamatToko = (EditText) findViewById(R.id.alamatBukaToko);
        kec = findViewById(R.id.kecamatanBukaToko);
        bkToko = (Button) findViewById(R.id.btnSimpanToko);
        ktp = (Button) findViewById(R.id.ktp);
        imgKtp = findViewById(R.id.imgKtp);

        // Toolbar
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
        bkToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        getDataKecamatan();
        action();

    }
    public void action(){
        nik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cek = mApiService.cekNik(charSequence.toString());
                cek.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonResult =new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")){
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekNik = false;
                                }else if (charSequence.toString().isEmpty()){
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekNik = false;
                                }
                                else{
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                                    cekNik = true;
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
        kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kec.showDropDown();
            }
        });
        kec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                kec.showDropDown();
            }
        });
        kec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kec.showDropDown();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else {
            inputItem();
        }
    }
    private void inputItem() {
        if (namaToko.getText().toString().isEmpty()
                ||nik.getText().toString().isEmpty()
                ||alamatToko.getText().toString().isEmpty()
                ||kec.getText().toString().isEmpty()){
            Toast.makeText(FormBukaToko.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
        }else if (mediaPath==null){
            Toast.makeText(FormBukaToko.this, "Mohon upload foto ktp anda", Toast.LENGTH_SHORT).show();
        }else if(cekNik==false){
            Toast.makeText(FormBukaToko.this, "Nik Sudah digunakan", Toast.LENGTH_SHORT).show();
        }else if(nik.getText().length()<16||nik.getText().length()>16){
            Toast.makeText(FormBukaToko.this, "Isi Nik dengan benar", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> user = sessionManager.getUserDetails();
            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length/1024;
            if (size>4096){
                Toast.makeText(FormBukaToko.this, "ukuran Gambar terlalu besar"+size, Toast.LENGTH_SHORT).show();
            }else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_ktp", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.registerPenjual(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.kunci_id_user))
                        , RequestBody.create(MediaType.parse("text/plain"), namaToko.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), nik.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), alamatToko.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), kec.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Intent i = new Intent(FormBukaToko.this,Konfirmasi.class);
                            startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(FormBukaToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void getDataKecamatan() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<KecamatanResponseModel> get = mApiService.getKecamatan();
        get.enqueue(new Callback<KecamatanResponseModel>() {
            @Override
            public void onResponse(Call<KecamatanResponseModel> call, Response<KecamatanResponseModel> response) {
                if (response.isSuccessful()) {
                    kecamatanModelList = response.body().getData();
                    List<String> listAdmin = new ArrayList<String>();
                     for (int i = 0; i < kecamatanModelList.size(); i++){
                        listAdmin.add(kecamatanModelList.get(i).getNama());
                    }
                    // Set hasil result json ke dalam adapter spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormBukaToko.this,
                            R.layout.custom_spinner, listAdmin);
                    adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                    kec.setThreshold(0);
                    kec.setAdapter(adapter);
                } else {
                    Toast.makeText(FormBukaToko.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KecamatanResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_PICK_PHOTO){
                if (data != null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex= cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imgKtp.setVisibility(View.VISIBLE);
                    imgKtp.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}