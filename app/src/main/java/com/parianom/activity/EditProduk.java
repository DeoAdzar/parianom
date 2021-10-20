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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProduk extends AppCompatActivity {
    Button simpan;
    ImageView img;
    Spinner kategori, jenis;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String mediaPath, postPath;
    SessionManager sessionManager;
    EditText nama,harga,stok;
    private ProgressBar loading;;
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
        setContentView(R.layout.activity_edit_produk);

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
        getProduk();
        img = (ImageView) findViewById(R.id.etImgEditPr);
        simpan = (Button) findViewById(R.id.btnSimpanEditPr);
        kategori = findViewById(R.id.etKategoriEditPr);
        jenis = findViewById(R.id.etJenisEditPr);
        nama = findViewById(R.id.etNamaEditPr);
        harga = findViewById(R.id.etHargaEditPr);
        stok = findViewById(R.id.etStokEditPr);
        loading = findViewById(R.id.progress_edit_produk);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        change();
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
    private void getProduk() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getProdukById(Integer.parseInt(getIntent().getStringExtra("id_produk")));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")) {
                            String kategoris = jsonResult.getJSONObject("data").getString("kategori");
                            String kategori_sub = jsonResult.getJSONObject("data").getString("kategori_sub");
                            String namas = jsonResult.getJSONObject("data").getString("nama");
                            String hargas = jsonResult.getJSONObject("data").getString("harga");
                            String stoks = jsonResult.getJSONObject("data").getString("stok");
                            String images = jsonResult.getJSONObject("data").getString("foto_produk");
                            Picasso.get().load(UtilsApi.IMAGES_PRODUK + images)
                                    .placeholder(R.drawable.ic_person)
                                    .into(img);
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.kategori, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            kategori.setAdapter(adapter);
                            if (kategoris != null) {
                                int spinnerPosition = adapter.getPosition(kategoris);
                                kategori.setSelection(spinnerPosition);
                            }
                            ArrayAdapter<CharSequence> adapterPangan = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ArrayAdapter<CharSequence> adapterKriya = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisKriya, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            if  (kategori_sub!=null){
                                int spinnerPangan = adapterPangan.getPosition(kategori_sub);
                                int spinnerKriya = adapterKriya.getPosition(kategori_sub);
                                if (spinnerPangan>=0){
                                    jenis.setAdapter(adapterPangan);
                                    jenis.setSelection(spinnerPangan);
                                }else if (spinnerKriya>=0){
                                    jenis.setAdapter(adapterKriya);
                                    jenis.setSelection(spinnerKriya);
                                }
                            }
                            nama.setText(namas);
                            harga.setText(hargas);
                            stok.setText(stoks);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void change(){
        ArrayAdapter<CharSequence> adapterPangan = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, R.layout.custom_spinner);
        adapterPangan.setDropDownViewResource(R.layout.custom_spinner_dropdown);
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
    private void inputItem() {
        simpan.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        if (nama.getText().toString().isEmpty()
                || harga.getText().toString().isEmpty()
                || stok.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if (mediaPath == null){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> update = mApiService.updateProduk2(
                    kategori.getSelectedItem().toString()
                    ,jenis.getSelectedItem().toString()
                    , nama.getText().toString()
                    ,Integer.parseInt(harga.getText().toString())
                    ,Integer.parseInt(stok.getText().toString())
                    , Integer.parseInt(getIntent().getStringExtra("id_produk")));
            update.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getApplicationContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    simpan.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            });
        }
        else {

            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length / 1024;
            if (size > 4096) {
                Toast.makeText(getApplicationContext(), "ukuran Gambar terlalu besar" + size, Toast.LENGTH_SHORT).show();
                simpan.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            } else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.updateProduk(partImage
                        , RequestBody.create(MediaType.parse("text/plain"),  kategori.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), jenis.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), harga.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), stok.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("id_produk")));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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

}