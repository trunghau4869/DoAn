package com.example.demo.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_bill")
    private int inBill;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    private User user;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    Set<ProductBill> productBills;


    private String dateOder;
    private String addressOder;
    private String status;
    private double totalCost;

    public Bill() {
    }

    public int getInBill() {
        return inBill;
    }

    public void setInBill(int inBill) {
        this.inBill = inBill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ProductBill> getProductBills() {
        return productBills;
    }

    public void setProductBills(Set<ProductBill> productBills) {
        this.productBills = productBills;
    }

    public String getDateOder() {
        return dateOder;
    }

    public void setDateOder(String dateOder) {
        this.dateOder = dateOder;
    }

    public String getAddressOder() {
        return addressOder;
    }

    public void setAddressOder(String addressOder) {
        this.addressOder = addressOder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
