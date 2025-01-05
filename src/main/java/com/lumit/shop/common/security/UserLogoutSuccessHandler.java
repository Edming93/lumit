package com.lumit.shop.common.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    
    private static final Logger log = LoggerFactory.getLogger(UserLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        
        super.setDefaultTargetUrl("/admin/login");
        super.onLogoutSuccess(request, response, authentication);
    }

    
}
