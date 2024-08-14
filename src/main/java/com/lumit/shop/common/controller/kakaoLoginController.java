package com.lumit.shop.common.controller;

import com.lumit.shop.common.model.KakaoUser;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.KakaoLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class kakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @RequestMapping("/login/oauth2/code/kakao") // 1. 인가 코드 받기 (@RequestParam String code)
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        // 2. 토큰 받기
        String accessToken = kakaoLoginService.getAccessToken(code);

        // 3. 사용자 정보 받기
        Map<String, Object> kakaoUserInfo = kakaoLoginService.getUserInfo(accessToken);
        System.out.println(kakaoUserInfo);

        String email = (String)kakaoUserInfo.get("email");
        String nickname = (String)kakaoUserInfo.get("nickname");

        System.out.println("3333333333333333333333333");
        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);


        String result = "";
        if (kakaoUserInfo != null) {
            User user = kakaoLoginService.handleKakaoUser(kakaoUserInfo);
            if(user == null) {
                session.setAttribute("kakao_id", (String) kakaoUserInfo.get("kakao_id"));
                result = "redirect:/member/createUser"; // 회원가입 페이지로 리디렉션
            }else {
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("gender", user.getGenderCd());
                session.setAttribute("address", user.getAddress());
                session.setAttribute("email", user.getEmail());

                result = "redirect:/lumit"; //  main 페이지로 리디렉션
            }
        } else {
            System.out.println("카카오 토큰 정보를 받아오지 못했습니다.");
        }
        return result;
    }
}
