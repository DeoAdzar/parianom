package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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

import com.parianom.ImageResizer;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    ImageView img, img2, img3, selectedImage;
    CardView cardImg2;
    Spinner kategori, jenis;
    private static final int select1 = 1;
    private static final int select2 = 2;
    private static final int select3 = 3;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String mediaPath1, mediaPath2, mediaPath3, postPath;
    String harga2;
    SessionManager sessionManager;
    EditText nama, harga, stok, deskripsi;
    private ProgressBar loading;
    private LinearLayout parentLinearLayout;
    Bitmap yourBitmap;

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
        img2 = (ImageView) findViewById(R.id.imgTambahPr2);
        img3 = (ImageView) findViewById(R.id.imgTambahPr3);
//        v1 = (ImageView) findViewById(R.id.v1);
        kategori = (Spinner) findViewById(R.id.kategori);
        jenis = (Spinner) findViewById(R.id.jenis);
        nama = findViewById(R.id.edtNamaPr);
        harga = findViewById(R.id.edtHargaPr);
        stok = findViewById(R.id.edtStokPr);
        deskripsi = findViewById(R.id.edtDeskPr);
        loading = findViewById(R.id.progress_tambah_produk);
        cardImg2 = findViewById(R.id.cardImg2);

//        harga2 = harga.getText().toString();

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
                startActivityForResult(galery, select1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, select2);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, select3);
            }
        });
//        harga.addTextChangedListener(new TextWatcher() {
//            private String current = harga.getText().toString().trim();
//            private String view;
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!editable.toString().equals(current)) {
//                    harga.removeTextChangedListener(this);
//
//                    String replaceable = String.format("[%s,.\\s]",   NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
//                    String cleanString = editable.toString().replaceAll(replaceable, "");
//
//                    double parsed;
//                    try {
//                        parsed = Double.parseDouble(cleanString);
//                    } catch (NumberFormatException e) {
//                        parsed = 0.00;
//                    }
//                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
//                    formatter.setMaximumFractionDigits(0);
//                    String formatted = formatter.format((parsed));
//
//                    current = formatted;
//                    harga.setText(formatted);
//                    harga.setSelection(formatted.length());
//                    harga.addTextChangedListener(this);
//                }
//            }
//        });

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
        }else if (mediaPath1 == null){
            Toast.makeText(TambahProduk.this, "Isi Foto Produk", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
        else {
            Bitmap fullSizeBitmap = BitmapFactory.decodeFile(mediaPath1);
            Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 1000000);
            File reducedFile = getBitmapFile(reducedBitmap);

//            File imagefile = new File(mediaPath1);
//            long length = imagefile.length();
//            int size = (int) length / 1024;
//            if (size > 4096) {
//                simpan.setVisibility(View.VISIBLE);
//                loading.setVisibility(View.GONE);
//                Toast.makeText(TambahProduk.this, "ukuran Gambar terlalu besar" + size, Toast.LENGTH_SHORT).show();
//            } else {

                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), reducedFile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", reducedFile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.inputProduk(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("id_penjual"))
                        , RequestBody.create(MediaType.parse("text/plain"), kategori.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), jenis.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), deskripsi.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), harga.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), stok.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(TambahProduk.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        simpan.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(TambahProduk.this, "Koneksi Anda terputus", Toast.LENGTH_SHORT).show();
                    }
                });


//                postPath = mediaPath1+mediaPath2+mediaPath3;
//                Toast.makeText(TambahProduk.this, postPath, Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "inputItem: "+harga.getText());

//            }

        }
    }

    private File getBitmapFile(Bitmap reduceBitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "reduced_file");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reduceBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == select1) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath1 = cursor.getString(columnIndex);
                    img.setImageURI(data.getData());
                    cursor.close();
                }
            }
            if (requestCode == 2) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath2 = cursor.getString(columnIndex);
                    img2.setImageURI(data.getData());
                    cursor.close();
                }
            }
            if (requestCode == select3) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath3 = cursor.getString(columnIndex);
                    img3.setImageURI(data.getData());
                    cursor.close();

                }
            }
        }
    }
}