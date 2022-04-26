package com.example.demo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class VuongController {
    @RequestMapping("/thongtin")
    public String register(){
        return "vuong/thong_tin";
    }
}
