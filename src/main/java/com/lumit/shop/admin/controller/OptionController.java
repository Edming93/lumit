package com.lumit.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/{siteId}/option")
public class OptionController {
    private final String PRODUCT_PATH = "/admin/option";
    
    @GetMapping("/list")
    public String selectOptionList(ModelMap map) {
        return PRODUCT_PATH + "/list";
    }
    
    @GetMapping("/regist")
    public String registOption(ModelMap map) {
        return PRODUCT_PATH + "/regist";
    }

} 
