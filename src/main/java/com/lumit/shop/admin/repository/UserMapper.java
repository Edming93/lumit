package com.lumit.shop.admin.repository;

import com.lumit.shop.admin.model.user;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<user> getUserList();
}
