package com.example.demo.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "auction_user")
public class AuctionUser {
    @EmbeddedId
    private AuctionUserKey id;

    @ManyToOne
    @MapsId("idAuction")
    @JoinColumn(name = "idAuction")
    private Auction auction;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
    private User user;

    private double startingPrice;
    private Time auctionEndTime;

    public AuctionUser() {
    }

    public AuctionUser(AuctionUserKey id, Auction auction, User user) {
        this.id = id;
        this.auction = auction;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Time getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Time auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public AuctionUserKey getId() {
        return id;
    }

    public void setId(AuctionUserKey id) {
        this.id = id;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

}
