package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
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

    @Override
    public int insertUser(TbLogin user) {
        return userRepository.insertUser(user);
    }

    public int insertAdmin(TbLogin user) {
        return userRepository.insertUser(user);
    }

    @Override
    public ServiceCode insertUserControl(TbLogin user) {
        TbLogin tbLogin = selectByUserId(user.getUserId());
        if (tbLogin != null) {
            return ServiceCode.CONFLICT;
        }
        try {
            user.setRegId(user.getUserId());
            user.setModId(user.getUserId());
            user.setPassword(passwordEncoder.encode(tbLogin.getPassword()));
            insertUser(tbLogin);
        } catch (Exception e) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.SUCCESS;
    }
}
