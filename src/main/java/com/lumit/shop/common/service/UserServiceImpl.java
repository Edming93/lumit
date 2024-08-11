package com.lumit.shop.common.service;

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
    public com.lumit.shop.admin.model.User findUserById(String userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public int addNewUser(com.lumit.shop.admin.model.User user) {
        return userRepository.addNewUser(user);
    }
}
