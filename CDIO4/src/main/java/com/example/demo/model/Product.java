package com.example.demo.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
public class Product implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_product")
    private int idProduct;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "idCategory", referencedColumnName = "idCategory")
    private Category category;

    @OneToOne(mappedBy = "product")
    private Auction auction;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    Set<ProductBill> productBills;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    Set<DiscountProduct> discountProducts;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Account accounts;

    @OneToMany(mappedBy = "product")
    private Set<Comment> comments;

    @NotEmpty(message = "Vui lòng nhập tên sản phẩm.")
    private String productName;
    @Min(value = 1000, message = "Giá khởi điểm vui lòng nhập lớn hơn 1000đ.")
    private double price;
    private String image;
    private String image1;
    private String image2;
    @Column(length = 2000)
    @NotEmpty(message = "Vui lòng nhập mô tả")
    private String description;
    private String dateAuction;
//    private String dateEndAuction;
//    private String startAuction;
    private int quantity;
    private String status;

    public Product() {
    }


    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    public Account getAccounts() {
        return accounts;
    }

    public void setAccounts(Account accounts) {
        this.accounts = accounts;
    }

    public Set<ProductBill> getProductBills() {
        return productBills;
    }

    public void setProductBills(Set<ProductBill> productBills) {
        this.productBills = productBills;
    }

    public Set<DiscountProduct> getDiscountProducts() {
        return discountProducts;
    }

    public void setDiscountProducts(Set<DiscountProduct> discountProducts) {
        this.discountProducts = discountProducts;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAuction() {
        return dateAuction;
    }

    public void setDateAuction(String dateAuction) {
        this.dateAuction = dateAuction;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public String getDateEndAuction() {
//        return dateEndAuction;
//    }
//
//    public void setDateEndAuction(String dateEndAuction) {
//        this.dateEndAuction = dateEndAuction;
//    }
//
//    public String getStartAuction() {
//        return startAuction;
//    }
//
//    public void setStartAuction(String startAuction) {
//        this.startAuction = startAuction;
//    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        String dateAuction = product.getDateAuction();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        try {
            date1 = format.parse(dateAuction);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
