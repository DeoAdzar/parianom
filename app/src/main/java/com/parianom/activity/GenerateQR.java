package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
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

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQR extends AppCompatActivity {
    private ImageView qrCode;
    private String kode;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrCode = (ImageView) findViewById(R.id.qrCode);

        Bundle bundle = getIntent().getExtras();

        if(TextUtils.isEmpty(bundle.getString("kode"))) {
            Toast.makeText(GenerateQR.this, "Masukan id pesanan", Toast.LENGTH_SHORT).show();
        } else {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();

            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;

            int dimen = width < height ? width : height;
            dimen = dimen * 3/4;

            qrgEncoder = new QRGEncoder(bundle.getString("kode"), null, QRGContents.Type.TEXT, dimen);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.e("Tag", e.toString());
            }
        }

//        generate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(TextUtils.isEmpty(kode.getText().toString())) {
//                    Toast.makeText(GenerateQR.this, "Masukan id pesanan", Toast.LENGTH_SHORT).show();
//                } else {
//                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
//                    Display display = manager.getDefaultDisplay();
//
//                    Point point = new Point();
//                    display.getSize(point);
//
//                    int width = point.x;
//                    int height = point.y;
//
//                    int dimen = width < height ? width : height;
//                    dimen = dimen * 3/4;
//
//                    qrgEncoder = new QRGEncoder(kode.getText().toString(), null, QRGContents.Type.TEXT, dimen);
//
//                    try {
//                        bitmap = qrgEncoder.encodeAsBitmap();
//                        qrCode.setImageBitmap(bitmap);
//                    } catch (WriterException e) {
//                        Log.e("Tag", e.toString());
//                    }
//                }
//            }
//        });
    }
}