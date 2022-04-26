package com.example.demo.service.ProductBillService;

import com.example.demo.model.ProductBill;
import com.example.demo.repository.ProductRepo.ProductBillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBillServiceImpl implements ProductBillService {
    @Autowired
    ProductBillRepo productBillRepo;
    @Override
    public Page<ProductBill> findAll(Pageable pageable) {
        return productBillRepo.findAll(pageable);
    }
    @Override
    public Page<ProductBill> findByProduct_ProductNameContains(String tenSanPham, Pageable pageable) {
        return productBillRepo.findByProduct_ProductNameContains(tenSanPham,pageable);
    }
    @Override
    public Page<ProductBill> findByProduct_ProductNameAndBill_User_NameContains(String tenNguoiDung, String tenSanPham, Pageable pageableSort) {
        return productBillRepo.findByProduct_ProductNameAndBill_User_NameContains(tenSanPham,tenNguoiDung,pageableSort);
    }
    @Override
    public Page<ProductBill> findByBill_User_NameContains(String tenNguoiDung, Pageable pageableSort) {
        return productBillRepo.findByBill_User_NameContains(tenNguoiDung,pageableSort);
    }

}
