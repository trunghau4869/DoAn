package com.example.demo.secrcutiy;

import com.example.demo.model.Account;
import com.example.demo.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private Account account;

    public MyUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        for (Role role : account.getRoles()) {
            // ROLE_CUSTOMER, ROLE_ADMIN,..
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            grantList.add(authority);
        }
        return grantList;
    }

    @Override
    public String getPassword() {
        return account.getPassWord() ;
    }

    @Override
    public String getUsername() {
        return account.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
