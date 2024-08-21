package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int insertNewUser(TbLogin user) {
        return userRepository.insertNewUser(user);
    }

    public int insertNewAdmin(TbLogin user) {
        return userRepository.insertNewUser(user);
    }
}
