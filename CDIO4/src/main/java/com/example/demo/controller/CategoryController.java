package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    UserRepo userRepo;

    @GetMapping(value = "/danhmuc/list")
    private String viewList(Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/nha/category/list";
    }
    @GetMapping(value = "/danhmuc/create")
    private String viewCreate(Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("danhmucs", new Category());
        return "/nha/category/create";
    }

    @PostMapping(value = "/danhmuc/create")
    private String Create(@Valid @ModelAttribute("danhmucs") Category category, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            return "/nha/category/create";
        }
        List<Category> categoryList = categoryService.findByName(category.getCategoryName());

        if ( categoryList.size() != 0) {
            model.addAttribute("mgsdm", "Danh mục đã tồn tại.");
            return "/nha/category/create";
        }


        categoryService.create(category);
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("mgsedit", "Thêm mới danh mục thành công.");
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/nha/category/list";
    }

    @GetMapping(value = "/danhmuc/edit")
    public String ViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("danhmucs", categoryService.findById(id));
        return "/nha/category/edit";
    }

    @PostMapping(value = "/danhmuc/edit")
    public String Edit(Category category, Model model, Principal principal) {
        this.categoryService.create(category);
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("mgsedit", "Sửa danh mục thành công.");
        model.addAttribute("danhmucs", categoryService.findAll());
        return "/nha/category/list";
    }
    @GetMapping(value = "/danhmuc/delete/{maDanhMuc}")
    public String delete(@PathVariable int maDanhMuc, Model model, Principal principal) {
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user);
        this.categoryService.delete(maDanhMuc);
        model.addAttribute("mgsedit", "Xóa danh mục thành công.");
        return "redirect:/admin/danhmuc/list";
    }
}
