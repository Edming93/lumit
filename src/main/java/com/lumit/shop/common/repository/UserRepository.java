package com.lumit.shop.common.repository;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    List<User> getUserList();

    User selectByUserName(String username);

    int insertNewUser(TbLogin tbLogin);

    TbLogin selectByUserId(String userId);

    int insertUser(TbLogin user);

    User selectUserByKakaoId (String kakaoId);
}
