package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    private ArrayList<Purchase> purchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        // Show "back button" in top bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Extract history of purchases from main activity
        Intent intent = getIntent();
        purchases = (ArrayList<Purchase>) intent.getSerializableExtra("purchases");

        Button history = findViewById(R.id.managerHistoryButton);
        history.setOnClickListener(v -> {
            Intent purchasesIntent = new Intent(this, PurchasesHistory.class);
            purchasesIntent.putExtra("purchases", (Serializable) purchases);
            startActivity(purchasesIntent);
        });
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