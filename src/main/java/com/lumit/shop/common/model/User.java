package com.lumit.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private String userId;
    private String name;
    private String roleId;
    private String password;
    private String address;
    private String email;
    private String genderCd;
    private String phone;
    private String kakaoId;
    private Timestamp regDt;
    private String regId;
    private Timestamp modDt;
    private String modId;

    private List<TbMenu> authorities = new ArrayList<>();

    public void setAuthorities (List<TbMenu> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority (TbMenu menu) {
        authorities.add(menu);
    }

    public List<TbMenu> getMenuAuthorities() {
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority> unmodifiableCollection(authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getUsername() {
        return null;
    }

    public String getUserName() {
        return userId;
    }

}