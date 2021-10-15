package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;


public class BukaToko extends AppCompatActivity {

    Button jualan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_toko);
        jualan = (Button) findViewById(R.id.btnBukaToko);

        jualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BukaToko.this, FormBukaToko.class);
                startActivity(intent);
                finish();
            }
        });
    }
}