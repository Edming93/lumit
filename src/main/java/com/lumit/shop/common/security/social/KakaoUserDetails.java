package com.lumit.shop.common.security.social;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;
import java.util.Map;


@AllArgsConstructor
public class KakaoUserDetails implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return (String) ((Map<String, Object>) attributes.get("properties")).get("nickname");
    }
}
