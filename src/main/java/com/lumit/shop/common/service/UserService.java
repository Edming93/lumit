package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUserList();

    public TbLogin selectByUserId(String userId);

    public int insertUser(TbLogin tbLogin);

    public int insertAdmin(TbLogin tbLogin);

}
