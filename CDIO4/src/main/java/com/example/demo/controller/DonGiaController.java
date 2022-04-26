package com.example.demo.controller;

import com.example.demo.model.ProductBill;
import com.example.demo.service.ProductBillService.BillService;
import com.example.demo.service.ProductBillService.ProductBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/nhas")
public class DonGiaController {
    @Autowired
    ProductBillService productBillService;
    @Autowired
    BillService billService;

    @RequestMapping("/dongia")
    public ModelAndView listAll(@RequestParam(defaultValue = "0") int page){
        ModelAndView model = new ModelAndView("/nha/DonGia");
        Page<ProductBill> productBillList;
        Pageable pageable = PageRequest.of(page, 5);
        productBillList = productBillService.findAll(pageable);
        model.addObject("listDonGia",productBillList);
        return model;
    }

    @PostMapping("/pagaList")
    public ModelAndView getList(@RequestParam(defaultValue = "0") int page, @RequestParam String tenNguoiDung,
                                @RequestParam String tenSanPham) {
        ModelAndView modelAndView = new ModelAndView("/nha/DonGia");
        Page<ProductBill> productBills;
        Pageable pageableSort = PageRequest.of(page, 999);
        if (tenNguoiDung.equals("")) {
            if (!tenSanPham.equals("")) {
                productBills = productBillService.findByProduct_ProductNameContains(tenSanPham, pageableSort);
            } else {
                productBills = productBillService.findAll(pageableSort);
            }
        } else {
            if (!tenSanPham.equals("")) {
                productBills = productBillService.findByProduct_ProductNameAndBill_User_NameContains(tenNguoiDung, tenSanPham, pageableSort);
            } else {
                productBills = productBillService.findByBill_User_NameContains(tenNguoiDung, pageableSort);
            }
        }
        modelAndView.addObject("tenNguoiDung", tenNguoiDung);
        modelAndView.addObject("listDonGia", productBills);
        modelAndView.addObject("tenSanPham", tenSanPham);
        return modelAndView;
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        billService.remove(id);
        return "redirect:/nhas/dongia";
    }

}
