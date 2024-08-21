package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public interface KakaoLoginService {
    // 토큰 가져오기
    String getAccessToken(String code);
    // 토큰으로 유저정보 가져오기
    HashMap<String, Object> getUserInfo(String accessToken);

    // 카카오 로그아웃
    void kakaoLogout(String accessToken);

    // 가입 된 유저정보 가져오기
    User selectUserByKakaoId(Map<String, Object> kakaoUserInfo);

    String getKaKaoCheck(String code, HttpSession session, HttpServletRequest request, HttpServletResponse response);


}
