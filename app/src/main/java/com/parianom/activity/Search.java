package com.parianom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.model.PenjualanModel;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private RecyclerView rv;
    private SearchView search;
    private Spinner spinner;
    private ShimmerFrameLayout shimmer;
    private List<PenjualanModel> penjualanModelList = new ArrayList<>();

    private void setInit() {
        rv = findViewById(R.id.searchRv);
        search = findViewById(R.id.searchS);
        spinner = findViewById(R.id.spinnerKec);
        shimmer = findViewById(R.id.shimmerSearch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        setInit();

        search.setQuery(getIntent().getStringExtra("query"), true);
        search.setFocusable(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}