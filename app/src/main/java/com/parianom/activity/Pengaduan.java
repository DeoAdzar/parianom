package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.ImageResizer;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pengaduan extends AppCompatActivity {
    private EditText keterangan;
    private ImageView viewBukti;
    private Spinner poinPengaduan;
    private Button bukti, kirim;
    private ProgressBar loading;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    SessionManager sessionManager;
    private static final int REQUEST_PICK_PHOTO = 2;
    private String mediaPath, postPath;
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
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_pengaduan);

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
        keterangan = findViewById(R.id.ketPengaduan);
        viewBukti = findViewById(R.id.viewBukti);
        poinPengaduan = findViewById(R.id.poinPengaduan);
        bukti = findViewById(R.id.buktiPengaduan);
        kirim = findViewById(R.id.btnPengaduan);
        loading = findViewById(R.id.progress_pengaduan);
        TextView custom = findViewById(R.id.custom_dropdown);

//        poinPengaduan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.custom_dropdown, getResources().getStringArray(R.array.poinPengaduan));
        adapter.setDropDownViewResource(R.layout.custom_dropdown);
        poinPengaduan.setAdapter(adapter);

        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirim.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                requestPermissions();
            }
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            inputItem();
        }
    }

    private void inputItem() {
        kirim.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        HashMap<String, String> user = sessionManager.getUserDetails();
        if (keterangan.getText().toString().isEmpty()||mediaPath.isEmpty()||mediaPath == null) {
            Toast.makeText(Pengaduan.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            kirim.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else {
            Bitmap fullSizeBitmap = BitmapFactory.decodeFile(mediaPath);
            Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 1000000);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            reducedBitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
            String imageBase64 = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.inputAduan(RequestBody.create(MediaType.parse("text/plain"), imageBase64)
                        , RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.kunci_id_user))
                        , RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("id_penjual"))
                        , RequestBody.create(MediaType.parse("text/plain"), poinPengaduan.getSelectedItem().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), keterangan.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        kirim.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(Pengaduan.this, "Laporan anda terkirim", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        kirim.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(Pengaduan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        }
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
                    viewBukti.setVisibility(View.VISIBLE);
                    viewBukti.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}