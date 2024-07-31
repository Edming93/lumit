package com.lumit.shop.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class CommonController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "common/auth/login";
    }

    @GetMapping("/admin")
    public String getAdminMain() {
        return "admin/index";
    }

    @GetMapping("/lumit")
    public String getLumitMain() {
        return "lumit/index";
    }
}
