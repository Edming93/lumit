package com.lumit.shop.admin.controller;


import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.MenuService;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('1', '2')")
@RequestMapping(value = "/admin")
public class AdminController {
    private final MenuService menuService;
    private final UserService userService;
    final String BASE_URL = "admin";

    @GetMapping("")
    public String adminHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("request", request);
        return BASE_URL + "/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("request", request);
        return BASE_URL + "/dashboard/index";
    }

    @PreAuthorize("hasAnyAuthority('1', '2')")
    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("request", request);
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return BASE_URL + "/user/index";
    }
}
