package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import com.lumit.shop.common.security.social.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    @Override
    public TbLogin selectByUserId(String userId) {
        return userRepository.selectByUserId(userId);
    }

    public User selectByUsername(String username) {
        return userRepository.selectByUserName(username);
    }

    @Override
    public int insertUser(TbLogin user) {
        return userRepository.insertUser(user);
    }

    public int insertAdmin(TbLogin user) {
        return userRepository.insertUser(user);
    }

    @Override
    public ServiceCode insertUserControl(TbLogin user) {
        try {
            TbLogin tbLogin = selectByUserId(user.getUserId());
            if (tbLogin != null) {
                return ServiceCode.CONFLICT;
            }
            user.setRegId(user.getUserId());
            user.setModId(user.getUserId());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user);
            insertUser(user);
        } catch (Exception e) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.SUCCESS;
    }

    public ServiceCode updateSocialUser(TbLogin tbLogin) {
        TbLogin user = userRepository.selectByUserId(tbLogin.getUserId());
        if (user != null) {
            return ServiceCode.CONFLICT;
        }
        int result = userRepository.updateSocialUser(tbLogin);
        if (result > 0) {
            return ServiceCode.UPDATED;
        }
        return ServiceCode.UNKNOWN;
    }

}
