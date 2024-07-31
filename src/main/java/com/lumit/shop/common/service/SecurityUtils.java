package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;

public class SecurityUtils {

    public static User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();

    }
}
