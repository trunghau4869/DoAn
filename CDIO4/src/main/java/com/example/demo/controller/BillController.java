package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.Comment.CommentService;
import com.example.demo.service.ProductBillService.ProductBillService;
import com.example.demo.service.User.UserService;
import com.example.demo.service.auctionuser.AuctionUserService;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes("carts")
public class BillController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductBillService productBillService;
    @Autowired
    AuctionUserService auctionUserService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CommentService commentService;

    @ModelAttribute("carts")
    public HashMap<Integer, Cart> showInfo() {
        return new HashMap<>();
    }

    @ModelAttribute("nguoiDung")
    public User getDauGia() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByAccount_UserName(auth.getName());
    }

    @RequestMapping("/")
    public String index(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        List<Category> categoryList;
        categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        model.addAttribute("MensFashion", productService.findByCategory("Đã duyệt", 1));
        model.addAttribute("WomanFashion", productService.findByCategory("Đã duyệt", 2));
        model.addAttribute("Accessory", productService.findByCategory("Đã duyệt", 3));
        model.addAttribute("Bags", productService.findByCategory("Đã duyệt", 4));
        model.addAttribute("Camera", productService.findByCategory("Đã duyệt", 5));
        model.addAttribute("FootwareMan", productService.findByCategory("Đã duyệt", 6));
        model.addAttribute("FootwareWoman", productService.findByCategory("Đã duyệt", 7));
        model.addAttribute("Health", productService.findByCategory("Đã duyệt", 8));
        model.addAttribute("Houseware", productService.findByCategory("Đã duyệt", 9));
        model.addAttribute("Laptop", productService.findByCategory("Đã duyệt", 10));
        model.addAttribute("Makeup", productService.findByCategory("Đã duyệt", 11));
        model.addAttribute("MotherAndBaby", productService.findByCategory("Đã duyệt", 12));
        model.addAttribute("Smartphone", productService.findByCategory("Đã duyệt", 13));
        model.addAttribute("Television", productService.findByCategory("Đã duyệt", 14));
        model.addAttribute("Watch", productService.findByCategory("Đã duyệt", 15));
        model.addAttribute("Sport", productService.findByCategory("Đã duyệt", 16));
        return "/nha/Home";
    }
    @RequestMapping("/afterLogin")
    public String afterLogin(Model model, Principal principal) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        List<Category> categoryList;
        categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        model.addAttribute("MensFashion", productService.findByCategory("Đã duyệt", 1));
        model.addAttribute("WomanFashion", productService.findByCategory("Đã duyệt", 2));
        model.addAttribute("Accessory", productService.findByCategory("Đã duyệt", 3));
        model.addAttribute("Bags", productService.findByCategory("Đã duyệt", 4));
        model.addAttribute("Camera", productService.findByCategory("Đã duyệt", 5));
        model.addAttribute("FootwareMan", productService.findByCategory("Đã duyệt", 6));
        model.addAttribute("FootwareWoman", productService.findByCategory("Đã duyệt", 7));
        model.addAttribute("Health", productService.findByCategory("Đã duyệt", 8));
        model.addAttribute("Houseware", productService.findByCategory("Đã duyệt", 9));
        model.addAttribute("Laptop", productService.findByCategory("Đã duyệt", 10));
        model.addAttribute("Makeup", productService.findByCategory("Đã duyệt", 11));
        model.addAttribute("MotherAndBaby", productService.findByCategory("Đã duyệt", 12));
        model.addAttribute("Smartphone", productService.findByCategory("Đã duyệt", 13));
        model.addAttribute("Television", productService.findByCategory("Đã duyệt", 14));
        model.addAttribute("Watch", productService.findByCategory("Đã duyệt", 15));
        model.addAttribute("Sport", productService.findByCategory("Đã duyệt", 16));
        return "redirect:/";
    }
    @RequestMapping("/product-detail/{id}")
    public String producDetailBill(@PathVariable int id, Model model, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap){
        Product product;
        product =  productService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            model.addAttribute("userName", auth.getName());
        }
        model.addAttribute("sanPhamBT",product);
        model.addAttribute("cartMap",cartMap);
        return "Vinh/ProductDetail";
    }
    @RequestMapping("afterLogin/product-detail/{id}")
    public String afterloginProducDetailBill(@PathVariable int id, Model model, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap){
        Product product;
        product =  productService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            model.addAttribute("userName", auth.getName());
        }
        model.addAttribute("sanPhamBT",product);
        model.addAttribute("cartMap",cartMap);
        return "redirect:/product-detail/"+id;
    }
    @GetMapping("/timKiem")
    public String search(@RequestParam("maDanhMuc") Integer maDanhMuc,
                         @RequestParam("tenSp") String tenSp, Model model) {
        List<Category> categories = categoryService.findAll();
        List<Product> products;
        if (maDanhMuc != 0) {
            if (!tenSp.equals("")) {
                products = productService.findByDanhMucTenSanPham("Đã duyệt", maDanhMuc, tenSp);
            } else {
                products = productService.findByDanhMuc("Đã duyệt", maDanhMuc);
            }
        } else {
            if (!tenSp.equals("")) {
                products = productService.findByNameDaDuyet("Đã duyệt", tenSp);
            } else {
                products = productService.findByDaDuyet("Đã duyệt");
            }
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        if (categories.size() == 0 || products.size() == 0) {
            model.addAttribute("danhmucs", categories);
            model.addAttribute("listSP", products);
            model.addAttribute("mgskt", "ko tìm thay");
            return "/nha/Home";
        } else {
            model.addAttribute("danhmucs", categories);
            model.addAttribute("listSP", products);
            model.addAttribute("mgs", "Danh sách sp tìm thấy");
            return "/nha/Home";
        }
    }

}
