package com.lumit.shop.common.service;

import com.lumit.shop.common.model.KakaoUser;
import com.lumit.shop.common.model.User;

public interface KakaoLoginService {
    KakaoUser getKakaoUser(String accessToken);
    User handleKakaoUser(KakaoUser kakaoUser);
}
