package com.lumit.shop.common.security.social;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if (provider.equals("google")) {
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
        }

        String providerId = (String) oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String socialId = oAuth2UserInfo.getProvider() + "_" + providerId;
        String name = oAuth2UserInfo.getName();
        User user = userRepository.selectUserBySocialId(socialId);
        if (user == null) {
            if (email == null) {
                email = socialId;
            }
            TbLogin temp = createUser(socialId, email, name);
            userRepository.insertUser(temp);
            user = userRepository.selectUserBySocialId(socialId);
        }
        user.setRole();
        return new CustomOauth2UserDetails(user, oAuth2User.getAttributes());
    }

    public TbLogin createUser(String socialId, String email, String name) {
        TbLogin tbLogin = new TbLogin();
        tbLogin.setUserId(email);
        tbLogin.setName(name);
        tbLogin.setSocialId(socialId);
        tbLogin.setAddress("");
        tbLogin.setPassword("");
        tbLogin.setEmail(email);
        tbLogin.setPhone("0");
        tbLogin.setGenderCd("1");
        tbLogin.setRoleId(3);
        return tbLogin;
    }
}
