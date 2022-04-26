package com.example.demo.repository.RoleRepo;

import com.example.demo.model.Account;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    List<Role> findByAccounts(Account account);
}
