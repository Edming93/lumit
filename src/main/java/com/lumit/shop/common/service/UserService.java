package com.lumit.shop.common.service;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.dto.SearchUserDto;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getUserList();

    List<User> selectAdminList();

    TbLogin selectByUserId(String userId);

    TbLogin selectByEmail(String email);

    int insertUser(TbLogin tbLogin);

    int insertAdmin(TbLogin tbLogin);

    ModalInfo updateUserInfo(UserInfoDto userInfo, HttpSession httpSession);

    boolean isIdDuplicated(String id);

    ServiceCode insertUserControl(SignUpDto signUpDto);

    ServiceCode updateSocialUser(SignUpDto signUpDto);

    int insertAddress(TbAddress tbAddress);

    int updateDefaultAddr(TbLogin tbLogin);

    List<TbAddress> selectAddressListByUserId(String userId);

    ServiceCode deleteAdmin(String id);

    ServiceCode updateAdmin(String id, AdminDto adminDto);

    List<User> selectOldAdminList();

    ServiceCode updateTempPwd(UserInfoDto userInfo);
}
