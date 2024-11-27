package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.CustomUserDetailsService;
import com.lumit.shop.common.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Security;

@Component
public class SecurityUtils {
    private static UserDetailsService userDetailsService;

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

    public static void refreshPrincipal() {
        User oldUser = getPrincipal();
        createNewAuthentication(SecurityContextHolder.getContext().getAuthentication(), oldUser.getUsername());
    }

    protected static void createNewAuthentication(Authentication currentAuth, String username) {
        SecurityContextHolder.clearContext();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        currentAuth.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
