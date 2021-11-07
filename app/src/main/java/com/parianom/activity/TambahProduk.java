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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
    ImageView img, img2, img3,img4,img5, selectedImage;
    CardView cardImg2,cardImg3,cardImg4,cardImg5;
    Spinner kategori, jenis;
    private static final int select1 = 1;
    private static final int select2 = 2;
    private static final int select3 = 3;
    private static final int select4 = 4;
    private static final int select5 = 5;
    HorizontalScrollView hscroll;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String mediaPath1, mediaPath2, mediaPath3,mediaPath4,mediaPath5, imageBase64_2, imageBase64_3, imageBase64_4, imageBase64_5;
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
        img4 = (ImageView) findViewById(R.id.imgTambahPr4);
        img5 = (ImageView) findViewById(R.id.imgTambahPr5);
//        v1 = (ImageView) findViewById(R.id.v1);
        kategori = (Spinner) findViewById(R.id.kategori);
        jenis = (Spinner) findViewById(R.id.jenis);
        nama = findViewById(R.id.edtNamaPr);
        harga = findViewById(R.id.edtHargaPr);
        stok = findViewById(R.id.edtStokPr);
        deskripsi = findViewById(R.id.edtDeskPr);
        loading = findViewById(R.id.progress_tambah_produk);
        cardImg2 = findViewById(R.id.cardImg2);
        cardImg3 = findViewById(R.id.cardImg3);
        cardImg4 = findViewById(R.id.cardImg4);
        cardImg5 = findViewById(R.id.cardImg5);
        hscroll = findViewById(R.id.scrollH_add_produk);
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
                simpan.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
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
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, select4);

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, select5);

            }
        });
    }

    public void change() {
        ArrayAdapter<CharSequence> adapterPangan = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, R.layout.custom_spinner);
        adapterPangan.setDropDownViewResource(R.layout.custom_spinner_dropdown);

//        jenis.setPadding(10, 0, 0, 0);
        ArrayAdapter<CharSequence> adapterKriya = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisKriya, R.layout.custom_spinner);
        adapterKriya.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    jenis.setAdapter(adapterPangan);
                } else {
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
        if (nama.getText().toString().isEmpty()
                || harga.getText().toString().isEmpty()
                || stok.getText().toString().isEmpty()) {
            Toast.makeText(TambahProduk.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        } else if (mediaPath1 == null) {
            Toast.makeText(TambahProduk.this, "Isi Foto Produk", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        } else {
            Bitmap fullSizeBitmap = BitmapFactory.decodeFile(mediaPath1);
            Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 1000000);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            reducedBitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
            String imageBase64 = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);

            if (mediaPath2 != null) {
                Bitmap fullSizeBitmap2 = BitmapFactory.decodeFile(mediaPath2);
                Bitmap reducedBitmap2 = ImageResizer.reduceBitmapSize(fullSizeBitmap2, 1000000);
                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                reducedBitmap2.compress(Bitmap.CompressFormat.JPEG, 30, outputStream2);
                imageBase64_2 = Base64.encodeToString(outputStream2.toByteArray(), Base64.DEFAULT);
            } else {
                imageBase64_2 = null;
            }

            if (mediaPath3 != null) {
                Bitmap fullSizeBitmap3 = BitmapFactory.decodeFile(mediaPath3);
                Bitmap reducedBitmap3 = ImageResizer.reduceBitmapSize(fullSizeBitmap3, 1000000);
                ByteArrayOutputStream outputStream3 = new ByteArrayOutputStream();
                reducedBitmap3.compress(Bitmap.CompressFormat.JPEG,30,outputStream3);
                imageBase64_3 = Base64.encodeToString(outputStream3.toByteArray(),Base64.DEFAULT);
            } else {
                imageBase64_3 = null;
            }
            if (mediaPath4 != null) {
                Bitmap fullSizeBitmap4 = BitmapFactory.decodeFile(mediaPath4);
                Bitmap reducedBitmap4 = ImageResizer.reduceBitmapSize(fullSizeBitmap4, 1000000);
                ByteArrayOutputStream outputStream4 = new ByteArrayOutputStream();
                reducedBitmap4.compress(Bitmap.CompressFormat.JPEG,30,outputStream4);
                imageBase64_4 = Base64.encodeToString(outputStream4.toByteArray(),Base64.DEFAULT);
            } else {
                imageBase64_4 = null;
            }
            if (mediaPath5 != null) {
                Bitmap fullSizeBitmap5 = BitmapFactory.decodeFile(mediaPath5);
                Bitmap reducedBitmap5 = ImageResizer.reduceBitmapSize(fullSizeBitmap5, 1000000);
                ByteArrayOutputStream outputStream5 = new ByteArrayOutputStream();
                reducedBitmap5.compress(Bitmap.CompressFormat.JPEG,30,outputStream5);
                imageBase64_5 = Base64.encodeToString(outputStream5.toByteArray(),Base64.DEFAULT);
            } else {
                imageBase64_5 = null;
            }

            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> update = mApiService.inputProduk(imageBase64
                    , imageBase64_2
                    ,imageBase64_3
                    ,imageBase64_4
                    ,imageBase64_5
                    ,Integer.parseInt(getIntent().getStringExtra("id_penjual"))
                    ,kategori.getSelectedItem().toString()
                    ,jenis.getSelectedItem().toString()
                    ,nama.getText().toString()
                    , deskripsi.getText().toString()
                    , Integer.parseInt(harga.getText().toString())
                    ,Integer.parseInt(stok.getText().toString()));
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
                    Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

//    private File getBitmapFile(Bitmap reduceBitmap) {
//
//        File file = new File(Environment.getExternalStorageDirectory().toString()+ "/reduced_file");
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        reduceBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
//        try {
//            file.mkdirs();
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//            return file;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }

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
                    if  (mediaPath1!=null){
                        cardImg2.setVisibility(View.VISIBLE);

                    }else{
                        cardImg2.setVisibility(View.GONE);
                    }
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
                    if  (mediaPath2!=null){
                        cardImg3.setVisibility(View.VISIBLE);

                    }else{
                        cardImg3.setVisibility(View.GONE);
                    }
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
                    if  (mediaPath3!=null){
                        cardImg4.setVisibility(View.VISIBLE);
                    }else{
                        cardImg4.setVisibility(View.GONE);
                    }
                }
            }
            if (requestCode == select4){
                if (data!=null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                    assert  cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath4 = cursor.getString(columnIndex);
                    img4.setImageURI(data.getData());
                    cursor.close();
                    if (mediaPath4!=null){
                        cardImg5.setVisibility(View.VISIBLE);
                    }else {
                        cardImg5.setVisibility(View.GONE);
                    }
                }
            }
            if (requestCode == select5){
                if (data!=null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                    assert  cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath5 = cursor.getString(columnIndex);
                    img5.setImageURI(data.getData());
                    cursor.close();

                }
            }

        }

    }
}