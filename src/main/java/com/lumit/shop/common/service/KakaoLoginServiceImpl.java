package com.lumit.shop.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumit.shop.common.model.KakaoUser;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {
    @Autowired
    private UserRepository userRepository;


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public KakaoLoginServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public KakaoUser getKakaoUser(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        String response = restTemplate.getForObject(url + "?access_token=" + accessToken, String.class);

        // TODO: JSON 응답을 User로 매핑하는 로직 추가
        KakaoUser kakaoUserInfo = null;
        try {
            kakaoUserInfo = objectMapper.readValue(response, KakaoUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return kakaoUserInfo;
    }

    @Override
    public User handleKakaoUser(KakaoUser kakaoUser) {
        User user = userRepository.countByKakaoId(kakaoUser.getId());

        return user;
        // TODO :  로그인 처리 또는 세션 관리 로직 추가
    }

    public int registUser(User user) {
        return userRepository.registUser(user);
    }

}

