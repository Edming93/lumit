package com.lumit.shop.admin.controller;


import com.lumit.shop.admin.dto.UserDto;
import com.lumit.shop.admin.model.User;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
// todo
// authority가 TB_MENU 형태로 들어가서 사용이 안됨
//@PreAuthorize("hasAnyAuthority('1', '2')")
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final String BASE_URL = "/admin";

    private final String NEW_MANAGER_FORM = BASE_URL + "/manager/managerForm";

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

    @GetMapping("/manager/new")
    public String newManager(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        model.addAttribute("userDto", new UserDto());
        return NEW_MANAGER_FORM;
    }

    @PostMapping("/manager/new")
    public String newManager(@Valid UserDto userDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("request", request);
            model.addAttribute("userDto", new UserDto());
            return NEW_MANAGER_FORM;
        }
        try {
            User manager = User.createAdmin(userDto, passwordEncoder);
            userService.addNewUser(manager);
        } catch (Exception e) {
            model.addAttribute("request", request);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userDto", new UserDto());
            return NEW_MANAGER_FORM;
        }
        model.addAttribute("request", request);
        return "redirect:/admin";
    }

    //    @PreAuthorize("hasAnyAuthority('1', '2')")
    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("request", request);
        return BASE_URL + "/user/index";
    }
}
