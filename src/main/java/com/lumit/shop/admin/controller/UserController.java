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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String userDashboard(Model model) {
        List<User> users = userService.findRecentUsers(); // 최근 가입자만 일부
        model.addAttribute("title", "회원 관리 대시보드");
        model.addAttribute("summaryCards", getUserSummaryCards());
        model.addAttribute("users", users);
        model.addAttribute("contentFragment", "admin/user/menu :: menuContent");
        return "admin/user/dashboard"; // dashboardLayout 사용
    }

    @GetMapping("/test-menu")
    public String testMenu() {
        return "admin/user/menu";  // .html 없이 경로만
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("tbLogin", new TbLogin());
        return "admin/user/userForm";
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("tbLogin") TbLogin tbLogin,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/user/userForm";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        tbLogin.setEmail(tbLogin.getUserId() + "@lumit.com");
        tbLogin.setPhone("추후 입력 요망");
        tbLogin.setAddress("추후 입력 요망");
        tbLogin.setRegId(currentUser.getUserId());
        tbLogin.setPassword(passwordEncoder.encode(tbLogin.getPassword()));

        try {
            userService.insertAdmin(tbLogin);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/user/userForm";
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/edit")
    public String editUserForm(Model model) {
        // TODO: 수정 대상 사용자 정보를 넘길 수 있도록 구현
        return "admin/user/editUser";
    }

    @GetMapping("/manager")
    public String manageAdmins(Model model) {
        List<User> adminList = userService.selectAdminList();
        List<User> oldAdminList = userService.selectOldAdminList();
        model.addAttribute("adminList", adminList);
        model.addAttribute("oldAdminList", oldAdminList);
        return "admin/user/managers";
    }

    private List<SummaryCardDto> getUserSummaryCards() {
        long totalUsers = userService.countAllUsers();            // 전체 회원 수
        long newToday = userService.countUsersToday();            // 오늘 신규 가입
        long adminCount = userService.countAdmins();              // 관리자 수

        return List.of(
                new SummaryCardDto("총 회원 수", String.valueOf(totalUsers), "fa-users"),
                new SummaryCardDto("오늘 가입자", String.valueOf(newToday), "fa-user-plus"),
                new SummaryCardDto("관리자 수", String.valueOf(adminCount), "fa-user-shield")
        );
    }

}
