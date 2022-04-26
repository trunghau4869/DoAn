package com.example.demo.service.Account;

import com.example.demo.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountSerivce {
    Account findById(String taikhoan);

    void save(Account account);
    List<Account> findAll();
    String  findByPass(String  userName);

    Account findByResetPasswordToken(String token);
}
