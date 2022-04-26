package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.User.UserService;
import com.example.demo.service.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AccountAdminController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepo userRepo;
    @RequestMapping("/account")
    public String listAll(@RequestParam(defaultValue = "0") int page, Principal principal, Model model){
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("user",user);
        Page<User> userPage;
        Pageable pageable = PageRequest.of(page,5);
        userPage = userService.findAll(pageable);
        model.addAttribute("nguoidungs",userPage);
        return "/nha/admin/AccountAdmin";
    }
    @PostMapping("/filter")
    public String getList(@RequestParam(defaultValue = "0") int page,@RequestParam String nameUser,@RequestParam String addRess,
                          Principal principal, Model model)
    {
        Page<User> users;
        Pageable pageableSort = PageRequest.of(page, 5);
         //tim kiem
        if (nameUser.equals("")) {
            if (!addRess.equals("")) {
                users = userService.findByDiaChi(addRess, pageableSort);
            } else {
                users = userService.findAll(pageableSort);
            }
        } else {
            if (!addRess.equals("")) {
                users = userService.findByTenNguoiDungAndDiaChi(nameUser, addRess, pageableSort);
            } else {
                users = userService.findByTenNguoiDung(nameUser, pageableSort);
            }
        }
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoidungs", users);
        model.addAttribute("tenNguoiDung", nameUser);
        model.addAttribute("diaChi",addRess);
        model.addAttribute("nguoidung",user);
        return "/nha/admin/AccountAdmin";
    }
    @GetMapping("/add_member")
    public String create(Model model, Principal principal) {
        User user1 = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user1);
        model.addAttribute("nguoiDung1", new User());
        return "nha/admin/AccountAdd";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirAttrs) {
        userService.remove(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/account";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("nguoiDung1") User users, RedirectAttributes redirAttrs, Model model, Principal principal) {
        System.out.println(users.getIdUser());
        userService.save(users);
        User user = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoidung", user);
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/account";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, Principal principal) {
        User user = userService.findById(id);
        User user1 = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user1);
        model.addAttribute("nguoiDung1", userService.findById(id));
        model.addAttribute("tenNguoiDung", user.getName());
        return "nha/admin/AccountEdit";
    }

    @PostMapping("/edit_member")
    public String edit(@ModelAttribute("nguoiDung1") User user, Model model, RedirectAttributes redirAttrs, Principal principal) {
        userService.save(user);
        User user1 = userRepo.findByAccount_UserName(principal.getName());
        model.addAttribute("nguoiDung", user1);
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        model.addAttribute("tenNguoiDung", user.getName());
        return "redirect:/admin/account";
    }


}
