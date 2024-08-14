//package com.lumit.shop.common.controller;
//
//import com.lumit.shop.common.model.KakaoUser;
//import com.lumit.shop.common.model.User;
//import com.lumit.shop.common.service.KakaoLoginService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//@RestController
//@RequestMapping("/kakaoCallback")
//public class kakaoLoginController_copy {
//
//    @Autowired
//    private KakaoLoginService kaKaoLoginService;
//
//    @PostMapping
//    public String kakaoCallback(@RequestParam("access_token") String accessToken, HttpSession session) {
//        KakaoUser kakaoUser = kaKaoLoginService.getKakaoUser(accessToken);
//        String result = "";
//        if (kakaoUser != null) {
//            User user = kaKaoLoginService.handleKakaoUser(kakaoUser);
//            if(user == null) {
//                session.setAttribute("kakao_id", kakaoUser.getId());
//                result = "redirect:/get/signup"; // 회원가입 페이지로 리디렉션
//            }else {
//                session.setAttribute("userId", user.getUserId());
//                session.setAttribute("userName", user.getUserName());
//                session.setAttribute("gender", user.getGenderCd());
//                session.setAttribute("address", user.getAddress());
//                session.setAttribute("email", user.getEmail());
//
//                result = "redirect:/lumit"; //  main 페이지로 리디렉션
//            }
//        } else {
//            System.out.println("카카오 토큰 정보를 받아오지 못했습니다.");
//        }
//        return result;
//    }
//}
