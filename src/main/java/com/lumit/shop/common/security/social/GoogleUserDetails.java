package com.lumit.shop.common.security.social;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Map;

@ToString
@AllArgsConstructor
public class GoogleUserDetails implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "GOOGLE";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
