package com.lumit.shop.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.lumit.shop.common.model.User;

public class UserContext extends org.springframework.security.core.userdetails.User {
    private final User user;

    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);

        this.user = user;
    }
    
    public User getUser() {
        return user;
    }

}
