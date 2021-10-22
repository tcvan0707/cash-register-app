package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.product_layout, parent, false);
        Product product = products.get(position);

        TextView productType = convertView.findViewById(R.id.productType);
        productType.setText(product.getType());

        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        productQuantity.setText(String.valueOf(product.getQuantity()));

        TextView productPrice = convertView.findViewById(R.id.productPrice);
        productPrice.setText("$" + String.valueOf(product.getPrice()));

        return convertView;
    }
}
