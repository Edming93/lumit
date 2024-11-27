package com.lumit.shop.common.repository;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbCode;
import com.lumit.shop.common.model.TbEmailAuth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmailRepository {
    TbEmailAuth selectAuthInfo(String userId);

    int insertAuthInfo(TbEmailAuth tbEmailAuth);

    int updateAuthInfo(TbEmailAuth tbEmailAuth);

    int grantAuthInfo(String userId);
}

