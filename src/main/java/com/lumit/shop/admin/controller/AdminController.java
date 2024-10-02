package com.lumit.shop.admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final String BASE_URL = "/admin";
    private final String MEMBER_PATH = "/member";
    private final String NEW_MANAGER_FORM = BASE_URL + MEMBER_PATH + "/adminForm";

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


    @GetMapping(MEMBER_PATH + "/list")
    public String memberDashboard(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        return BASE_URL + MEMBER_PATH + "/index";
    }


    @GetMapping(MEMBER_PATH + "/newManager")
    public String newManager(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        model.addAttribute("tbLogin", new TbLogin());
        return NEW_MANAGER_FORM;
    }


    @PostMapping(MEMBER_PATH + "/newManager")
    public String newManager(@Valid @ModelAttribute("tbLogin") TbLogin tbLogin, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        tbLogin.setRegId(user.getUserId());
        tbLogin.setPassword(passwordEncoder.encode(tbLogin.getPassword()));
        if (bindingResult.hasErrors()) {
            return NEW_MANAGER_FORM;
        }
        try {
            userService.insertAdmin(tbLogin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("tbLogin", new TbLogin());
            return NEW_MANAGER_FORM;
        }
        return "redirect:/admin";
    }

    //    @PreAuthorize("hasAnyAuthority('1', '2')")
    @GetMapping(MEMBER_PATH + "/user")
    public String user(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("request", request);
        return BASE_URL + MEMBER_PATH + "/user/dashboard";
    }
} 
