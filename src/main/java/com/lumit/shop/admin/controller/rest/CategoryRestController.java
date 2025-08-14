package com.lumit.shop.admin.controller.rest;

import com.lumit.shop.admin.dto.ApiResponse;
import com.lumit.shop.admin.dto.ReturnKeyAndServiceCode;
import com.lumit.shop.admin.service.CategoryService;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;
import com.lumit.shop.common.service.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/{siteId}/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;


    @GetMapping("/list")
    public ResponseEntity<?> selectTopParentCategories() {
        List<TbCategory> categories = categoryService.selectTopParentCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> selectChildrenCategories(@PathVariable("id") int parentId) {
        List<TbCategory> categories = categoryService.selectChildrenCategories(parentId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/new")
    public ResponseEntity<?> insertNewCategory(@RequestBody TbCategory data) {
        if (data.getParent() == null) {
            data.setParent(0);
        }

        ReturnKeyAndServiceCode result = categoryService.insertNewCategory(data);
        if (result.getSc().equals(ServiceCode.CONFLICT)) {
            return ResponseEntity.status(409).build();
        }
        if (!result.getSc().equals(ServiceCode.SUCCESS)) {
            return ResponseEntity.badRequest().build();
        }
        TbCategory insertedCategory = categoryService.selectCategory(result.getId());
        return ResponseEntity.ok(insertedCategory);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody TbCategory data) {
        return getResponseEntity(data);
    }

    @PatchMapping("/move")
    public ResponseEntity<?> moveCategory(@RequestBody TbCategory data) {
        return getResponseEntity(data);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCategory(@PathVariable(value = "name") String name) {
        List<TbCategory> categories = categoryService.searchCategory(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<?> getAllParentCategories(@PathVariable("id") int categoryId) {
        List<TbCategory> parents = categoryService.getAllParents(categoryId);
        return ResponseEntity.ok(parents);
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody TbCategory data) {
        ServiceCode sc = categoryService.updateCategory(data);
        if (!sc.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.ok(ApiResponse.fail("NO_UPDATE", "업데이트 되지 않았습니다."));
        } else {
            Map<String, Object> updatedInfo = Map.of(
                    "updatedAt", LocalDateTime.now(),
                    "updatedBy", SecurityUtils.getPrincipal().getUserId()
            );
            return ResponseEntity.ok(ApiResponse.ok("업데이트가 완료되었습니다.", updatedInfo));
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "categoryId") int categoryId) {
        TbCategory category = categoryService.selectCategory(categoryId);
        category.setUseYn("N");
        ServiceCode sc = categoryService.updateCategory(category);
        if (!sc.equals(ServiceCode.DELETED)) {
            return ResponseEntity.ok(ApiResponse.fail("DELETE_FAILED", "삭제 되지 않았습니다."));
        } else {
            Map<String, Object> deletedInfo = Map.of(
                    "deletedAt", LocalDateTime.now(),
                    "deletedBy", SecurityUtils.getPrincipal().getUserId()
            );
            return ResponseEntity.ok(ApiResponse.ok("삭제가 완료되었습니다.", deletedInfo));
        }
    }
}
