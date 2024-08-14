package com.lumit.shop.common.service;

import com.lumit.shop.common.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUserList();

    public com.lumit.shop.admin.model.User findUserById(String userId);

    public int addNewUser(com.lumit.shop.admin.model.User user);

    int insertUser(com.lumit.shop.admin.model.User user);
}
