package com.lumit.shop.common.repository;

import java.math.BigInteger;
import java.util.List;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.dto.SearchUserDto;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.TbAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;


@Mapper
@Repository
public interface UserRepository {

    //select
    List<User> getUserList();

    List<User> selectAdminList();

    TbLogin selectByUserId(String userId);

    TbLogin selectByEmail(String email);

    User selectUserByKakaoId(String kakaoId);

    User selectUserBySocialId(String socialId);

    List<TbAddress> selectAddressListByUserId(String userId);

    List<User> selectOldAdminList();

    //insert

    int insertUser(TbLogin tbLogin);

    int insertAddress(TbAddress tbAddress);

    //update

    int updateSocialUser(TbLogin tbLogin);

    int updateDefaultAddr(TbLogin tbLogin);

    int updateAddress(TbAddress tbAddress);

    int deleteAddress(int id);

    int insertNewAddress(TbAddress tbAddress);

    TbAddress selectAddressById(int id);

    int updateAdmin(AdminDto adminDto);

    int updatePwd(UserInfoDto userInfo);

    int updateUserInfo(UserInfoDto userInfoDto);

    // delete
    int deleteAdmin(AdminDto adminDto);

    int removeAuthCode(String userId);
}
