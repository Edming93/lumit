package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.PrincipalDetails;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;

public class SecurityUtils {

    public static User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object obj = authentication.getPrincipal();
        User user = null;
        if (obj instanceof User) {
            user = (User) obj;
        } else if (obj instanceof PrincipalDetails) {
            user = ((PrincipalDetails) obj).getUser();
        }
        return user;
    }
}
