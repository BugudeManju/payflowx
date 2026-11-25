package com.payflowx.userinfodetails;

import com.payflowx.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// Translates our User entity into the shape Spring Security understands
public class UserInfoDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(User userInfo) {
        this.username = userInfo.getEmail(); // Use email as username
        this.password = userInfo.getPassword();
        // "MERCHANT,ADMIN" -> ROLE_MERCHANT, ROLE_ADMIN, which is the format hasRole(...) checks
        this.authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(String::trim)
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // The login check compares the typed password against this BCrypt hash
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
