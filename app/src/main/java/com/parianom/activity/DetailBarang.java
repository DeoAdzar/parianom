package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parianom.R;
import com.parianom.utils.SessionManager;

public class DetailBarang extends AppCompatActivity {
    Button decrement, increment, chat;
    int quantity = 1;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        sessionManager = new SessionManager(getApplicationContext());
        chat = (Button) findViewById(R.id.btnChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.checkLogin()==1){
                    Intent intent = new Intent(DetailBarang.this, Chat.class);
//                    iki diisi putstring
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DetailBarang.this, Masuk.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void increment(View view) {
    }

    private void decrement() {
    }


}