package com.github.adrian83.mordeczki.auth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MUserDetails implements UserDetails {

    private static final long serialVersionUID = -4198090540995302263L;

    @JsonProperty("passwordHash")
    private final String passwordHash;
    @JsonProperty("username")
    private final String username;
    @JsonProperty("accountExpired")
    private final boolean accountExpired;
    @JsonProperty("credentialsExpired")
    private final boolean credentialsExpired;
    @JsonProperty("locked")
    private final boolean locked;
    @JsonProperty("enabled")
    private final boolean enabled;
    @JsonProperty("roles")
    private final Collection<String> roles;

    public MUserDetails(String email, String passwordHash, boolean accountExpired, boolean credentialsExpired, boolean locked, boolean enabled, Collection<String> roles) {
        this.username = email;
        this.passwordHash = passwordHash;
        this.accountExpired = accountExpired;
        this.credentialsExpired = credentialsExpired;
        this.locked = locked;
        this.enabled = enabled;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(this::toGrantedAuthority)
                .toList();
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    protected GrantedAuthority toGrantedAuthority(String roleName) {
        return () -> roleName;
    }
}
