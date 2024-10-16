package com.lumit.shop.common.controller;

import com.lumit.shop.common.security.CustomUserDetailsService;
import com.lumit.shop.common.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // code에 인가코드 받아서 전달
    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        return kakaoLoginService.getKaKaoCheck(code, session, request, response);
    }


}
