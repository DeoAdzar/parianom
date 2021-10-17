package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parianom.R;

public class TambahProduk extends AppCompatActivity {
    Button simpan;
    CardView cardImg;
    ImageView img;
    Uri selectedImage;
    Spinner kategori, jenis;

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

        img = (ImageView) findViewById(R.id.imgTambahPr);
        cardImg = (CardView) findViewById(R.id.cardImgTambahPr);
        kategori = (Spinner) findViewById(R.id.kategori);
        jenis = (Spinner) findViewById(R.id.jenis);
        
        if (kategori.getSelectedItem().equals("Pangan")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisPangan, R.layout.custom_spinner);
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            jenis.setAdapter(adapter);
        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jenisKriya, R.layout.custom_spinner);
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            jenis.setAdapter(adapter);
        }

        simpan = (Button) findViewById(R.id.btnSimpanPr);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TambahProduk.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TambahProduk.this, Toko.class);
                startActivity(intent);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        cardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && null != data) {
            if (resultCode == TambahProduk.this.RESULT_OK) {
                selectedImage = data.getData();
                Log.i("selectedImage", "selectedImage: " + selectedImage.toString());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = TambahProduk.this.getContentResolver().query(selectedImage,
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