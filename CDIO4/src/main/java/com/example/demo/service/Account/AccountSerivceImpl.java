package com.example.demo.service.Account;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class AccountSerivceImpl implements AccountSerivce {
    @Autowired
    AccountRepo accountRepo;

    @Override
    public Account findById(String taikhoan) {
        return accountRepo.findById(taikhoan).orElse(null);
    }

    @Override
    public void save(Account account) {
        accountRepo.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public String findByPass(String userName) {
        return accountRepo.findByPass(userName);
    }


    @Override
    public Account findByResetPasswordToken(String token) {
        return accountRepo.findAccountByGmail(token);
    }

    public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException {
        Account account = accountRepo.findAccountByGmail(email);
//        Account account1 = accountRepo.findAccountByUser_Name(email);

        System.out.println("day la email  " + account);
        if (account != null) {
            account.setResetPasswordToken(token);
            accountRepo.save(account);
        } else {
            throw new AccountNotFoundException("Không tìm thấy account !" + email);

        }
    }

    public Account get(String resetPasswordToken) {
        return accountRepo.findAccountByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setResetPasswordToken(null);
        account.setPassWord(encodedPassword);
        accountRepo.save(account);
    }

}
