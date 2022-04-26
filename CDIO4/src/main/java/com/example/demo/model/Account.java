package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Account {
    @Id
    @Column(name = "userName")
    private String userName;
    private String passWord;
    private String rePassWord;
    private boolean status;
    private String description;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "userName"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;
    @OneToOne(mappedBy = "account")
    private User user;
    @OneToMany(mappedBy = "accounts")
    private Set<Product> products;
    public Account() {
    }

    public Account(String userName, String passWord, boolean status, String description) {
        this.userName = userName;
        this.passWord = passWord;
        this.status = status;
        this.description = description;
    }

    public Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
    public Account(String userName) {
        this.userName = userName;
    }

    public Account(String userName, String passWord, Set<Role> roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.roles = roles;
    }

    public Account(boolean status) {
        this.status = status;
    }


    public Account(String userName, String passWord, String description, Set<Role> roles, boolean status) {
        this.userName = userName;
        this.passWord = passWord;
        this.description = description;
        this.roles = roles;
        this.status = status;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRePassWord() {
        return rePassWord;
    }

    public void setRePassWord(String rePassWord) {
        this.rePassWord = rePassWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
