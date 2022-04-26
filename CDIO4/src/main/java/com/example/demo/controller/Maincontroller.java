package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.AccountUser;
import com.example.demo.model.Role;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.Account.AccountRoleService;
import com.example.demo.service.Account.AccountSerivce;
import com.example.demo.service.User.UserService;
import com.example.demo.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("")
public class Maincontroller {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService userService;

    @Autowired
    UserRepo userRepo;
    @Autowired
    public AccountSerivce accountSerivce;
    @Autowired
    AccountRoleService accountRoleService;




    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        // 1 cái util( dùng chung) dùng để hiển thị principal
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "/nha/userInfoPage";
    }
    @GetMapping(value = "/register")
    public String viewsingup1(Model model) {
        model.addAttribute("dangkys", new AccountUser());
        return "/nha/register";
    }
    @PostMapping(value = "/register")
    public String singUp(@Valid @ModelAttribute("dangkys") AccountUser accountUser, BindingResult bindingResult, Model model) {
        new AccountUser().validate(accountUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/nha/register";
        }
        Account account = accountSerivce.findById(accountUser.getUserName1());
        List<com.example.demo.model.User> email = userService.findByEmail(accountUser.getGmail1());
        if (account != null && email.size() != 0) {
            model.addAttribute("errTK", "Ten tai khoan da ton tai");
            model.addAttribute("errEmail", "Email da ton tai");
            return "/nha/register";
        }
        if (account != null) {
            model.addAttribute("errTK", "Ten tai khoan da ton tai");
            return "/nha/register";
        }
        if (email.size() != 0) {
            model.addAttribute("errEmail", "Email da ton tai");
            return "/nha/register";
        }
        Set<Role> roles = accountRoleService.findname("ROLE_CUSTOMER") ;
        System.out.println("quyền là  " + roles);
        com.example.demo.model.User user = new com.example.demo.model.User();
        user.setName(accountUser.getName1());
        Account account1 =new Account(accountUser.getUserName1(), bCryptPasswordEncoder.encode(accountUser.getPassWord1()),
                "Active", roles,true);
        user.setAccount(account1);
        user.setGmail(accountUser.getGmail1());
        user.setDateTime(accountUser.getDateTime1());
        user.setPhoneUser(accountUser.getPhoneUser1());
        user.setNumberCard(accountUser.getNumberCard1());
        user.setAddress(accountUser.getAddress1());
        user.setSex(accountUser.isSex1());
        userService.save(user);
        System.out.println("nguoi dun  ==========" + user);
        return "redirect:/login";
    }

    @GetMapping("/403")
    private String accessDenied(Model model, Principal principal) {
        com.example.demo.model.User user = userService.findByUser_User(principal.getName());
        model.addAttribute("nguoiDung", user);
        model.addAttribute("mgs", "Bạn không có quyền truy cập !");
        return "/Vinh/ErrorPage";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "/nha/login";
    }
}
