package com.lumit.shop.admin.controller;

import com.lumit.shop.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
    private static String BASE_PATH = "admin/category";

    @RequestMapping("/list")
    public String categoryMain() {
        return BASE_PATH + "/list";
    }

}
