package com.lumit.shop.common.security;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.lumit.shop.common.model.User;

import jakarta.servlet.http.HttpServletRequest;

@Component("authorizationChecker")
public class AuthorizationChecker {
    public boolean check(HttpServletRequest request, Authentication authentication) {
        boolean result = false;

        try {
            User principalObj = (User) authentication.getPrincipal();

            if (principalObj != null) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                Map<String, String> variables = antPathMatcher.extractUriTemplateVariables("/{menu}/**", request.getServletPath());

                String menu = variables.get("menu");

                // 나중에 공통으로 쓸 메뉴가 생길 경우 /common ~~으로 생성
                if ("common".equals(menu)) {
                    return true;
                }

                for (GrantedAuthority authority : principalObj.getAuthorities()) {
                    if (antPathMatcher.match(authority.getAuthority(), request.getServletPath())) {
                        result = true;
                        break;
                    }
                }

            }

        } catch (Exception exception) {
            result = false;
        }

        return result;

    }
}
