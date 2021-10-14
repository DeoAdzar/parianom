package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.parianom.R;
import com.parianom.utils.SessionManagerSplash;

public class Board extends AppCompatActivity {
    Button btnMulai;
    SessionManagerSplash sessionManagerSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        sessionManagerSplash = new SessionManagerSplash(Board.this);
        btnMulai = (Button) findViewById(R.id.btnMulai);
        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Board.this, MainActivity.class);
                startActivity(intent);
                sessionManagerSplash.createSession("continue");
                finish();
            }
        });
    }
}