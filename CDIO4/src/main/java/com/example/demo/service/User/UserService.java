package com.example.demo.service.User;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void save(User user);

    User findByUser_User(String user);

    Page<User> findAll(Pageable pageable);

    List<User> findByEmail(String email);

    User findById(int id);

    void remove(int id);

    Page<User> findByTenNguoiDung(String nameUser,Pageable pageable);

    Page<User> findByDiaChi(String addRess, Pageable pageable);

    Page<User> findByTenNguoiDungAndDiaChi(String nameUser, String addRess, Pageable pageable);
}
