package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAuction")
    private int idAuction;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idProduct",referencedColumnName = "id_product")
    private Product product;
    @OneToMany(mappedBy = "auction", cascade = {CascadeType.ALL,CascadeType.REMOVE})
    Set<AuctionUser> auctionUsers;

    public Auction() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

    public Set<AuctionUser> getAuctionUsers() {
        return auctionUsers;
    }

    public void setAuctionUsers(Set<AuctionUser> auctionUsers) {
        this.auctionUsers = auctionUsers;
    }
}
