package com.example.demo.service.User;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    UserRepo userRepo;

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User findByUser_User(String user) {
        return userRepo.findByAccount_UserName(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepo.findByGmail(email);
    }

    @Override
    public User findById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void remove(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public Page<User> findByTenNguoiDung(String nameUser, Pageable pageable) {
        return userRepo.findByName(nameUser,pageable);
    }

    @Override
    public Page<User> findByDiaChi(String addRess, Pageable pageable) {
        return userRepo.findByAddress(addRess,pageable);
    }

    @Override
    public Page<User> findByTenNguoiDungAndDiaChi(String nameUser, String addRess, Pageable pageable) {
        return userRepo.findByNameAndAddress(nameUser,addRess,pageable);
    }
}
