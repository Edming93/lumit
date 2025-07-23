package com.lumit.shop.admin.service;

import com.lumit.shop.admin.dto.ReturnKeyAndServiceCode;
import com.lumit.shop.admin.repository.CategoryRepository;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;
import com.lumit.shop.common.service.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public TbCategory selectCategory(int categoryId) {
        return categoryRepository.selectCategory(categoryId);
    }

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
    public ReturnKeyAndServiceCode insertNewCategory(TbCategory category) {
        category.setRegId(SecurityUtils.getPrincipal().getUserId());
        category.setRegDt(LocalDateTime.now());
        if (isDuplicatedCategory(category)) {
            return ReturnKeyAndServiceCode.builder().id(null).sc(ServiceCode.CONFLICT).build();
        } else {
            if (categoryRepository.insertNewCategory(category) > 0) {
                return ReturnKeyAndServiceCode.builder().id(category.getCategoryId()).sc(ServiceCode.SUCCESS).build();
            } else {
                return ReturnKeyAndServiceCode.builder().id(null).sc(ServiceCode.UNKNOWN).build();
            }
        }
    }

    @Override
    public boolean isDuplicatedCategory(TbCategory category) {
        if (categoryRepository.isDuplicatedCategory(category) != null) {
            return true;
        }
        return false;
    }

    @Override
    public ServiceCode updateCategory(TbCategory data) {
        System.out.println("what the..." + data);
        data.setModId(SecurityUtils.getPrincipal().getUserId());
        data.setModDt(LocalDateTime.now());
        return categoryRepository.updateCategory(data) > 0 ? ServiceCode.UPDATED : ServiceCode.UNKNOWN;
    }

    @Override
    public List<TbCategory> searchCategory(String name) {
        return categoryRepository.searchCategory(name);
    }
}
