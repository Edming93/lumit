package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

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
        return userRepository.insertNewUser(user);
    }

    public int insertAdmin(TbLogin user) {
        return userRepository.insertNewUser(user);
    }


}
