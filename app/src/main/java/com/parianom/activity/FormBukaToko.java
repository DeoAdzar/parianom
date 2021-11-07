package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.parianom.ImageResizer;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.KecamatanModel;
import com.parianom.model.KecamatanResponseModel;
import com.parianom.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormBukaToko extends AppCompatActivity {
    Button bkToko, tahap1, tahap2, before1, before2, ktp;
    EditText namaToko, nik, alamatToko, npwp, lainnya;
    LinearLayout lTahap1, lTahap2, lTahap3;
    AutoCompleteTextView kec;
//    Bitmap bitmap;
//    Uri selectedImage;
    String mediaPath,postPath;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    Context context;
    ImageView imgKtp;
    BaseApiService mApiService;
    boolean cekNik;
    private ProgressBar loading;
    SessionManager sessionManager;
    List<KecamatanModel> kecamatanModelList = new ArrayList<>();
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cekNik();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_buka_toko);
        sessionManager = new SessionManager(getApplicationContext());
        context = this;
        mApiService = UtilsApi.getApiService();

        namaToko = findViewById(R.id.namaBukaToko);
        nik = (EditText) findViewById(R.id.nik);
        alamatToko = (EditText) findViewById(R.id.alamatBukaToko);
        kec = findViewById(R.id.kecamatanBukaToko);
        npwp = findViewById(R.id.nomorNPWP);
//        wa = findViewById(R.id.nmrWa);
        lTahap1 = findViewById(R.id.lTahap1);
        lTahap2 = findViewById(R.id.lTahap2);
        lTahap3 = findViewById(R.id.lTahap3);
        lainnya = findViewById(R.id.lainnya);
        tahap1 = (Button) findViewById(R.id.btnTahap1);
        tahap2 = (Button) findViewById(R.id.btnTahap2);
        before1 = findViewById(R.id.btnSebelum);
        bkToko = (Button) findViewById(R.id.btnTahap3);
        before2 = findViewById(R.id.btnSebelum2);
        ktp = (Button) findViewById(R.id.ktp);
        imgKtp = findViewById(R.id.imgKtp);
        loading = findViewById(R.id.progress_buka_toko);

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
        tahap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lTahap1.setVisibility(View.GONE);
                lTahap2.setVisibility(View.VISIBLE);
                lTahap3.setVisibility(View.GONE);
            }
        });
        tahap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lTahap1.setVisibility(View.GONE);
                lTahap2.setVisibility(View.GONE);
                lTahap3.setVisibility(View.VISIBLE);
            }
        });
        before1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lTahap1.setVisibility(View.VISIBLE);
                lTahap2.setVisibility(View.GONE);
                lTahap3.setVisibility(View.GONE);
            }
        });
        bkToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bkToko.setVisibility(View.GONE);
                before2.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                lTahap1.setVisibility(View.GONE);
                lTahap2.setVisibility(View.GONE);
                requestPermission();
            }
        });
        before2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lTahap1.setVisibility(View.GONE);
                lTahap2.setVisibility(View.VISIBLE);
                lTahap3.setVisibility(View.GONE);
            }
        });
        getDataKecamatan();
        action();

    }
    public void action(){
        nik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cek = mApiService.cekNik(charSequence.toString());
                cek.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonResult =new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")){
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekNik = false;
                                }else if (charSequence.toString().isEmpty()){
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_false,0);
                                    cekNik = false;
                                }
                                else{
                                    nik.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_true,0);
                                    cekNik = true;
                                }
                            }catch (JSONException e ){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kec.showDropDown();
            }
        });
        kec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                kec.showDropDown();
            }
        });
        kec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kec.showDropDown();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else {
            cekNik();
        }
    }
    private void cekNik(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nik",nik.getText().toString());
        BaseApiService api = UtilsApi.getApiServiceKtp();
        Call<ResponseBody>cek = api.nik(jsonObject);
        cek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult =new JSONObject(response.body().string());
                        if (jsonResult.getString("metadata").equals("OK")){
                            inputItem();
                        }else{
                            Toast.makeText(getApplicationContext(), "Maaf anda bukan masyarakat kabupaten madiun", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "response Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void inputItem() {
        if (namaToko.getText().toString().isEmpty()
                ||nik.getText().toString().isEmpty()
                ||alamatToko.getText().toString().isEmpty()
                ||kec.getText().toString().isEmpty()){
            Toast.makeText(FormBukaToko.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            bkToko.setVisibility(View.VISIBLE);
            before2.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if (mediaPath==null){
            bkToko.setVisibility(View.VISIBLE);
            before2.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            Toast.makeText(FormBukaToko.this, "Mohon upload foto ktp anda", Toast.LENGTH_SHORT).show();
        }else if(cekNik==false){
            Toast.makeText(FormBukaToko.this, "Nik Sudah digunakan", Toast.LENGTH_SHORT).show();
            bkToko.setVisibility(View.VISIBLE);
            before2.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if(nik.getText().length()<16||nik.getText().length()>16){
            Toast.makeText(FormBukaToko.this, "Isi Nik dengan benar", Toast.LENGTH_SHORT).show();
            bkToko.setVisibility(View.VISIBLE);
            before2.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else {
            HashMap<String, String> user = sessionManager.getUserDetails();
            Bitmap fullSizeBitmap = BitmapFactory.decodeFile(mediaPath);
            Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 1000000);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            reducedBitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
            String imageBase64 = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
//                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
//                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_ktp", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.registerPenjual(RequestBody.create(MediaType.parse("text/plain"), imageBase64)
                        , RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.kunci_id_user))
                        , RequestBody.create(MediaType.parse("text/plain"), namaToko.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), nik.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), npwp.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), lainnya.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), alamatToko.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), kec.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(FormBukaToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        bkToko.setVisibility(View.VISIBLE);
                        before2.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                });
            }

    }
    private void getDataKecamatan() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<KecamatanResponseModel> get = mApiService.getKecamatan();
        get.enqueue(new Callback<KecamatanResponseModel>() {
            @Override
            public void onResponse(Call<KecamatanResponseModel> call, Response<KecamatanResponseModel> response) {
                if (response.isSuccessful()) {
                    kecamatanModelList = response.body().getData();
                    List<String> listAdmin = new ArrayList<String>();
                     for (int i = 0; i < kecamatanModelList.size(); i++){
                        listAdmin.add(kecamatanModelList.get(i).getNama());
                    }
                    // Set hasil result json ke dalam adapter spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormBukaToko.this,
                            R.layout.custom_spinner_dropdown, listAdmin);
                    adapter.setDropDownViewResource(R.layout.custom_spinner);
                    kec.setThreshold(0);
                    kec.setAdapter(adapter);
                } else {
                    Toast.makeText(FormBukaToko.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KecamatanResponseModel> call, Throwable t) {

            }
        });
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
                    imgKtp.setVisibility(View.VISIBLE);
                    imgKtp.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}