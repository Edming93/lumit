package com.lumit.shop.admin.controller;

import com.lumit.shop.admin.dto.SummaryCardDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lumit.shop.common.service.UserService;

import lombok.RequiredArgsConstructor;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String userDashboard() {
        return "admin/user/index";
    }

    @GetMapping("/new-manager")
    public String newUserForm(Model model) {
        model.addAttribute("tbLogin", new TbLogin());
        return "admin/user/form";
    }


    @GetMapping("/customers")
    public String editUserForm() {
        return "admin/user/customers";
    }

    @GetMapping("/manager")
    public String manageAdmins() {
        return "admin/user/managers";
    }

}
