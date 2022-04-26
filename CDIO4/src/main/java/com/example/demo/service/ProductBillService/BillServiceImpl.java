package com.example.demo.service.ProductBillService;

import com.example.demo.model.Bill;
import com.example.demo.model.ProductBill;
import com.example.demo.repository.ProductRepo.BillRepo;
import com.example.demo.repository.ProductRepo.ProductBillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepo billRepo;
    @Autowired
    ProductBillRepo productBillRepo;

    @Override
    public List<Bill> findBills(int id) {
        return billRepo.findBills(id);
    }

    @Override
    public void remove(int id) {
         billRepo.deleteById(id);
    }

    @Override
    public void create(Bill bill) {
        billRepo.save(bill);
    }

    @Override
    public void createChiTiet(ProductBill productBill) {
        productBillRepo.save(productBill);
    }
}
