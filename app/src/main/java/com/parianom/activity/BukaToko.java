package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;
import com.parianom.fragment.ProfilFragment;


public class BukaToko extends AppCompatActivity {

    Button jualan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_buka_toko);
        jualan = (Button) findViewById(R.id.btnBukaToko);

        jualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog
                        .setMessage("Pastikan nomor telepon yang Anda gunakan aktif dan terdaftar WhatsApp!")
                        .setCancelable(false)
                        .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(BukaToko.this, FormBukaToko.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNeutralButton("Ubah Nomor", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(BukaToko.this, EditProfil.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }
}