package com.example.assignment2;

import java.io.Serializable;
import java.util.Date;

public class Purchase implements Serializable {
    private Product product;
    private Date date;

    public Purchase(Product product, Date date) {
        this.product = product;
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
