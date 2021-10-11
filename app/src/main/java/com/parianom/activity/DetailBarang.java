package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

    }

    private void increment(View view) {
    }

    private void decrement() {
    }


}