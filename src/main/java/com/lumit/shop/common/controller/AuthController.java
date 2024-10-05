package com.lumit.shop.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    @GetMapping(value = "/accessDenied")
    public String accessDenied() {
        return "/error/403";
    }
}
