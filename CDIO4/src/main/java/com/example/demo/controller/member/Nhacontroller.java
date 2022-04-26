package com.example.demo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class Nhacontroller {
    @RequestMapping("/register")
    public String register(){
        return "nha/register";
    }
    @RequestMapping("/dongia")
    public String donGia(){
        return "/nha/DonGia";
    }
    @RequestMapping("/HomeAdmin")
    public String Home(){
        return "AccountAdmin";
    }
    @RequestMapping("/Paynha")
    public String Pay(){
        return "Vinh/Pay";
    }
    @RequestMapping("/Demo")
    public String Demo(){return "nha/admin/Demo";}
    @RequestMapping("/Demo1")
    public String Demo1(){
        return "HeaderFooter/Header";
    }
}
