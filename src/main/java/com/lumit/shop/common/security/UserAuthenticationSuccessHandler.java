package com.lumit.shop.common.security;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();
        System.out.println("여기타??");
        System.out.println(user.getUserId());
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println("----------------------------");


        for (GrantedAuthority authority : user.getAuthorities()) {
            System.out.println("Authority: " + authority.getAuthority());
        }
        List<TbMenu> menuList = user.getMenuAuthorities();
        String defaultUrl = "";
        for (TbMenu menu : menuList) {
            if (StringUtils.equals(menu.getMenuDefaultUrl(), "")) {
                if (StringUtils.equals(menu.getMainYn(), "Y")) {
                    defaultUrl = menu.getMenuUrl().replace("/**", "");
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("menuList", menuList);
        System.out.println("기본경로 ::");
        System.out.println(defaultUrl);
        super.setDefaultTargetUrl(defaultUrl);
        super.onAuthenticationSuccess(request, response, authentication);


    }

}
