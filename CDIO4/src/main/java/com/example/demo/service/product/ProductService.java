package com.example.demo.service.product;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findByCategory(String status, int idCategory);

    List<Product> findByApproved();

    void create(Product product);
    Product findById(int id);

    //sản phẩm của người dùng
    List<Product> findProduct(String idUser);

    Page<Product> findAllManure(Pageable pageable);

    void delete(int maSanPham);

    List<Product> findByTinhTrang(String tinhTrang);

    List<Product> findByName(String tenSanPham);

    List<Product> findByNameDaDuyet1(String tinhtrang, String tenSanPham);

    List<Product> findAll();

    List<Product> findByDanhMucTenSanPham(String tinhTrang, Integer maDanhMuc, String tenSp);

    List<Product> findByDanhMuc(String tinhtrang, Integer maDanhMuc);

    List<Product> findByNameDaDuyet(String tinhtrang, String tenSp);

    List<Product> findByDaDuyet(String tinhtrang);

    //sản phẩm của người dùng


    List<Product> findAllByChuaDuyet(String status , String userName);

    List<Product> findCuaBan(String idUser);

    List<Product> findBytensanphamchuaduyetcuaban(String status, String user, String tenSp);

    List<Product> findBytensanphamcuaban(String user, String tenSp);

}
