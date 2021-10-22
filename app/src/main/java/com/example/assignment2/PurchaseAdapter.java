package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    private ArrayList<Purchase> purchases = new ArrayList<>();
    private Context context;

    public PurchaseAdapter(Context context, ArrayList<Purchase> purchases) {
        this.purchases = purchases;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.purchase_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindPurchase(purchases.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PurchaseDetails.class);
            intent.putExtra("purchase", purchases.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected View itemView;
        protected TextView type;
        protected TextView quantity;
        protected TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            type = itemView.findViewById(R.id.purchaseProductType);
            quantity = itemView.findViewById(R.id.purchaseQuantity);
            total = itemView.findViewById(R.id.purchaseTotal);
        }

        public void bindPurchase(Purchase purchase) {
            Product product = purchase.getProduct();
            type.setText(product.getType());
            quantity.setText(String.valueOf(product.getQuantity()));
            total.setText(new DecimalFormat("$#.0#").format(product.getPrice() * product.getQuantity()));
        }

    }

}
