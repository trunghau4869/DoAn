package com.example.demo.service.ProductBillService;

import com.example.demo.model.ProductBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductBillService {
    Page<ProductBill> findAll(Pageable pageable);

    Page<ProductBill> findByProduct_ProductNameContains(String tenSanPham, Pageable pageable);

    Page<ProductBill> findByProduct_ProductNameAndBill_User_NameContains(String tenNguoiDung, String tenSanPham, Pageable pageableSort);

    Page<ProductBill> findByBill_User_NameContains(String tenNguoiDung, Pageable pageableSort);
}
