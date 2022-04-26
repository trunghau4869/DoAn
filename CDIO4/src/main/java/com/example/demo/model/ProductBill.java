package com.example.demo.model;

import javax.persistence.*;

@Entity
public class ProductBill {
    @EmbeddedId
    private ProductBillKey id;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product products;

    @ManyToOne
    @MapsId("inBill")
    @JoinColumn(name = "in_bill")
    private Bill bill;

    private int quantity;
    private double price;

    public ProductBill() {
    }

    public ProductBill(ProductBillKey id, Product products, Bill bill) {
        this.id = id;
        this.products = products;
        this.bill = bill;
    }

    public ProductBillKey getId() {
        return id;
    }

    public void setId(ProductBillKey id) {
        this.id = id;
    }

    public Product getProduct() {
        return products;
    }

    public void setProduct(Product product) {
        this.products = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
