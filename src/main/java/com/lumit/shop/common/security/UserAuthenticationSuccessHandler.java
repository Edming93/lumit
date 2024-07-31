package com.lumit.shop.common.security;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();
        System.out.println("여기타??");
        System.out.println(user.getUserId());
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        System.out.println("된겨??????");

        for (GrantedAuthority authority : user.getAuthorities()) {
            System.out.println("Authority: " + authority.getAuthority());
        }
        super.getDefaultTargetUrl();
        super.onAuthenticationSuccess(request, response, authentication);



    }
}
