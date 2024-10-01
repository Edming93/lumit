package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUserList();

    public TbLogin selectByUserId(String userId);

    public User selectByUsername(String username);

    public int insertUser(TbLogin tbLogin);

    public int insertAdmin(TbLogin tbLogin);

    public boolean isIdDuplicated(String id);

    public ServiceCode insertUserControl(SignUpDto signUpDto);

    public ServiceCode updateSocialUser(SignUpDto signUpDto);

    public int insertAddress(TbAddress tbAddress);

    public int updateDefaultAddr(TbLogin tbLogin);

    public List<TbAddress> selectAddressListByUserId(String userId);
}
