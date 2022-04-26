package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.Account.AccountRoleService;
import com.example.demo.service.Account.AccountSerivce;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductService productService;
    @Autowired
    AccountSerivce accountSerivce;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AccountRoleService accountRoleService;

    @GetMapping(value = "/product/list")
    public String NguoiDung(Model model, Principal principal, @PageableDefault(size = 5) Pageable pageable) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listProduct", productService.findProduct(userName));
        return "/vuong/sanpham/ListProductSaler";
    }

    @GetMapping(value = "/product/listduyet")
    public String nguoiDungChuaDuyet(Product product , Model model , Principal principal){
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listSP", productService.findAllByChuaDuyet("Chưa duyệt", userName));
        return "/vuong/sanpham/listchuaduyet";
    }

    @GetMapping(value = "/product/khongduyet")
    public String nguoiDungKhongDuyet(Product product, Model model, Principal principal){
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listSP", productService.findAllByChuaDuyet("Không duyệt", userName));
        return "/vuong/sanpham/listkhongduyet";
    }

    @GetMapping(value = "/product/create")
    public String viewCreate(Model model, Principal principal){
        LocalDate localDate = LocalDate.now();
        Product product = new Product();
        product.setDateAuction(localDate.toString());
        model.addAttribute("products", product);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("usernams", principal.getName());
        model.addAttribute("taiKhoans", accountSerivce.findAll());
        model.addAttribute("danhmucs", categoryService.findAll());
        model.addAttribute("chuaduyet", "Chưa duyệt");
        return "/vuong/sanpham/create_nguoidung";
    }

    @PostMapping(value = "/product/create")
    public String create(@Valid @ModelAttribute("products") Product product , BindingResult bindingResult , Model model , Principal principal){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        System.out.println(LocalDateTime.now());
        String dateFormat = format.format(date);
        System.out.println("create product" + product);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        product.setStatus("Chưa duyệt");
        product.setDateAuction(dateFormat);
        product.setAccounts(new Account(userName));
        new Product().validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            User user = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("nguoiDung", user);
            model.addAttribute("usernams", principal.getName());
            model.addAttribute("taiKhoans", accountSerivce.findAll());
            model.addAttribute("danhmucs", categoryService.findAll());
            model.addAttribute("chuaduyet", "Chưa duyệt");
            return "/vuong/sanpham/create_nguoidung";
        }
        this.productService.create(product);
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listSP", productService.findCuaBan(userName));
        model.addAttribute("mgs", "thêm mới sản phẩm thành công");
        return "/vuong/sanpham/ListProductSaler";
    }

    @GetMapping(value = "product/edit")
    public String viewEdit(@RequestParam("id") Integer id, Model model , Principal principal){
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("sanphams", productService.findById(id));
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/vuong/sanpham/edit";
    }

    @GetMapping(value = "/product/view")
    public String viewView(@RequestParam("id") Integer id, Model model, Principal principal) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("admin", "là admin");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("sanphams", productService.findById(id));
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/vuong/sanpham/view";
    }

    @PostMapping(value = "/product/edit")
    public String Edit(@Valid @ModelAttribute("sanphams") Product product, BindingResult bindingResult, Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        new Product().validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("nguoiDung", user);
            model.addAttribute("danhmucs", categoryService.findAll());
            return "/vuong/sanpham/edit";
        }
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listSP", productService.findCuaBan(userName));
        model.addAttribute("danhmucs", categoryService.findAll());
//        System.out.println("ten -----------" + product.getAccounts().getUserName());
        this.productService.create(product);
        model.addAttribute("mgsedit", "Sửa sản phẩm thành công");
        System.out.println("userName ------ " + product.getAccounts());
        System.out.println("ten -----------" + product.getDateAuction());
        System.out.println("ten -----------" + product.getStatus());
        System.out.println("ten -----------" + product.getProductName());

        return "/vuong/sanpham/ListProductSaler";
    }

    @GetMapping(value = "/product/delete/{maSanPham}")
    public String deleteSanpham(@PathVariable Integer maSanPham, Model model, Principal principal) {
        this.productService.delete(maSanPham);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("listProduct", productService.findCuaBan(userName));
        model.addAttribute("mgsdelete", "Xóa sản phẩm thành công!");
        return "/vuong/sanpham/ListProductSaler";
    }


    @GetMapping("product/timKiemchoduyet")
    public String searchchoduyet(@RequestParam("tenSp") String tenSp, Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        List<Product> products = productService.findBytensanphamchuaduyetcuaban("Chưa duyệt", userName, tenSp);
        if (products.size() == 0) {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + products.size());
            model.addAttribute("nguoiDung", user);
            model.addAttribute("listSP", products);
            model.addAttribute("mgstk", "Không tìm thấy sản phẩm");
            return "/vuong/sanpham/listchuaduyet";
        } else {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + products.size());
            model.addAttribute("nguoiDung", user);
            model.addAttribute("listSP", products);
            model.addAttribute("mgstk1", "sản phẩm được tìm thấy");
            return "/vuong/sanpham/listchuaduyet";
        }
    }

    @GetMapping("/timKiemcuatoi")
    public String search(@RequestParam("tenSp") String tenSp,
                         Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SALER"))) {
            model.addAttribute("saler", "là saler");
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        List<Product> products = productService.findBytensanphamcuaban(userName, tenSp);
        if (products.size() == 0) {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + products.size());
            model.addAttribute("nguoiDung", user);
            model.addAttribute("listSP", products);
            model.addAttribute("mgstk", "Không tìm thấy sản phẩm");
            return "/vuong/sanpham/ListProductSaler";
        } else {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + products.size());
            model.addAttribute("nguoiDung", user);
            model.addAttribute("listSP", products);
            model.addAttribute("mgstk1", "sản phẩm được tìm thấy");
            return "/vuong/sanpham/ListProductSaler";
        }
    }

}
