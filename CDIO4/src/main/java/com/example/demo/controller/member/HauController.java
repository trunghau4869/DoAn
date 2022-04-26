package com.example.demo.controller.member;

import com.example.demo.config.Utility;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.Account.AccountSerivce;
import com.example.demo.service.Account.AccountSerivceImpl;
import com.example.demo.service.ProductBillService.BillService;
import com.example.demo.service.User.UserService;
import com.example.demo.service.User.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class HauController {
        @Autowired
        UserService userService;
        @Autowired
        UserRepo userRepo;
        @Autowired
        AccountSerivce accountSerivce;
        @Autowired
        BillService billService;
        @Autowired
        AccountSerivceImpl accountSerivceImpl;
//        @Autowired
//        private JavaMailSender mailSender;


        @GetMapping(value = "/ProfileDetail/{idUser}")
        public String showMemberView(@PathVariable("idUser") int idUser, Model model, Principal principal) {
            User user = userService.findById(idUser);
            User user1 = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("users1", user1);
            model.addAttribute("users", user);
            return "Hau/ProfileDetail";
        }

        @GetMapping(value = "/editMember/{id}")
        public String showMemberEdit(@PathVariable("id") int idUser, Model model) {
            User user = userService.findById(idUser);
            model.addAttribute("users", user);
            model.addAttribute("userName", user.getIdUser());
            return "Hau/ProfileDetail";
        }

        @PostMapping("/editMember")
        public String editMember(@Valid @ModelAttribute("users") User users, BindingResult bindingResult, Model model) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("userName", users.getIdUser());
                return "Hau/ProfileDetail";
            } else {
                userService.save(users);
                model.addAttribute("message", "Cập Nhật Thành Công !");
                model.addAttribute("userName", users.getIdUser());
                return "Hau/ProfileDetail";
            }
        }

        @GetMapping(value = "/editPass/{userName}")
        public String showMemberEditPass(@PathVariable("userName") String account, Model model) {
            Account account1 = accountSerivce.findById(account);
    //        System.out.println(account1.getUserName());
            model.addAttribute("account1", account1);
            return "/Hau/Pass";
        }

        @PostMapping("/editPass")
        public String editPass(@RequestParam("psw") String psw, @ModelAttribute("account")
                Account account, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String oldPass = passwordEncoder.encode(psw);
            String oldPass1 = accountSerivce.findByPass(account.getUserName());
            if (BCrypt.checkpw(psw,oldPass1)) {
                if (account.getPassWord().equals(account.getRePassWord())) {
                    account.setPassWord(passwordEncoder.encode(account.getPassWord()));
                    account.setRePassWord(passwordEncoder.encode(account.getRePassWord()));
                    accountSerivce.save(account);
                    redirectAttributes.addAttribute("account", account.getUserName()).
                            addFlashAttribute("message", "Cập nhật thành công ! ");
                    return "redirect:/editPass/" + account.getUserName();
                } else {
                    System.out.println("day la mat khau moi " + account.getPassWord());
                    System.out.println("day la mat khau moi xac nhan" + account.getRePassWord());
                    redirectAttributes.addAttribute("account", account.getUserName()).
                            addFlashAttribute("messages", "Mật khẩu phải trùng nhau ! ");
                }

            } else {
                redirectAttributes.addAttribute("account", account.getUserName()).
                        addFlashAttribute("messagess", "Mật Khẩu Không Đúng ! ");
            }
            return "redirect:/editPass/" + account.getUserName();
        }

//        @GetMapping("showInfoUser")
//        public ModelAndView show(@PathVariable("idUser") int idUser, Model model ) {
//            ModelAndView modelAndView = new ModelAndView("/Hau/UserBill");
//            List<Bill> bills = billService.findBills(idUser);
//            modelAndView.addObject("bills", bills);
//            return modelAndView;
//        }

        @GetMapping(value = "/listOfInvoices/{idUser}")
        public String showHistory(@PathVariable("idUser") int idUser, Model model ,Principal principal) {
            List<Bill> bills = billService.findBills(idUser);
            model.addAttribute("bills", bills);

            User user1 = userRepo.findByAccount_UserName(principal.getName());
            model.addAttribute("users1", user1);

            return "Hau/UserBill";
    //        return "redirect:/showInfoUser";
        }

        @GetMapping("/forgot_password")
        public String showForgotPasswordForm(Model model) {
    //        model.addAttribute("pageTitle", "Forgot Password");
            return "Hau/forgotPassword";

        }

//        @PostMapping("/forgot_password")
//        public String processForgotPasswordForm(HttpServletRequest request, Model model){
//            String email=request.getParameter("email");
//            String token= RandomString.make(45);
//            try {
//                accountSerivceImpl.updateResetPasswordToken(token,email);
//                String resetPasswordLink= Utility.getSiteURL(request) +"/reset_password?token="+token;
//                System.out.println(resetPasswordLink);
//                sendMail(email,resetPasswordLink);
//                model.addAttribute("message","We have sent a reset password link to your email. Please check email");
//            }catch (AccountNotFoundException e){
//                System.out.println("day la code "+token);
//                model.addAttribute("error","Not Found Email!!");
//
//            } catch (UnsupportedEncodingException | MessagingException e) {
//                model.addAttribute("error","Error while sending email !!");
//
//            }
//            return "Hau/forgotPassword";
//        }

//        private void sendMail(String email,String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
//            MimeMessage message=mailSender.createMimeMessage();
//            MimeMessageHelper helper=new MimeMessageHelper(message);
//            helper.setFrom("nhokhau1603@gmail.com","Neffex Support");
//            helper.setTo(email);
//            String subject="Here's the link to your reset password";
//            String content="<p> Hello ,</p>"
//                    +"<p>Your have requested to reset your password.</p>"
//                    +"<p>Clink the link to change your password</p>"
//                    +"<p><a href=\""+resetPasswordLink+"\">Click to change my password</a></p>"
//                    +"<p>Ignore this email if you do remember your password,or you have not made the request</p>";
//            helper.setSubject(subject);
//            helper.setText(content,true);
//            mailSender.send(message);
//        }

        @GetMapping("/reset_password")
        public String showResetPasswordForm(@Param(value = "token")String token,Model model){
            Account account=accountSerivceImpl.get(token);
            System.out.println("khon tim thay account "+account);
            if(account == null){
                model.addAttribute("messageError","Invalid Token");
                return "Hau/ResetPassword";
            }
            model.addAttribute("token",token);
            return "Hau/ResetPassword";
        }

        @PostMapping("/reset_password")
        public String processResetPassword(HttpServletRequest request,Model model,RedirectAttributes redirectAttributes){
           String token=request.getParameter("token");
           String password=request.getParameter("password");
           String confirmPass=request.getParameter("confirmPass");
            Account account=accountSerivceImpl.get(token);
            System.out.println(account);
            if(account == null){
                model.addAttribute("messageError","Invalid Token");
                return "Hau/ResetPassword";
            }else {
                if(password.equals(confirmPass)) {
                    accountSerivceImpl.updatePassword(account, password);
                    model.addAttribute("message", "You have successfully changed your password");
                }else {
                    redirectAttributes.addFlashAttribute("messageError", "Password Incorrect");
                    return  "redirect:/reset_password?token="+token;

                }
            }
            return "Hau/ResetPassword";
        }
}

