package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    @Override
    public int addNewUser(com.lumit.shop.admin.model.User user) {
        return userRepository.addNewUser(user);
    }
}
