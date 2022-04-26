package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_discount")
    private int idDiscount;
    private String discountName;
    private boolean status;
    private String discountEndDay;
    private String discountStartDay;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    Set<DiscountProduct> discountProducts;

    public Discount() {
    }

    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDiscountEndDay() {
        return discountEndDay;
    }

    public void setDiscountEndDay(String discountEndDay) {
        this.discountEndDay = discountEndDay;
    }

    public String getDiscountStartDay() {
        return discountStartDay;
    }

    public void setDiscountStartDay(String discountStartDay) {
        this.discountStartDay = discountStartDay;
    }

    public Set<DiscountProduct> getDiscountProducts() {
        return discountProducts;
    }

    public void setDiscountProducts(Set<DiscountProduct> discountProducts) {
        this.discountProducts = discountProducts;
    }
}
