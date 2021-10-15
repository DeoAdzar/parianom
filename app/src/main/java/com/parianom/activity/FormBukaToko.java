package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.KecamatanModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormBukaToko extends AppCompatActivity {
    Button bkToko, ktp;
    EditText namaToko, nik, alamatToko;
    Spinner kec;
    Bitmap bitmap;
    Uri selectedImage;
    Context context;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_buka_toko);

        context = this;
        mApiService = UtilsApi.getApiService();

        namaToko = (EditText) findViewById(R.id.namaBukaToko);
        nik = (EditText) findViewById(R.id.nik);
        alamatToko = (EditText) findViewById(R.id.alamatBukaToko);
        kec = (Spinner) findViewById(R.id.kecamatanBukaToko);
        bkToko = (Button) findViewById(R.id.btnSimpanToko);
        ktp = (Button) findViewById(R.id.ktp);

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

        //  spinner Kecamatan
//        initKecamatan();
//        kec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedName = parent.getItemAtPosition(position).toString();
////                requestDetailDosen(selectedName);
//                Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        // Pilih foto dari galeri
        ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        bukaToko();
    }

    //
    private void bukaToko() {
        bkToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaToko.getText().toString().equals("a")) {
                    namaToko.setError("Nama toko sudah digunakan");
                } else if (namaToko.getText().toString().isEmpty()){
                    namaToko.setError("Wajib diisi");
                } else if (nik.getText().toString().equals("1")){
                    nik.setError("Nik sudah digunakan");
                } else if (nik.getText().toString().isEmpty()){
                    nik.setError("Wajib diisi");
                } else if (alamatToko.getText().toString().isEmpty()){
                    alamatToko.setError("Wajib diisi");
                } else {
                    Intent intent = new Intent(FormBukaToko.this, Konfirmasi.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // API data kecamatan
//    private void initKecamatan(){
////        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);
//        mApiService.getKecamatan().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    List<KecamatanModel> kecamatanModels = ne
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

//    protected void uploadTask() {
//        // TODO Auto-generated method stub
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//        byte[] data = bos.toByteArray();
//        String file = Base64.encodeToString(data, 0);
//        Log.i("base64 string", "base64 string: " + file);
//        new ImageUploadTask(file).execute();
//    }

    // Galeri
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && null != data) {
            if (resultCode == FormBukaToko.this.RESULT_OK) {
                selectedImage = data.getData();
                Log.i("selectedImage", "selectedImage: " + selectedImage.toString());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = FormBukaToko.this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                Log.i("columnIndex", "columnIndex: " + columnIndex);

                String picturePath = cursor.getString(columnIndex);
                Log.i("picturePath", "picturePath: " + picturePath);
                cursor.close();
            }
        }
    }
}