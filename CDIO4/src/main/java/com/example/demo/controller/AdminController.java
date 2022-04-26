package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping(value="")
    public String AdminTrangchu(Model model, Principal principal) {
        // xet duyet account
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        return "/nha/admin/HomeAdmin";
    }

    @GetMapping(value = "/list")
    public String AdminList(@RequestParam(value = "page", defaultValue = "1") int page, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        //tim kiem phan trang theo ten san pham
        Sort sort = Sort.by("productName").descending();
        model.addAttribute("sanphams1", productService.findAllManure(PageRequest.of(page, 10, sort)));
        return "/nha/admin/ListProduct";
    }

    @GetMapping(value = "/view")
    public String AdminView(@RequestParam("id") int id, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("sanphams", productService.findById(id));
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/nha/admin/ViewProduct";
    }
    @GetMapping(value = "/{maSanPham}/deleteSanPham")
    public String delete(@PathVariable int maSanPham , @ModelAttribute("sanphams") Product product,Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        System.out.println("mã sp là :----------------------------" + maSanPham);
        productService.delete(maSanPham);
        return "redirect:/admin/list";
    }
    @GetMapping(value = "/duyet")
    public String AdminDuyet(Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("sanphams1", productService.findByTinhTrang("Chưa duyệt"));
        return "nha/admin/DuyetProduct";
    }
    @GetMapping(value = "/delete1/{maSanPham}")
    public String deletechuaduyejt(@PathVariable int maSanPham, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        this.productService.delete(maSanPham);
        return "redirect:/admin/duyet";
    }
    @PostMapping(value = "/duyetok")
    public String AdminCreate(@RequestParam("submit") String submit, Product product, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (submit.equals("duyet")) {
            User user = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("nguoiDung", user);
            product.setStatus("Đã duyệt");
            this.productService.create(product);
            redirectAttributes.addFlashAttribute("mgs1", "Phê duyệt sản phẩm thành công!");
            return "redirect:/admin/duyet";
        } else {
            User user = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("nguoiDung", user);
            product.setStatus("Không duyệt");
            this.productService.create(product);
            return "redirect:/admin/duyet";
        }
    }

    @GetMapping(value = "/edit")
    public String AdminViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("sanphams", productService.findById(id));
        model.addAttribute("danhmucs", categoryService.findAll());
        model.addAttribute("chuaduyet", "Chưa duyệt");
        model.addAttribute("daduyet", "Đã duyệt");
        model.addAttribute("khongduyet", "Không duyệt");
        return "/nha/admin/EditProduct";
    }

    @PostMapping(value = "/edit")
    public String AdminEdit(@ModelAttribute("sanphams") Product product, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        this.productService.create(product);
        redirectAttributes.addFlashAttribute("mgs2", "sửa sản phẩm thành công!");
        return "redirect:/admin/list";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam("tensanpham") String tenSanPham, Model model, Principal principal) {
        List<Product> sanPhams = productService.findByName(tenSanPham);
        if (sanPhams.size() == 0) {
            model.addAttribute("sanphams1", sanPhams);
            model.addAttribute("mgs", "khomg tim thay sp");
            return "/nha/admin/ListProduct";
        } else {
            User user = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("nguoiDung", user);
            Sort sort = Sort.by("tenSanPham").descending();
//            model.addAttribute("sanphams1", sanPhamService.findByNameadmin(tenSanPham);
            model.addAttribute("sanphams1", sanPhams);
            return "/nha/admin/ListProduct";
        }
    }

    @GetMapping(value = "/search_duyet")
    public String search_duyet(@RequestParam("tensanpham") String tenSanPham, Model model) {
        List<Product> sanPhams = productService.findByNameDaDuyet1("Chưa duyệt", tenSanPham);
        if (sanPhams.size() == 0) {
            model.addAttribute("sanphams1", sanPhams);
            model.addAttribute("mgs", "khomg tim thay sp");
            return "/nha/admin/DuyetProduct";
        } else {
            model.addAttribute("sanphams1", sanPhams);
            return "/nha/admin/DuyetProduct";
        }
    }
}
