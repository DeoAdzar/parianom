package com.parianom.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parianom.R;
import com.parianom.adapter.ChatRVAdapter;
import com.parianom.adapter.PenjualanRvAdapter;
import com.parianom.adapter.PesanRVAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.ChatModel;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PesanModel;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    Context context;
    private TextView namaPenjual, namaProduk, alamatProduk, jumlahBeli, hargaSatuan, hargaTotal, galeri, kamera, lokasi;
    private ImageView imgProduk;
    private CardView beli;
    private EditText isiPesan;
    private ImageButton tamlahLampiran;
    private FrameLayout kirim;
    private RecyclerView rv;
    SessionManager sessionManager;
    Calendar calendar;
    SimpleDateFormat sdf,sdf2;
    String harga,namaP,jumlah,namaPr,alamat,gambar,idPr,idPn, mediaPath, postPath;
    private static final int REQUEST_PICK_PHOTO = 2;

    private List<ChatModel> mData;

    private void setInit() {
        isiPesan = findViewById(R.id.formMessage);
        tamlahLampiran = findViewById(R.id.btnTambahLampiran);
        kirim = findViewById(R.id.kirimChat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        getWindow().setStatusBarColor(Color.parseColor("#20111111"));
//        getWindow().setNavigationBarColor(Color.parseColor("#20111111"));
//        getWindow().setTitle(Color.parseColor(""));

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

        setInit();

        harga = getIntent().getStringExtra("harga");
        namaP=getIntent().getStringExtra("penjual");
        jumlah = getIntent().getStringExtra("jumlah");
        namaPr=getIntent().getStringExtra("nama_produk");
        alamat = getIntent().getStringExtra("alamat");
        gambar = getIntent().getStringExtra("gambar");
        idPn = getIntent().getStringExtra("id_penjual");
        idPr= getIntent().getStringExtra("id_produk");
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sessionManager.getUserDetails();
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyMMdd");
        sdf2 = new SimpleDateFormat("HHmm");
        namaPenjual = findViewById(R.id.namaPenjualChat);
        namaProduk = findViewById(R.id.namaPrChat);
        alamatProduk = findViewById(R.id.alamatPrChat);
        jumlahBeli = findViewById(R.id.jmlBeliChat);
        hargaSatuan = findViewById(R.id.hargaSatuan);
        hargaTotal = findViewById(R.id.hargaTotalChat);
        imgProduk = findViewById(R.id.imgPrChat);
        beli = findViewById(R.id.btnBeli);
        hargaSatuan.setText(harga);
        namaPenjual.setText(namaP);
        jumlahBeli.setText(jumlah);
        namaProduk.setText(namaPr);
        alamatProduk.setText(alamat);
        Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK+gambar))
        .placeholder(R.color.shimmer).into(imgProduk);

        String hargaSt = formatRupiah(Double.parseDouble(harga));
        hargaSatuan.setText(hargaSt);

        int jumlah = Integer.parseInt(jumlahBeli.getText().toString())*Integer.parseInt(String.valueOf(harga));
        String total = formatRupiah(Double.parseDouble(String.valueOf(jumlah)));
        hargaTotal.setText(String.valueOf(total));

        String kode_pesanan = sdf.format(calendar.getTime())+ sdf2.format(calendar.getTime())+idPr+user.get(SessionManager.kunci_id_user);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> input = mApiService.inputPesanan(
                        Integer.parseInt(idPr),
                        Integer.parseInt(user.get(SessionManager.kunci_id_user)),
                        Integer.parseInt(idPn),
                        Integer.parseInt(jumlahBeli.getText().toString()),
                        kode_pesanan,
                        Integer.parseInt(String.valueOf(jumlah)));
                input.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i = new Intent(Chat.this,GenerateQR.class);
                        i.putExtra("kode_pesanan",kode_pesanan);
                        startActivity(i);
                        finish();
                        Log.d(TAG, "onResponseParianom: "
                                +idPr
                                +idPn
                                +jumlahBeli.getText().toString()+kode_pesanan+jumlah);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Chat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tamlahLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Chat.this);

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.item_lampiran_chat, null);

                kamera = v.findViewById(R.id.kamera);
                galeri = v.findViewById(R.id.galeri);
                lokasi = v.findViewById(R.id.lokasi);

                dialog.setView(view);
                dialog.setCancelable(false);

                galeri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, REQUEST_PICK_PHOTO);
                    }
                });

            }
        });
        data();
        rv = findViewById(R.id.isiChatRv);
        ChatRVAdapter adapter = new ChatRVAdapter(mData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);


    }


    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }

    private void lampiran (){
        tamlahLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Chat.this);

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.item_lampiran_chat, null);

                kamera = v.findViewById(R.id.kamera);
                galeri = v.findViewById(R.id.galeri);
                lokasi = v.findViewById(R.id.lokasi);

                dialog.setView(view);
                dialog.setCancelable(false);

                galeri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, REQUEST_PICK_PHOTO);
                    }
                });

            }
        });

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
//                    Image.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    public void data() {
        mData = new ArrayList<>();
        mData.add(new ChatModel("tes", "12-12-12 (17.00)", 12, 1, 1));
        mData.add(new ChatModel("tesaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa aaaaa", "12-12-12 (17.00)", 13, 1, 0));
        mData.add(new ChatModel("OKE DITUNGGU YAAAAAAA", "12-12-12 (17.00)", 14, 1, 1));
        mData.add(new ChatModel("tesaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa aaaaa", "12-12-12 (17.00)", 15, 1, 1));
    }
}