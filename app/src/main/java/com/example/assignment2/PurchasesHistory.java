package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class PurchasesHistory extends AppCompatActivity {
    private ArrayList<Purchase> purchases;
    private PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases_history);
        // Show "back button" in top bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Extract history of purchases from main activity
        Intent intent = getIntent();
        purchases = (ArrayList<Purchase>) intent.getSerializableExtra("purchases");

        // Fill RecyclerView with purchases
        RecyclerView purchasesView = findViewById(R.id.purchases);
        purchaseAdapter = new PurchaseAdapter(this, purchases);
        purchasesView.setAdapter(purchaseAdapter);
        purchasesView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Finish activity on press of "back button"
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}