package com.lumit.shop.common.security;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.social.CustomOauth2UserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.List;

public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = null;
        if (authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            CustomOauth2UserDetails details = (CustomOauth2UserDetails) authentication.getPrincipal();
            user = (User) userDetailsService.loadUserByUsername(details.getUsername());
        }

//        for (GrantedAuthority authority : user.getAuthorities()) {
//            System.out.println("Authority: " + authority.getAuthority());
//        }

        List<TbMenu> menuList = user.getMenuAuthorities();
        String defaultUrl = "";
        for (TbMenu menu : menuList) {
            if (StringUtils.equals(menu.getMenuDefaultUrl(), "")) {
                if (StringUtils.equals(menu.getMainYn(), "Y")) {
                    defaultUrl = menu.getMenuUrl().replace("/**", "");
                    if (defaultUrl.equals("") || defaultUrl.equals("/main")) {
                        defaultUrl = "/";
                    }
                }
            }
        }
        HttpSession session = request.getSession();
        if (user.getSocialId() != null) {
            if (user.getEmail().equals(user.getUserId()) || user.getUserId().equals(user.getSocialId())) {
                session.setAttribute("social_id", user.getSocialId());
                defaultUrl = "/main/member/createUser";
            }
        }


        session.setAttribute("menuList", menuList);
        System.out.println("기본경로 ::");
        System.out.println(defaultUrl);
        super.setDefaultTargetUrl(defaultUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
