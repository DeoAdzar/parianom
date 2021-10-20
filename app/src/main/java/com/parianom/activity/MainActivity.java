package com.parianom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parianom.R;
import com.parianom.fragment.BoardMasukFragment;
import com.parianom.fragment.PesanFragment;
import com.parianom.fragment.ProfilFragment;
import com.parianom.fragment.RiwayatFragment;
import com.parianom.fragment.BerandaFragment;
import com.parianom.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragment;
    private RelativeLayout pangan, kriya;
    private FragmentManager fragmentManager;
    boolean doubleBack = false;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(getApplicationContext());
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.beranda:
                            selectedFragment = new BerandaFragment();
                            break;
                        case R.id.pesan:
                            if (sessionManager.checkLogin()==1) {
                                selectedFragment = new PesanFragment();
                                break;
                            } else{
                                selectedFragment = new BoardMasukFragment();
                                break;
                            }
                        case R.id.riwayat:
                            if (sessionManager.checkLogin()==1) {
                                selectedFragment = new RiwayatFragment();
                                break;
                            } else{
                                selectedFragment = new BoardMasukFragment();
                                break;
                            }
                        case R.id.profil:
                            if (sessionManager.checkLogin()==1){
                                selectedFragment = new ProfilFragment();
                                break;
                            }else{
                                selectedFragment = new BoardMasukFragment();
                                break;
                            }
                }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();
        this.doubleBack = true;
        Toast.makeText(this, "Tekan dua kali untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();
//    }
}