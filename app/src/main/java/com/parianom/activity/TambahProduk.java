package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ImageView img, selectedImage;
    Spinner kategori, jenis;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String mediaPath, postPath;
    SessionManager sessionManager;
    EditText nama,harga,stok, deskripsi;
    private ProgressBar loading;
    private LinearLayout parentLinearLayout;

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
        sessionManager = new SessionManager(getApplicationContext());
        img = (ImageView) findViewById(R.id.imgTambahPr);
//        v1 = (ImageView) findViewById(R.id.v1);
        kategori = (Spinner) findViewById(R.id.kategori);
        jenis = (Spinner) findViewById(R.id.jenis);
        nama = findViewById(R.id.edtNamaPr);
        harga = findViewById(R.id.edtHargaPr);
        stok = findViewById(R.id.edtStokPr);
        deskripsi = findViewById(R.id.edtDeskPr);
        loading = findViewById(R.id.progress_tambah_produk);
//        if (kategori.getSelectedItem().equals("Pangan")) {
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, R.layout.custom_spinner);
//            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
//            jenis.setAdapter(adapter);
//        } else {
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisKriya, R.layout.custom_spinner);
//            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
//            jenis.setAdapter(adapter);
//        }

        change();
        simpan = (Button) findViewById(R.id.btnSimpanPr);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    public void change(){
        ArrayAdapter<CharSequence> adapterPangan = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, R.layout.custom_spinner);
        adapterPangan.setDropDownViewResource(R.layout.custom_spinner_dropdown);

//        jenis.setPadding(10, 0, 0, 0);
        ArrayAdapter<CharSequence> adapterKriya = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisKriya, R.layout.custom_spinner);
        adapterKriya.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    jenis.setAdapter(adapterPangan);
                }else{
                    jenis.setAdapter(adapterKriya);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        HashMap<String, String> user = sessionManager.getUserDetails();
        simpan.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        if (nama.getText().toString().isEmpty()
                || harga.getText().toString().isEmpty()
                || stok.getText().toString().isEmpty()) {
            Toast.makeText(TambahProduk.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if (mediaPath == null){
            Toast.makeText(TambahProduk.this, "Isi Foto Produk", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
        else {
            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length / 1024;
            if (size > 4096) {
                Toast.makeText(TambahProduk.this, "ukuran Gambar terlalu besar" + size, Toast.LENGTH_SHORT).show();
                simpan.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            } else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.inputProduk(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("id_penjual"))
                        , RequestBody.create(MediaType.parse("text/plain"), kategori.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), jenis.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), harga.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), stok.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(TambahProduk.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        simpan.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
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

//    public void addImage() {
//        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View rowView=inflater.inflate(R.layout.item_img_multiple, null);
//        // Add the new row before the add field button.
//        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
//        parentLinearLayout.isFocusable();
//
//        selectedImage = rowView.findViewById(R.id.addImg);
//    }
}