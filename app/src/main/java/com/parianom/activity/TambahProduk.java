package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProduk extends AppCompatActivity {
    Button simpan;
    ImageView img;
//    Uri selectedImage;
    EditText namaProduk,hargaProduk,stok;
    Spinner kategori,kategoriSub;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String mediaPath, postPath;
    SessionManager sessionManager;
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
        setContentView(R.layout.activity_tambah_produk);
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
        namaProduk = findViewById(R.id.edtNamaPr);
        hargaProduk = findViewById(R.id.edtHargaPr);
        stok = findViewById(R.id.edtStokPr);
        kategori = findViewById(R.id.kategori);
        kategoriSub = findViewById(R.id.jenisPr);
        img = (ImageView) findViewById(R.id.imgTambahPr);
        simpan = (Button) findViewById(R.id.btnSimpanPr);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TambahProduk.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO);
            }
        });
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            inputItem();
        }
    }

    private void inputItem() {
        if (namaProduk.getText().toString().isEmpty()
                || hargaProduk.getText().toString().isEmpty()
                || stok.getText().toString().isEmpty()) {
            Toast.makeText(TambahProduk.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
        }else if (mediaPath=="null"){
            Toast.makeText(TambahProduk.this, "Isi foto produk", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> user = sessionManager.getUserDetails();
            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length / 1024;
            if (size > 4096) {
                Toast.makeText(TambahProduk.this, "ukuran Gambar terlalu besar" + size, Toast.LENGTH_SHORT).show();
            } else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.inputProduk(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("id_penjual"))
                        , RequestBody.create(MediaType.parse("text/plain"), kategori.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), kategoriSub.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), namaProduk.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), hargaProduk.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), stok.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i= new Intent(TambahProduk.this,DaftarJualan.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    img.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && null != data) {
//            if (resultCode == TambahProduk.this.RESULT_OK) {
//                selectedImage = data.getData();
//                Log.i("selectedImage", "selectedImage: " + selectedImage.toString());
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = TambahProduk.this.getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor
//                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//
//                Log.i("columnIndex", "columnIndex: " + columnIndex);
//
//                String picturePath = cursor.getString(columnIndex);
//                Log.i("picturePath", "picturePath: " + picturePath);
//                cursor.close();
//            }
//        }
//    }
}