package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comment")
    private int idComment;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "idProduct", referencedColumnName = "id_product")
    private Product product;
    @Column(length = 2000)
    private String Content;

    @ManyToMany(mappedBy = "commentSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> userSet;

    public Comment() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
