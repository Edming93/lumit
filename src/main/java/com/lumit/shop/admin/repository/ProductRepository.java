package com.lumit.shop.admin.repository;

import java.util.List;
import java.util.Map;

import com.lumit.shop.common.data.RequestList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.TbProduct;

@Mapper
@Repository
public interface ProductRepository {
    public List<TbProduct> selectProductList(SearchDto search);

}

