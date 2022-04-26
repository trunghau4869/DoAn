package com.example.demo.repository.ProductRepo;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("select e from Product e where e.status = ?1")
    List<Product> findByApproved();

    List<Product> findByStatusAndCategory_IdCategoryOrderByPrice(String statis,int category);

    @Query("select e from  Product e where e.accounts.userName= ?1")
    List<Product> findProduct(String idUser);

    @Query("select e from  Product e where e.status = ?1")
    List<Product> findAllByTinhTrangContaining(String tinhTrang);

    List<Product> findByProductNameContains(String tenSanPham);


    List<Product> findByStatusAndProductNameContains(String tinhTrang,String tenSanPham);

    @Query("select e from  Product e where e.status = ?1 and e.category.idCategory = ?2 and e.productName like %?3%")
        List<Product> findByStatusAndCategory_IdCategoryAndProductNameContainsOrderByPrice(String tinhTrang, Integer maDanhMuc, String tenSp);

    @Query("select e from  Product e where e.status = ?1 and e.category.idCategory = ?2 ")
    List<Product> findByStatusAndCategory_IdCategoryOrderByPrice(String tinhtrang, Integer maDanhMuc);

    @Query("select e from  Product e where e.status = ?1")
    List<Product> findAllDaDuyet(String tinhtrang);

    @Query("select p from Product p where p.status = ?1 and p.accounts.userName = ?2")
    List<Product> findAllByChuaDuyet(String status , String userName);

    @Query("select e from  Product e where e.accounts.userName= ?1")
    List<Product> findCuaBan(String idUser);

    @Query("select e from  Product e where e.status = ?1  and e.accounts.userName = ?2 and e.productName like %?3%")
    List<Product> findBytensanphamchuaduyetcuaban(String status, String user, String tenSp);

    @Query("select e from  Product e where e.accounts.userName= ?1 and e.productName like %?2%")
    List<Product> findBytensanphamcuaban(String user, String tenSp);
}
