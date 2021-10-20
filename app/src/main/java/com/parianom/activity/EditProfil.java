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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfil extends AppCompatActivity {
    Button simpan;
    CircleImageView Image;
    EditText nama, username, email, no_hp, alamat, password;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    SessionManager sessionManager;
    String mediaPath, postPath;
    private LinearLayout layout;
    private ShimmerFrameLayout shimmer;
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
        setContentView(R.layout.activity_edit_profil);
        sessionManager = new SessionManager(getApplicationContext());
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
        HashMap<String, String> user = sessionManager.getUserDetails();
        nama = findViewById(R.id.namaUser);

        username = findViewById(R.id.usernameUser);
        email = findViewById(R.id.email);
        no_hp = findViewById(R.id.nmrHp);
        alamat = findViewById(R.id.alamat);
        getDataUser(user.get(SessionManager.kunci_id_user));
        Image = findViewById(R.id.imgUser);
        layout = findViewById(R.id.layoutEditProfil);
        password = findViewById(R.id.ubahPwd);
        shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmerEditProfil);
        loading = findViewById(R.id.progress_edit_profil);

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery, REQUEST_PICK_PHOTO);
            }
        });
        simpan = (Button) findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestPermission();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfil.this, UbahKataSandi.class);
                startActivity(intent);
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

    private void getDataUser(String s) {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> get = mApiService.getUser(Integer.parseInt(s));
        get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")) {
                            String emails = jsonResult.getJSONObject("data").getString("email");
                            String namas = jsonResult.getJSONObject("data").getString("nama_lengkap");
                            String alamats = jsonResult.getJSONObject("data").getString("alamat");
                            String no_hps = jsonResult.getJSONObject("data").getString("no_hp");
                            String usernames = jsonResult.getJSONObject("data").getString("username");
                            String images = jsonResult.getJSONObject("data").getString("foto_profil");
                            Picasso.get().load(UtilsApi.IMAGES_PROFIL + images)
                                    .placeholder(R.drawable.ic_person)
                                    .into(Image);
                            email.setText(emails);
                            nama.setText(namas);
                            alamat.setText(alamats);
                            no_hp.setText(no_hps);
                            username.setText(usernames);
                            layout.setVisibility(View.VISIBLE);
                            shimmer.stopShimmer();
                            shimmer.hideShimmer();
                            shimmer.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(EditProfil.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(EditProfil.this, "Cannot Connect server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void inputItem() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        simpan.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        if (nama.getText().toString().isEmpty()
                || username.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || no_hp.getText().toString().isEmpty()
                || alamat.getText().toString().isEmpty()) {
            Toast.makeText(EditProfil.this, "Mohon Isi semua Data", Toast.LENGTH_SHORT).show();
            simpan.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }else if (mediaPath == null){
            BaseApiService mApiService = UtilsApi.getApiService();
            Call<ResponseBody> update = mApiService.updateUser2(
                      nama.getText().toString()
                    , username.getText().toString()
                    , email.getText().toString()
                    , alamat.getText().toString()
                    , no_hp.getText().toString()
                    , Integer.parseInt(user.get(SessionManager.kunci_id_user)));
            update.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(EditProfil.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                    Toast.makeText(EditProfil.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    simpan.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            });
        }
        else {

            File imagefile = new File(mediaPath);
            long length = imagefile.length();
            int size = (int) length / 1024;
            if (size > 4096) {
                Toast.makeText(EditProfil.this, "ukuran Gambar terlalu besar" + size, Toast.LENGTH_SHORT).show();
            } else {
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_profil", imagefile.getName(), reqBody);
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> update = mApiService.updateUser(partImage
                        , RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), username.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), email.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), alamat.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), no_hp.getText().toString())
                        , RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.kunci_id_user)));
                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(EditProfil.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure : Error -> " + t.getMessage());
                        Toast.makeText(EditProfil.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Image.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

}