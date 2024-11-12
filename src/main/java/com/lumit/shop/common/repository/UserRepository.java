package com.lumit.shop.common.repository;

import java.math.BigInteger;
import java.util.List;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.model.TbAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;


@Mapper
@Repository
public interface UserRepository {

    List<User> getUserList();

    List<User> selectAdminList();

    User selectByUserName(String username);

    int insertUser(TbLogin tbLogin);

    TbLogin selectByUserId(String userId);

    TbLogin selectByEmail(String email);

    User selectUserByKakaoId(String kakaoId);

    User selectUserBySocialId(String socialId);

    int updateSocialUser(TbLogin tbLogin);

    int insertAddress(TbAddress tbAddress);

    List<TbAddress> selectAddressListByUserId(String userId);

    int updateDefaultAddr(TbLogin tbLogin);

    int deleteAdmin(AdminDto adminDto);

    int updateAdmin(AdminDto adminDto);

    List<User> selectOldAdminList();

    int updatePwd(SignUpDto signUpDto);
}
