package com.parianom.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parianom.R;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQr extends AppCompatActivity {
    private ScannerLiveView camera;
    private String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

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

        if (checkPermission()) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions();
        }

        camera = (ScannerLiveView) findViewById(R.id.camScanQr);
        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanQr.this, "Mulai Memindai", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanQr.this, "Pemindaian Selesai", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(ScanQr.this, "Scanner Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                if (data.equals("Sayur Kol")) {
                    Toast.makeText(ScanQr.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ScanQr.this, Transaksi.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ScanQr.this, "Kode tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(ScanQr.this, data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();

        decoder.setScanAreaPercent(0.5);
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    protected void onPause() {
        // on app pause the
        // camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }

    private void requestPermissions() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denined \n You cannot use app without providing permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}