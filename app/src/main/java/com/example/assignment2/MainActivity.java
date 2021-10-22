package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int selectedProductPosition = -1;
    private Product selectedProduct = null;
    private int selectedQuantity = -1;

    private ArrayList<Purchase> purchases = new ArrayList<>();

    // Reevaluates total price of selected purchase. Should be run on any change to selection
    private void updateTotal() {
        TextView total = findViewById(R.id.total);
        TextView selectedQuantityView = findViewById(R.id.quantity);
        if (selectedProduct != null && selectedQuantity != -1) {
            double price = selectedProduct.getPrice();
            total.setText(new DecimalFormat("$#.0#").format(selectedQuantity * price));
        } else {
            total.setText(R.string.total);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fill ListView from array of products
        ListView productsView = findViewById(R.id.products);
        ArrayList<Product> products = new ArrayList<Product>() {
            {
                add(new Product("Pants", 20.44, 10));
                add(new Product("Shoes", 10.44, 100));
                add(new Product("Hats", 5.9, 30));
            }
        };
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), products);
        productsView.setAdapter(productAdapter);

        // Update Product TextView to new product type selected in ListView
        TextView selectedProductType = findViewById(R.id.selectedProductType);
        productsView.setOnItemClickListener((parent, view, position, id) -> {
            selectedProduct = (Product) parent.getItemAtPosition(position);
            selectedProductPosition = position;
            selectedProductType.setText(selectedProduct.getType());
            updateTotal();
        });

        // Handler for press on buttons with numbers (0 - 9)
        View.OnClickListener numberButtonListener = v -> {
            TextView selectedQuantityView = findViewById(R.id.quantity);
            String selectedQuantityText = selectedQuantityView.getText().toString();
            try {
                Integer.parseInt(selectedQuantityText);
            } catch (NumberFormatException e) {
                selectedQuantityText = "";
            }
            String pressedButton = ((Button)v).getText().toString();
            selectedQuantityText += pressedButton;
            selectedQuantity = Integer.parseInt(selectedQuantityText);
            selectedQuantityView.setText(selectedQuantityText);
            updateTotal();
        };
        // Handler for clear button (C)
        View.OnClickListener clearButtonListener = v -> {
            TextView selectedQuantityView = findViewById(R.id.quantity);
            selectedQuantityView.setText(R.string.quantity);
            selectedQuantity = -1;
            updateTotal();
        };

        // Attach button handlers to buttons
        TableLayout numpad = findViewById(R.id.numpad);
        for(int i = 0; i < numpad.getChildCount(); i++) {
            View numpadRow = numpad.getChildAt(i);
            if (numpadRow instanceof TableRow) {
                for(int j = 0; j < ((TableRow) numpadRow).getChildCount(); j++) {
                    View button = ((TableRow) numpadRow).getChildAt(j);
                    if(button instanceof Button && button.getId() != R.id.numpadClear) {
                        button.setOnClickListener(numberButtonListener);
                    }
                }
            }
        }
        Button numpadClear = findViewById(R.id.numpadClear);
        numpadClear.setOnClickListener(clearButtonListener);

        // Handle press of "BUY" button
        Button buy = findViewById(R.id.buy);
        buy.setOnClickListener(v -> {
            Context context = getApplicationContext();
            if (selectedProduct != null && selectedQuantity != -1) {
                if (selectedProduct.getQuantity() >= selectedQuantity) {
                    // Update stock in products list
                    int newQuantity = selectedProduct.getQuantity() - selectedQuantity;
                    products.get(selectedProductPosition).setQuantity(newQuantity);
                    productAdapter.setProducts(products);
                    // Add purchase to history
                    Product boughtProduct = new Product(
                            selectedProduct.getType(),
                            selectedProduct.getPrice(),
                            selectedQuantity
                    );
                    Purchase purchase = new Purchase(boughtProduct, new Date());
                    purchases.add(purchase);
                    // Show message with information about purchase
                    PurchaseReceiptDialogFragment dialog = new PurchaseReceiptDialogFragment(purchase);
                    dialog.show(getSupportFragmentManager(), "receipt");
                } else {
                    // Show an error in case quantity of selected product is smaller than requested
                    Toast stockQuantityError = Toast.makeText(
                            context,
                            R.string.stockQuantityErrorMessage,
                            Toast.LENGTH_SHORT
                    );
                    stockQuantityError.show();
                }
            } else {
                // Show an error in case either product or quantity is not selected
                Toast inputValidationError = Toast.makeText(
                        context,
                        R.string.inputValidationErrorMessage,
                        Toast.LENGTH_SHORT
                );
                inputValidationError.show();
            }
        });

        // Transition to "History" activity
        Button manager = findViewById(R.id.manager);
        manager.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManagerActivity.class);
            intent.putExtra("purchases", (Serializable) purchases);
            startActivity(intent);
        });
    }
}