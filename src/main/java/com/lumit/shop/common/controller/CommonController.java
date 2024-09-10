package com.lumit.shop.common.controller;

import com.lumit.shop.common.security.social.CustomOauth2UserService;
import com.lumit.shop.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class CommonController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * GET Mapping Returns
     * <p>
     * GET / -> /lumit/index
     * GET /login -> /common/auth/login
     * GET /member/createUser -> /common/auth
     */
    private final String LUMIT_INDEX = "/lumit/index";
    private final String LOGIN_FORM = "/common/auth/login";


    @Value("${kakao.api_key}")
    private String kakaoApiKey;
    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    private CustomOauth2UserService authService;

    // Todo
    // 정보 입력 덜 된 애들 회원가입 페이지로 넘겨주기

    @GetMapping("/")
    public String getMain() {
        return LUMIT_INDEX;
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("kakaoUri", kakaoRedirectUri);
        return LOGIN_FORM;
    }


}
