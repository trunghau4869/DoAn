package com.example.demo.service.ProductBillService;

import com.example.demo.model.Bill;
import com.example.demo.model.ProductBill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {
    List<Bill> findBills(int id);
    void remove(int id);
    void create(Bill bill);
    void createChiTiet(ProductBill productBill);
}
