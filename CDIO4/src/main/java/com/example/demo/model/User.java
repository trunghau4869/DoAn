package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String name;
    private boolean sex;
    private String DateTime;
    private String gmail;
    private int numberCard;
    private String address;
    private String phoneUser;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "userName",referencedColumnName = "userName")
    private Account account;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    Set<AuctionUser> auctionUsers;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Bill> bills;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_user_product",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idComment")
    )
    private Set<Comment> commentSet;


    public User(int idUser, String name, boolean sex, String dateTime, String gmail, int numberCard, String address, String phoneUser, Account account, Set<AuctionUser> auctionUsers, Set<Bill> bills) {
        this.idUser = idUser;
        this.name = name;
        this.sex = sex;
        this.DateTime = dateTime;
        this.gmail = gmail;
        this.numberCard = numberCard;
        this.address = address;
        this.phoneUser = phoneUser;
        this.account = account;
        this.auctionUsers = auctionUsers;
        this.bills = bills;
    }

    public User() {
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Set<AuctionUser> getAuctionUsers() {
        return auctionUsers;
    }

    public void setAuctionUsers(Set<AuctionUser> auctionUsers) {
        this.auctionUsers = auctionUsers;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(int numberCard) {
        this.numberCard = numberCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
