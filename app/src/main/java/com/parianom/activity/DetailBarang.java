package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        chat = (Button) findViewById(R.id.btnChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailBarang.this, Chat.class);
                startActivity(intent);
            }
        });

    }

    private void increment(View view) {
    }

    private void decrement() {
    }


}