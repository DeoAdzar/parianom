package com.parianom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.KecamatanModel;
import com.parianom.model.KecamatanResponseModel;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilToko extends AppCompatActivity {
    Button simpan;
    CircleImageView imgToko;
    EditText nama,alamat;
    AutoCompleteTextView kecamatan;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    SessionManager sessionManager;
    List<KecamatanModel> kecamatanModelList = new ArrayList<>();
    private ProgressBar loading;

    String mediaPath,postPath;
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
        setContentView(R.layout.activity_profil_toko);
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
        imgToko = findViewById(R.id.imgToko);
        nama= findViewById(R.id.edtNamaToko);
        alamat= findViewById(R.id.edtAlamatTk);
        kecamatan = findViewById(R.id.kecamatanProfilToko);
        simpan = (Button) findViewById(R.id.btnSimpanEdtTk);
        loading = findViewById(R.id.progress_edit_profil_toko);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        imgToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO );
            }
        });
        getData();
        getDataKecamatan();
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else {
            inputItem();
        }
    }
//      DIBARNE KERI
    private void getData() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getPenjual(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult =new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("exist")){
                            String nama_toko = jsonResult.getJSONObject("data").getString("nama_toko");
                            String alamat_toko = jsonResult.getJSONObject("data").getString("alamat");
                            String kec = jsonResult.getJSONObject("data").getString("kec");
                            String foto_toko =jsonResult.getJSONObject("data").getString("foto_toko");
                            Picasso.get().load(UtilsApi.IMAGES_TOKO + foto_toko)
                                    .placeholder(R.drawable.ic_person)
                                    .into(imgToko);
                            nama.setText(nama_toko);
                            alamat.setText(alamat_toko);
                            kecamatan.setText(kec);
                        }else{
                            Toast.makeText(ProfilToko.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e ){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(ProfilToko.this, "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfilToko.this,
                            R.layout.custom_spinner_dropdown, listAdmin);
                    adapter.setDropDownViewResource(R.layout.custom_spinner);
                    kecamatan.setThreshold(0);
                    kecamatan.setAdapter(adapter);
                } else {
                    Toast.makeText(ProfilToko.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KecamatanResponseModel> call, Throwable t) {

            }
        });
    }

    private void inputItem() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        simpan.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        if (nama.getText().toString().isEmpty()
                ||alamat.getText().toString().isEmpty()
                ||kecamatan.getText().toString().isEmpty()){
            Toast.makeText(ProfilToko.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if (mediaPath==null) {
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> update = mApiService.updatePenjual2(
                      Integer.parseInt(user.get(SessionManager.kunci_id_user))
                    , nama.getText().toString()
                    , kecamatan.getText().toString()
                    , alamat.getText().toString());
            update.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ProfilToko.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                    Toast.makeText(ProfilToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    simpan.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            });
        }else {

            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length/1024;
            if (size>4096){
                Toast.makeText(ProfilToko.this, "ukuran Gambar terlalu besar"+size, Toast.LENGTH_SHORT).show();
                simpan.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_toko", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.updatePenjual(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.kunci_id_user))
                        , RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), kecamatan.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), alamat.getText().toString()));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(ProfilToko.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(ProfilToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    imgToko.setVisibility(View.VISIBLE);
                    imgToko.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }
}