package com.lumit.shop.common.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbCode;

@Mapper
@Repository
public interface CodeRepository {
    public List<TbCode> selectCodeListByGrpCd(CommonSearch search);
}

