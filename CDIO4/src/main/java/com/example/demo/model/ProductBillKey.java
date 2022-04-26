package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductBillKey implements Serializable {
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "in_bill")
    private int inBill;

    public ProductBillKey() {
    }

    public ProductBillKey(int idProduct, int inBill) {
        this.idProduct = idProduct;
        this.inBill = inBill;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getInBill() {
        return inBill;
    }

    public void setInBill(int inBill) {
        this.inBill = inBill;
    }
}
