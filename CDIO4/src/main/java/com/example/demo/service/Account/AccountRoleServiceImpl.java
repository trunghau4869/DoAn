package com.example.demo.service.Account;

import com.example.demo.model.Role;
import com.example.demo.repository.AccountRepo.AccountRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountRoleServiceImpl implements AccountRoleService{
    @Autowired
    AccountRoleRepo accountRoleRepo;
    @Override
    public Set<Role> findname(String role) {
        return accountRoleRepo.findByRoleName(role);
    }
}
