package com.example.demo.model;

import javax.persistence.*;

@Entity
public class DiscountProduct {
    @EmbeddedId
    private DiscountProductKey id;

    @ManyToOne
    @MapsId("idDiscount")
    @JoinColumn(name = "id_discount")
    private Discount discount;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    private String discountName;
    private float discountRatio;

    public DiscountProduct() {
    }

    public DiscountProduct(DiscountProductKey id, Discount discount, Product product) {
        this.id = id;
        this.discount = discount;
        this.product = product;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public float getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(float discountRatio) {
        this.discountRatio = discountRatio;
    }

    public DiscountProductKey getId() {
        return id;
    }

    public void setId(DiscountProductKey id) {
        this.id = id;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
