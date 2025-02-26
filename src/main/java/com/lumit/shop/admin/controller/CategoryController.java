package com.lumit.shop.admin.controller;

import com.lumit.shop.admin.service.CategoryService;
import com.lumit.shop.common.model.TbCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
    private static String BASE_PATH = "admin/category";
    private final CategoryService categoryService;

    @RequestMapping("/list")
    public String categoryMain(@RequestParam(required = false) Integer parentId, Model model) {
        if (parentId != null) {
            List<TbCategory> tbCategory = categoryService.selectChildrenCategories(parentId);
            model.addAttribute("selected", tbCategory);
        }
        return BASE_PATH + "/list";
    }

}
