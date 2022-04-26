package com.example.demo.service.Account;

import com.example.demo.model.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AccountRoleService {
    Set<Role> findname(String role);
}
