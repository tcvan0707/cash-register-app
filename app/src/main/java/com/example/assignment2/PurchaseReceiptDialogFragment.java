package com.example.assignment2;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PurchaseReceiptDialogFragment extends DialogFragment {
    private Purchase purchase;

    public PurchaseReceiptDialogFragment(Purchase purchase) {
        this.purchase = purchase;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getString(R.string.productReceiptDialogTitle);
        String format = getString(R.string.productReceiptDialogMessage);
        Product product = purchase.getProduct();
        builder.setTitle(title)
                .setMessage(String.format(
                        format,
                        product.getQuantity(),
                        product.getType(),
                        product.getPrice() * product.getQuantity()
                ));
        return builder.create();
    }
}
