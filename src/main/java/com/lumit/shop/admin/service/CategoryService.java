package com.lumit.shop.admin.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;

import java.util.List;

public interface CategoryService {

    List<TbCategory> selectAllCategories();

    List<TbCategory> selectTopParentCategories();

    List<TbCategory> selectChildrenCategories(int id);

    ServiceCode insertNewCategory(TbCategory category);
}
