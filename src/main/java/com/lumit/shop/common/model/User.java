package com.lumit.shop.common.model;

import com.lumit.shop.common.constants.Role;
import lombok.*;
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
@ToString
public class User implements UserDetails {
    private String userId;
    private String name;
    private String roleId;
    private Role role;
    private String password;
    private String address;
    private String email;
    private String genderCd;
    private String phone;
    private String socialId;
    private Timestamp regDt;
    private String regId;
    private Timestamp modDt;
    private String modId;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(GrantedAuthority grantedAuthority) {
        authorities.add(grantedAuthority);
    }

    public void setRole(int roleId) {
        switch (roleId) {
            case 1:
                this.role = Role.SUPER_ADMIN;
                break;
            case 2:
                this.role = Role.ADMIN;
                break;
            case 3:
                this.role = Role.USER;
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    public List<GrantedAuthority> getMenuAuthorities() {
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>unmodifiableCollection(authorities);
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

    public void setRole() {
        this.role = roleId == "1" ? Role.SUPER_ADMIN : roleId == "2" ? Role.ADMIN : Role.USER;
    }

}