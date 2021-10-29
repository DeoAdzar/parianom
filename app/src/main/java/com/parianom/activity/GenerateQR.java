package com.parianom.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateQR extends AppCompatActivity {
    private ImageView qrCode;
    private String kode;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

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
        handler = new Handler();
        qrCode = (ImageView) findViewById(R.id.qrCode);
        kode = getIntent().getStringExtra("kode_pesanan");

        if(kode.equals("null")) {
            Toast.makeText(GenerateQR.this, "Mohon Ulangi Pesanan", Toast.LENGTH_SHORT).show();
        } else {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();

            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;

            int dimen = width < height ? width : height;
            dimen = dimen * 3/4;

            qrgEncoder = new QRGEncoder(kode, null, QRGContents.Type.TEXT, dimen);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.e("Tag", e.toString());
            }
        }
        refresh();

    }

    private void refresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getKonfirmasi();
            }
        },2000);

    }

    private void getKonfirmasi() {
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> getConfirm = mApiService.getConfirm(kode);
        getConfirm.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("exist")){
                            String status = jsonResult.getJSONObject("data").getString("status");
                            if (status.equals("1")){
                                Intent i = new Intent(GenerateQR.this,DetailTransaksi.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("kode_pesanan",kode);
                                startActivity(i);
                                finish();
                            }else if (status.equals("null")){
                                refresh();
                            }else if (status.equals("0")){
                                Toast.makeText(getApplicationContext(), "Produk ini tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}