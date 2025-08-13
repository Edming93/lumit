package com.lumit.shop.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final String BASE_URL = "/admin";

    @GetMapping("")
    public String adminHome() {
        return BASE_URL + "/index";
    }

}
