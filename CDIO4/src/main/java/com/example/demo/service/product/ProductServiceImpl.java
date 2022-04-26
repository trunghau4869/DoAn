package com.example.demo.service.product;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Override
    public List<Product> findByCategory(String status, int idCategory) {
        return productRepo.findByStatusAndCategory_IdCategoryOrderByPrice(status,idCategory);
    }

    @Override
    public List<Product> findByApproved() {
        return productRepo.findByApproved();
    }

    @Override
    public void create(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product findById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public List<Product> findProduct(String idUser) {
        return productRepo.findProduct(idUser);
    }

    @Override
    public Page<Product> findAllManure(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public void delete(int maSanPham) {
        productRepo.deleteById(maSanPham);
    }

    @Override
    public List<Product> findByTinhTrang(String tinhTrang) {
        return productRepo.findAllByTinhTrangContaining(tinhTrang);
    }
    @Override
    public List<Product> findAllByChuaDuyet(String status, String userName) {
        return productRepo.findAllByChuaDuyet(status , userName);
    }

    @Override
    public List<Product> findByName(String tenSanPham) {
        return productRepo.findByProductNameContains(tenSanPham);
    }

    @Override
    public List<Product> findByNameDaDuyet1(String tinhtrang, String tenSanPham) {
        return productRepo.findByStatusAndProductNameContains(tinhtrang,tenSanPham);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findByDanhMucTenSanPham(String tinhTrang, Integer maDanhMuc, String tenSp) {
        return productRepo.findByStatusAndCategory_IdCategoryAndProductNameContainsOrderByPrice(tinhTrang,maDanhMuc,tenSp);
    }

    @Override
    public List<Product> findByDanhMuc(String tinhtrang, Integer maDanhMuc) {
        return productRepo.findByStatusAndCategory_IdCategoryOrderByPrice(tinhtrang,maDanhMuc);
    }

    @Override
    public List<Product> findByNameDaDuyet(String tinhtrang, String tenSp) {
        return productRepo.findByStatusAndProductNameContains(tinhtrang,tenSp);
    }

    @Override
    public List<Product> findByDaDuyet(String tinhtrang) {
        return productRepo.findAllDaDuyet(tinhtrang);
    }
    @Override
    public List<Product> findCuaBan(String idUser) {
        return productRepo.findCuaBan(idUser);
    }

    @Override
    public List<Product> findBytensanphamchuaduyetcuaban(String status, String user, String tenSp) {
        return productRepo.findBytensanphamchuaduyetcuaban(status , user , tenSp);
    }

    @Override
    public List<Product> findBytensanphamcuaban(String user, String tenSp) {
        return productRepo.findBytensanphamcuaban(user , tenSp);
    }
}
