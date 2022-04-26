package com.example.demo.repository.ProductRepo;


import com.example.demo.model.ProductBill;
import com.example.demo.model.ProductBillKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductBillRepo extends JpaRepository<ProductBill, ProductBillKey> {
    Page<ProductBill> findAll(Pageable pageable);
    Page<ProductBill> findByBill_User_NameContains(String userName , Pageable pageable);
    Page<ProductBill> findByProduct_ProductNameAndBill_User_NameContains(String productName,String userName, Pageable pageable);

    Page<ProductBill> findByProduct_ProductNameContains(String productName,Pageable pageable);
}
