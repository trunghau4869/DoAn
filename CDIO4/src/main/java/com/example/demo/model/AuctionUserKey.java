package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuctionUserKey implements Serializable {
    @Column(name = "id_auction")
    private int idAuction;
    @Column(name = "id_user")
    private int idUser;

    public AuctionUserKey() {
    }

    public AuctionUserKey(int idAuction, int idUser) {
        this.idAuction = idAuction;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

}
