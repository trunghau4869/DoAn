package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idCart;
    private Product product;
    private int quantity;
    private double maxPrice;

    public Cart() {
    }

    public Cart(String idCart, Product product, int quantity, double maxPrice) {
        this.idCart = idCart;
        this.product = product;
        this.quantity = quantity;
        this.maxPrice = maxPrice;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
