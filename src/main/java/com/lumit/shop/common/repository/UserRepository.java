package com.lumit.shop.common.repository;

import com.lumit.shop.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    List<User> getUserList();

    User selectByUserName(String username);

    int addNewUser(com.lumit.shop.admin.model.User user);
}
