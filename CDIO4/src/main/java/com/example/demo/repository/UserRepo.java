package com.example.demo.repository;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    List<User> findByGmail(String email);
    User findByAccount_UserName(String user);

    Page<User> findByName(String nameUser, Pageable pageable);
    Page<User> findByAddress(String addRess, Pageable pageable);
    Page<User> findByNameAndAddress(String nameUser,String addRess, Pageable pageable);
}
