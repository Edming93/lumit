package com.lumit.shop.common.security.social;

public interface OAuth2UserInfo {
    String getProvider();

    Object getProviderId();

    String getEmail();

    String getName();
}
