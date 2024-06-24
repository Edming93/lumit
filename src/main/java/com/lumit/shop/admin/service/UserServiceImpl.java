package com.lumit.shop.admin.service;

import com.lumit.shop.admin.model.user;
import com.lumit.shop.admin.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    @Override
    public List<user> getUserList() {
        return userMapper.getUserList();
    }
}
