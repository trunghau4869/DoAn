package com.example.demo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Vinh")
public class Vinhcontroller {
    @RequestMapping("/productDetail")
    public String productDetail(){
        return "/Vinh/ProductDetail";
    }
    @RequestMapping("/gioHang")
    public String gioHang(){
        return "/Vinh/CartPage";
    }

    @RequestMapping("/errorPage")
    public String errorPage(){
        return "/Vinh/ErrorPage";
    }
}
