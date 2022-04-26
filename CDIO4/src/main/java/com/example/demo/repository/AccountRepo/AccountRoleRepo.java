package com.example.demo.repository.AccountRepo;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AccountRoleRepo extends JpaRepository<Role,Integer> {
    Set<Role> findByRoleName(String role);
}
