package com.github.adrian83.mordeczki.auth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.model.entity.Role;

public class MUserDetails implements UserDetails {

    private static final long serialVersionUID = -4198090540995302263L;

    private final Account account;

    public MUserDetails(Account user) {
        this.account = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getRoles()
                .stream()
                .map(this::toGrantedAuthority)
                .toList();
    }

    @Override
    public String getPassword() {
        return account.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !account.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !account.isExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }

    protected GrantedAuthority toGrantedAuthority(Role role) {
        return () -> role.getName();
    }
}
