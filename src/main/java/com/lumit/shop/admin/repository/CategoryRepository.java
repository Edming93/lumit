package com.lumit.shop.admin.repository;


import com.lumit.shop.common.model.TbCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryRepository {

    List<TbCategory> selectAllCategories();

    List<TbCategory> selectChildrenCategories(int parentId);

    List<TbCategory> selectTopParentCategories();

    int insertNewCategory(TbCategory category);

    TbCategory isDuplicatedCategory(TbCategory tbCategory);
}
