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
import android.widget.Toast;

import com.parianom.ImageResizer;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
    ImageView img, img2, img3, img4, img5;
    Spinner kategori, jenis;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int select1 = 1;
    private static final int select2 = 2;
    private static final int select3 = 3;
    private static final int select4 = 4;
    private static final int select5 = 5;
    private CardView cardImg2,cardImg3,cardImg4,cardImg5;
    private String mediaPath1, mediaPath2, mediaPath3, mediaPath4, mediaPath5, imageBase64, imageBase64_2, imageBase64_3, imageBase64_4, imageBase64_5;
    SessionManager sessionManager;
    EditText nama,harga,stok, deskripsi;
    private ProgressBar loading;
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
        img2 = (ImageView) findViewById(R.id.etImgEditPr2);
        img3 = (ImageView) findViewById(R.id.etImgEditPr3);
        img4 = (ImageView) findViewById(R.id.etImgEditPr4);
        img5 = (ImageView) findViewById(R.id.etImgEditPr5);
        simpan = (Button) findViewById(R.id.btnSimpanEditPr);
        kategori = findViewById(R.id.etKategoriEditPr);
        jenis = findViewById(R.id.etJenisEditPr);
        nama = findViewById(R.id.etNamaEditPr);
        harga = findViewById(R.id.etHargaEditPr);
        stok = findViewById(R.id.etStokEditPr);
        deskripsi = findViewById(R.id.etDeskEditPr);
        loading = findViewById(R.id.progress_edit_produk);
        cardImg2 = findViewById(R.id.cardImageEd2);
        cardImg3 = findViewById(R.id.cardImageEd3);
        cardImg4 = findViewById(R.id.cardImageEd4);
        cardImg5 = findViewById(R.id.cardImageEd5);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                requestPermission();
            }
        });
        change();

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
                            String images2 = jsonResult.getJSONObject("data").getString("foto_produk2");
                            String images3 = jsonResult.getJSONObject("data").getString("foto_produk3");
                            String images4 = jsonResult.getJSONObject("data").getString("foto_produk4");
                            String images5 = jsonResult.getJSONObject("data").getString("foto_produk5");
                            String deskrip = jsonResult.getJSONObject("data").getString("deskripsi");
                            if (images!="null"){
                                inputImage(images,img);
                                imageBase64 = images;
                                if (images2!="null"){
                                    inputImage(images2,img2);
                                    imageBase64_2 = images2;
                                    cardImg2.setVisibility(View.VISIBLE);
                                    if (images3!="null"){
                                        inputImage(images3,img3);
                                        imageBase64_3 = images3;
                                        cardImg3.setVisibility(View.VISIBLE);
                                        if (images4!="null"){
                                            inputImage(images4,img4);
                                            imageBase64_4 = images4;
                                            cardImg4.setVisibility(View.VISIBLE);
                                            if (images5!="null"){
                                                inputImage(images5,img5);
                                                imageBase64_5 = images5;
                                                cardImg5.setVisibility(View.VISIBLE);
                                            }else{
                                                cardImg5.setVisibility(View.VISIBLE);
                                            }
                                        }else{
                                            cardImg4.setVisibility(View.VISIBLE);
                                            cardImg5.setVisibility(View.GONE);
                                        }
                                    }else{
                                        cardImg3.setVisibility(View.VISIBLE);
                                        cardImg4.setVisibility(View.GONE);
                                        cardImg5.setVisibility(View.GONE);
                                    }
                                }else{
                                    cardImg2.setVisibility(View.VISIBLE);
                                    cardImg3.setVisibility(View.GONE);
                                    cardImg4.setVisibility(View.GONE);
                                    cardImg5.setVisibility(View.GONE);
                                }
                            }
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
                            deskripsi.setText(deskrip);
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
    public void inputImage(String path, ImageView imageView){
        byte[] decodedString = Base64.decode(path, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
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
        if (nama.getText().toString().isEmpty()
                || harga.getText().toString().isEmpty()
                || stok.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
//        else if (mediaPath1 == null && mediaPath2 == null && mediaPath3 == null && mediaPath4 == null && mediaPath5 == null){
//            BaseApiService mApiService = UtilsApi.getApiService();
//            Call<ResponseBody> update = mApiService.updateProduk2(
//                    kategori.getSelectedItem().toString()
//                    ,jenis.getSelectedItem().toString()
//                    , nama.getText().toString()
//                    , deskripsi.getText().toString()
//                    ,Integer.parseInt(harga.getText().toString())
//                    ,Integer.parseInt(stok.getText().toString())
//                    , Integer.parseInt(getIntent().getStringExtra("id_produk")));
//            update.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Toast.makeText(getApplicationContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("debug", "OnFailure : Error -> " + t.getMessage());
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                    simpan.setVisibility(View.VISIBLE);
//                    loading.setVisibility(View.GONE);
//                }
//            });
//        }
        else {
            if (mediaPath1 != null) {
                Bitmap fullSizeBitmap = BitmapFactory.decodeFile(mediaPath1);
                Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 1000000);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                reducedBitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
                imageBase64 = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
            }

            if (mediaPath2 != null) {
                Bitmap fullSizeBitmap2 = BitmapFactory.decodeFile(mediaPath2);
                Bitmap reducedBitmap2 = ImageResizer.reduceBitmapSize(fullSizeBitmap2, 1000000);
                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                reducedBitmap2.compress(Bitmap.CompressFormat.JPEG, 30, outputStream2);
                imageBase64_2 = Base64.encodeToString(outputStream2.toByteArray(), Base64.DEFAULT);
            }

            if (mediaPath3 != null) {
                Bitmap fullSizeBitmap3 = BitmapFactory.decodeFile(mediaPath3);
                Bitmap reducedBitmap3 = ImageResizer.reduceBitmapSize(fullSizeBitmap3, 1000000);
                ByteArrayOutputStream outputStream3 = new ByteArrayOutputStream();
                reducedBitmap3.compress(Bitmap.CompressFormat.JPEG,30,outputStream3);
                imageBase64_3 = Base64.encodeToString(outputStream3.toByteArray(),Base64.DEFAULT);
            }

            if (mediaPath4 != null) {
                Bitmap fullSizeBitmap4 = BitmapFactory.decodeFile(mediaPath4);
                Bitmap reducedBitmap4 = ImageResizer.reduceBitmapSize(fullSizeBitmap4, 1000000);
                ByteArrayOutputStream outputStream4 = new ByteArrayOutputStream();
                reducedBitmap4.compress(Bitmap.CompressFormat.JPEG,30,outputStream4);
                imageBase64_4 = Base64.encodeToString(outputStream4.toByteArray(),Base64.DEFAULT);
            }
            if (mediaPath5 != null) {
                Bitmap fullSizeBitmap5 = BitmapFactory.decodeFile(mediaPath5);
                Bitmap reducedBitmap5 = ImageResizer.reduceBitmapSize(fullSizeBitmap5, 1000000);
                ByteArrayOutputStream outputStream5 = new ByteArrayOutputStream();
                reducedBitmap5.compress(Bitmap.CompressFormat.JPEG,30,outputStream5);
                imageBase64_5 = Base64.encodeToString(outputStream5.toByteArray(),Base64.DEFAULT);
            }
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.updateProduk(imageBase64
                        , imageBase64_2
                        , imageBase64_3
                        , imageBase64_4
                        , imageBase64_5
                        , kategori.getSelectedItem().toString()
                        , jenis.getSelectedItem().toString()
                        , nama.getText().toString()
                        , Integer.parseInt(harga.getText().toString())
                        , deskripsi.getText().toString()
                        , Integer.parseInt(stok.getText().toString())
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
                    if  (mediaPath1!=null){
                        cardImg2.setVisibility(View.VISIBLE);

                    }else{
                        cardImg2.setVisibility(View.GONE);
                    }
                }
            }
            if (requestCode == select2) {
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
            if (requestCode == select4) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
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
            if (requestCode == select5) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
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