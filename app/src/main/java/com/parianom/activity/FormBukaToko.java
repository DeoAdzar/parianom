package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parianom.R;

import java.io.ByteArrayOutputStream;

public class FormBukaToko extends AppCompatActivity {
    Button bkToko, ktp;
    EditText namaToko, nik, alamatToko;
    Spinner kec;
    Bitmap bitmap;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_buka_toko);

        namaToko = (EditText) findViewById(R.id.namaBukaToko);
        nik = (EditText) findViewById(R.id.nik);
        alamatToko = (EditText) findViewById(R.id.alamatBukaToko);
        kec = (Spinner) findViewById(R.id.kecamatanBukaToko);
        bkToko = (Button) findViewById(R.id.btnSimpanToko);
        ktp = (Button) findViewById(R.id.ktp);

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

        ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });

        bukaToko();
    }

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

//    protected void uploadTask() {
//        // TODO Auto-generated method stub
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//        byte[] data = bos.toByteArray();
//        String file = Base64.encodeToString(data, 0);
//        Log.i("base64 string", "base64 string: " + file);
//        new ImageUploadTask(file).execute();
//    }

    public void gallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
//                1);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

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