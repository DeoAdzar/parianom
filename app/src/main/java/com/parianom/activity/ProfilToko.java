package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
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
//        getData();
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else {
            inputItem();
        }
    }
//      DIBARNE KERI
//    private void getData() {
//        HashMap<String, String> user = sessionManager.getUserDetails();
//        BaseApiService mApiService = UtilsApi.getApiService();
//        Call<ResponseBody> get = mApiService.getPenjual(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
//        get.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try {
//                        JSONObject jsonResult =new JSONObject(response.body().string());
//                        if (jsonResult.getString("message").equals("exist")){
//                            String nama_toko = jsonResult.getJSONObject("data").getString("nama_toko");
//                            String alamat_toko = jsonResult.getJSONObject("data").getString("alamat");
//                            String nik = jsonResult.getJSONObject("data").getString("nik");
//                            String kec = jsonResult.getJSONObject("data").getString("kec");
//                            Picasso.get().load(UtilsApi.IMAGES_PROFIL + images)
//                                    .placeholder(R.drawable.ic_person)
//                                    .into(Image);
//                            email.setText(emails);
//                            nama.setText(namas);
//                            alamat.setText(alamats);
//                            no_hp.setText(no_hps);
//                            username.setText(usernames);
//                        }else{
//                            Toast.makeText(EditProfil.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
//                        }
//                    }catch (JSONException e ){
//                        e.printStackTrace();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                }else {
//                    Toast.makeText(EditProfil.this, "Cannot Connect server", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

    private void inputItem() {
    }
}