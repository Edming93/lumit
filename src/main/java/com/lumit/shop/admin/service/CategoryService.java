package com.lumit.shop.admin.service;

import com.lumit.shop.admin.dto.ReturnKeyAndServiceCode;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    TbCategory selectCategory(int categoryId);

    List<TbCategory> selectAllCategories();

    List<TbCategory> selectTopParentCategories();

    List<TbCategory> selectChildrenCategories(int id);

    ReturnKeyAndServiceCode insertNewCategory(TbCategory category);

    boolean isDuplicatedCategory(TbCategory category);

    ServiceCode updateCategory(TbCategory data);

    List<TbCategory> searchCategory(String name);
}
