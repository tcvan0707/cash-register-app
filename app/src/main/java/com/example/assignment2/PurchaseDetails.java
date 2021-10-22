package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class PurchaseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);
        // Show "back button" in top bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Extract history of purchases from main activity
        Intent intent = getIntent();
        Purchase purchase = (Purchase) intent.getSerializableExtra("purchase");
        Product product = purchase.getProduct();

        TextView type = findViewById(R.id.purchaseDetailsType);
        String typeFormat = getString(R.string.purchaseDetailType);
        type.setText(String.format(typeFormat, product.getType()));

        TextView price = findViewById(R.id.purchaseDetailsPrice);
        String priceFormat = getString(R.string.purchaseDetailPrice);
        price.setText(String.format(priceFormat, product.getPrice()));

        TextView date = findViewById(R.id.purchaseDetailsDate);
        String dateFormat = getString(R.string.purchaseDetailDate);
        date.setText(String.format(
                dateFormat,
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                        .format(purchase.getDate())
        ));
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