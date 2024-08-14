package com.lumit.shop.common.repository;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuRepository {
    List<TbMenu> getMenuList(String userId);


}
