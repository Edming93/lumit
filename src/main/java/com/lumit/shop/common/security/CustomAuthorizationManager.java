package com.lumit.shop.common.security;

import com.lumit.shop.common.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Map;

@Component("authorizationChecker")
public class CustomAuthorizationManager {
    public boolean check(HttpServletRequest request, Authentication authentication) {
        boolean result = false;

        try {
            User principalObj = null;

            if (authentication.getPrincipal() instanceof User) {
                principalObj = (User) authentication.getPrincipal();

            }

            if (principalObj != null) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                Map<String, String> variables = antPathMatcher.extractUriTemplateVariables("/{menu}/**", request.getServletPath());

                String menu = variables.get("menu");

                if("common".equals(menu)) {
                    return true;
                }

                for(GrantedAuthority authority : principalObj.getAuthorities()) {
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
