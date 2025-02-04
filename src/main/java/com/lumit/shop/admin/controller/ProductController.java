package com.lumit.shop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lumit.shop.common.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/{siteId}/product")
public class ProductController {
    private final String PRODUCT_PATH = "/admin/product";
    
    @Autowired
    MenuRepository menuRepository;

    @GetMapping("/list")
    public String selectProductList(ModelMap map) {
        return PRODUCT_PATH + "/list";
    }
    
    @GetMapping("/regist")
    public String registProduct(ModelMap map) {
        return PRODUCT_PATH + "/regist";
    }

    @GetMapping("/update/{productId}")
    public String updateProduct(ModelMap map, @PathVariable("productId") String productId) {
    	map.addAttribute("productId", productId);
        return PRODUCT_PATH + "/update";
    }
} 
