package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DiscountProductKey implements Serializable {
    @Column(name = "id_discount")
    private int idDiscount;
    @Column(name = "id_product")
    private int idProduct;

    public DiscountProductKey() {
    }

    public DiscountProductKey(int idDiscount, int idProduct) {
        this.idDiscount = idDiscount;
        this.idProduct = idProduct;
    }

    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
