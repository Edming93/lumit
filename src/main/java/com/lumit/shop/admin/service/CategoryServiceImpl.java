package com.lumit.shop.admin.service;

import com.lumit.shop.admin.repository.CategoryRepository;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<TbCategory> selectAllCategories() {
        return categoryRepository.selectAllCategories();
    }

    @Override
    public List<TbCategory> selectTopParentCategories() {
        return categoryRepository.selectTopParentCategories();
    }

    @Override
    public List<TbCategory> selectChildrenCategories(int parentId) {
        return categoryRepository.selectChildrenCategories(parentId);
    }

    @Override
    public ServiceCode insertNewCategory(TbCategory category) {
        if (isDuplicatedCategory(category)) {
            return ServiceCode.CONFLICT;
        }
        return categoryRepository.insertNewCategory(category) > 0 ? ServiceCode.SUCCESS : ServiceCode.UNKNOWN;
    }

    @Override
    public boolean isDuplicatedCategory(TbCategory category) {
        System.out.println("testing...");
        if (categoryRepository.isDuplicatedCategory(category) != null) {
            return true;
        }
        return false;
    }
}
