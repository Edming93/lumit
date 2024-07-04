package com.lumit.shop.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommonController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "common/auth/login";
    }
}
