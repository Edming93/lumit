package com.lumit.shop.admin.controller.restController;

import com.lumit.shop.admin.service.CategoryService;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ServiceCode sc = categoryService.insertNewCategory(data);
        if (sc.equals(ServiceCode.CONFLICT)) {
            return ResponseEntity.status(409).build();
        }
        if (!sc.equals(ServiceCode.SUCCESS)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
